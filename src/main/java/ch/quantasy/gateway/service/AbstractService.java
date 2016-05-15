/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
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

    public AbstractService(S serviceContract, String clientID) throws MqttException {
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

        addDescription(getServiceContract().STATUS_CONNECTION, "[" + getServiceContract().ONLINE + "|" + getServiceContract().OFFLINE + "]");
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
            if (status != null) {
                message = new MqttMessage(mapper.writeValueAsBytes(status));
            } else {
                message = new MqttMessage();
            }
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
            
            topic = topic.replaceFirst(getServiceContract().ID_TOPIC,"");
            String descriptionTopic = getServiceContract().DESCRIPTION + topic;
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
