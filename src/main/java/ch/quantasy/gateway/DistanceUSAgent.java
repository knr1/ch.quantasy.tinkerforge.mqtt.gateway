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
        parameters.setWillTopic(serviceContract.STATUS_CONNECTION);
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
