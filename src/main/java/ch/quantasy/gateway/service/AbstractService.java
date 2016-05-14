/*
 * Within this step, a service is using the MQTTCommunication and the 'business-logic'
 * The response of the 'business logic' is promoted to the event topic.
 * The 'business-logic' is invoked via the service and then is self-sustaining.
 * The 'business-logic' sends the result via Callback
 * This way we have a Model-'View'-Presenter (MVP) Where the presenter (the service) is glueing together
 * the Model ('business-logic') and the 'View' (the MQTT-Communication)
 * The service is promoting more status information to the status topic about the underlying 'business-logic'.
 *
 * This time, an agent is communicating with the service and controls it.
 * This way we delve into the Service based Agent oriented programming
 */
package ch.quantasy.gateway.service;

import ch.quantasy.mqtt.communication.MQTTCommunication;
import ch.quantasy.mqtt.communication.MQTTCommunicationCallback;
import ch.quantasy.mqtt.communication.MQTTParameters;
import ch.quantasy.tinkerforge.device.generic.DeviceCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.net.URI;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

/**
 *
 * @author reto
 * @param <S>
 */
public abstract class AbstractService<S extends ServiceContract> implements MQTTCommunicationCallback, DeviceCallback {

    private final MQTTParameters parameters;
    private final S serviceContract;
    private final MQTTCommunication communication;
    private final ObjectMapper mapper;
    private final HashMap<String, MqttMessage> statusMap;
    private final HashMap<String, List<Object>> eventMap;
    private final HashMap<String, MqttMessage> contractDescriptionMap;

    public AbstractService(S serviceContract,String clientID) throws MqttException {
        this.serviceContract = serviceContract;

        statusMap = new HashMap<>();
        eventMap = new HashMap<>();
        contractDescriptionMap = new HashMap<>();
        mapper = new ObjectMapper(new YAMLFactory());
        communication = new MQTTCommunication();
        parameters = new MQTTParameters();
        parameters.setClientID(clientID);
        parameters.setIsCleanSession(false);
        parameters.setIsLastWillRetained(true);
        parameters.setLastWillMessage(serviceContract.OFFLINE.getBytes());
        parameters.setLastWillQoS(1);
        parameters.setServerURIs(URI.create("tcp://127.0.0.1:1883"));
        parameters.setWillTopic(serviceContract.STATUS_CONNECTION);
        parameters.setMqttCallback(this);
        communication.connect(parameters);
        communication.publishActualWill(serviceContract.ONLINE.getBytes());
        communication.subscribe(serviceContract.INTENT + "/#", 1);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        //System.out.println("Delivery is done.");
    }

    public S getServiceContract() {
        return serviceContract;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    @Override
    public MqttMessage getMessageToPublish(String topic) {
        MqttMessage message = statusMap.get(topic);
        if (message != null) {
            return message;
        }
        message = contractDescriptionMap.get(topic);
        if (message != null) {
            return message;
        }
        List<Object> eventList = eventMap.get(topic);
        if (eventList != null) {
            eventMap.put(topic, new LinkedList<>());
            try {
                message = new MqttMessage(mapper.writeValueAsBytes(eventList));
                message.setQos(1);
                message.setRetained(true);
                return message;
            } catch (JsonProcessingException ex) {
                Logger.getLogger(AbstractService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    
    protected void addEvent(String topic, Object event) {
        List<Object> eventList = eventMap.get(topic);
        if (eventList == null) {
            eventList = new LinkedList<>();
            eventMap.put(topic, eventList);
        }
        eventList.add(event);
        this.communication.readyToPublish(this, topic);
    }

    protected void addStatus(String topic, Object status) {
        try {
            MqttMessage message = null;
            if(status!=null)
                message=new MqttMessage(mapper.writeValueAsBytes(status));
            else
                message=new MqttMessage();
            message.setQos(1);
            message.setRetained(true);
            statusMap.put(topic, message);
            communication.readyToPublish(this, topic);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(AbstractService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected void addDescription(String topic, Object description) {
        try {
            MqttMessage message = new MqttMessage(mapper.writeValueAsBytes(description));
            message.setQos(1);
            message.setRetained(true);
            String descriptionTopic=getServiceContract().DESCRIPTION_TOPIC+"/"+topic;
            contractDescriptionMap.put(descriptionTopic, message);
            communication.readyToPublish(this, descriptionTopic);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(AbstractService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        thrwbl.printStackTrace();
        System.out.println("Ouups, lost connection to subscriptions");
//        if (this.timer != null) {
//            return;
//        }
//        this.timer = new Timer(true);
//        this.timer.scheduleAtFixedRate(new TimerTask() {
//
//            @Override
//            public void run() {
//                try {
//                    try {
//                        communication.connect(parameters);
//                        Thread.sleep(3000);
//                    } catch (final InterruptedException e) {
//                        //OK, we go on
//                    }
//                    if (communication.isConnected()) {
//                        timer.cancel();
//                        timer=null;
//                    }
//                } catch (MqttException ex) {
//                }
//            }
//        }, 0, 3000);
    }
}
