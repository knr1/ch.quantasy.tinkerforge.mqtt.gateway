/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway;

import ch.quantasy.gateway.service.device.barometer.BarometerServiceContract;
import ch.quantasy.mqtt.communication.MQTTCommunication;
import ch.quantasy.mqtt.communication.MQTTCommunicationCallback;
import ch.quantasy.mqtt.communication.MQTTParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DistanceUSAgent implements MQTTCommunicationCallback {

    private final MQTTCommunication communication;
    private final ObjectMapper mapper;
    private final BarometerServiceContract serviceContract;
    private final Map<String, MqttMessage> messageMap;

    public DistanceUSAgent() throws MqttException {
        messageMap = new HashMap<>();
        serviceContract = new BarometerServiceContract("jAy", "DistanceUS");
        mapper = new ObjectMapper(new YAMLFactory());
        communication = new MQTTCommunication();
        MQTTParameters parameters = new MQTTParameters();
        parameters.setClientID("DistanceUSAgent");
        parameters.setIsCleanSession(true);
        parameters.setIsLastWillRetained(true);
        parameters.setLastWillMessage(serviceContract.OFFLINE.getBytes());
        parameters.setLastWillQoS(1);
        parameters.setServerURIs(URI.create("tcp://127.0.0.1:1883"));
        parameters.setWillTopic(serviceContract.STATUS_TOPIC_CONNECTION);
        parameters.setMqttCallback(this);
        communication.connect(parameters);
        communication.publishActualWill(serviceContract.ONLINE.getBytes());

        communication.subscribe(serviceContract.ID_TOPIC + "/#", 1);
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        thrwbl.printStackTrace();
    }
    int count;

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.printf("%d Topic: %s (%s)\n", count++, string, new String(mm.getPayload()));

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("done.");
    }

    @Override
    public MqttMessage getMessageToPublish(String topic) {
        return messageMap.get(topic);
    }

    public void connect() throws JsonProcessingException {
        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes("localhost"));
        String topic = "TF/Manager/intent/stack/address/add";
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);
    }

    public static void main(String[] args) throws Throwable {
        DistanceUSAgent agent = new DistanceUSAgent();
        Thread.sleep(1000);
        agent.connect();
        Thread.sleep(1000);
        System.in.read();
    }

}
