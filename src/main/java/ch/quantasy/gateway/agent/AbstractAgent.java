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
package ch.quantasy.gateway.agent;

import ch.quantasy.communication.mqtt.MQTTCommunication;
import ch.quantasy.communication.mqtt.MQTTCommunicationCallback;
import ch.quantasy.communication.mqtt.MQTTParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public abstract class AbstractAgent implements MQTTCommunicationCallback{

    private final MQTTCommunication communication;
    private final ObjectMapper mapper;

    private final Map<String, MqttMessage> messageMap;
    private String agentIdentifier;
    private final Map<String, MessageConsumer> messageConsumerMap;

    public AbstractAgent(URI mqttURI, String sessionID, String agentIdentifier) throws MqttException {
        this.agentIdentifier = agentIdentifier;
        messageMap = new HashMap<>();
        messageConsumerMap=new HashMap<>();
        mapper = new ObjectMapper(new YAMLFactory());
        communication = new MQTTCommunication();
        MQTTParameters parameters = new MQTTParameters();
        parameters.setClientID(sessionID);
        parameters.setIsCleanSession(true);
        parameters.setIsLastWillRetained(true);
        parameters.setLastWillQoS(1);
        parameters.setServerURIs(mqttURI);
        parameters.setWillTopic(this.agentIdentifier + "/status/online");
        try {
            parameters.setLastWillMessage(mapper.writeValueAsBytes(Boolean.FALSE));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(AbstractAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        parameters.setMqttCallback(this);
        communication.connect(parameters);
        try {
            communication.publishActualWill(mapper.writeValueAsBytes(Boolean.TRUE));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(AbstractAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void subscribe(String topic,int qualityOfService){
        communication.subscribe(topic, qualityOfService);
    }
    
    public void unsubscribe(String topic){
        communication.unsubscribe(topic);
    }
    
//    public void subscribe(String topic,int qualityOfService,MessageConsumer consumer){
//        messageConsumerMap.
//        communication.subscribe(topic, qualityOfService);
//    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        thrwbl.printStackTrace();
        System.out.println("Uuups, connection lost");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("done.");
    }

    @Override
    public MqttMessage getMessageToPublish(String topic) {
        return messageMap.get(topic);
    }

    protected void addMessage(String topic, Object status) {
        try {
            MqttMessage message = null;
            if (status != null) {
                message = new MqttMessage(mapper.writeValueAsBytes(status));
            } else {
                message = new MqttMessage();
            }
            message.setQos(1);
            message.setRetained(true);
            topic=topic + "/" + agentIdentifier;
            messageMap.put(topic, message);
            communication.readyToPublish(this, topic);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(AbstractAgent.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
