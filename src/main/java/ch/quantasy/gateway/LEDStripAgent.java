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

import ch.quantasy.gateway.service.device.ledStrip.LEDStripServiceContract;
import ch.quantasy.mqtt.communication.MQTTCommunication;
import ch.quantasy.mqtt.communication.MQTTCommunicationCallback;
import ch.quantasy.mqtt.communication.MQTTParameters;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class LEDStripAgent implements MQTTCommunicationCallback {

    private final MQTTCommunication communication;
    private final ObjectMapper mapper;
    private final LEDStripServiceContract serviceContract;
    private final Map<String, MqttMessage> messageMap;

    public LEDStripAgent() throws MqttException {
        messageMap = new HashMap<>();
        serviceContract = new LEDStripServiceContract("oZU",TinkerforgeDeviceClass.LEDStrip.toString());
        mapper = new ObjectMapper(new YAMLFactory());
        communication = new MQTTCommunication();
        MQTTParameters parameters = new MQTTParameters();
        parameters.setClientID("TinkerforgeLEDStripAgent");
        parameters.setIsCleanSession(false);
        parameters.setIsLastWillRetained(true);
        parameters.setLastWillMessage(serviceContract.OFFLINE.getBytes());
        parameters.setLastWillQoS(1);
        parameters.setServerURIs(URI.create("tcp://matrix:1883"));
        parameters.setWillTopic(serviceContract.STATUS_CONNECTION);
        parameters.setMqttCallback(this);
        communication.connect(parameters);
        communication.publishActualWill(serviceContract.ONLINE.getBytes());

        communication.subscribe(serviceContract.ID_TOPIC + "/#", 1);
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Uuups, connection lost");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        //System.out.printf("Topic: %s (%s)\n", string, new String(mm.getPayload()));
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
        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes("og-luzia"));
        String topic = "TF/Manager/intent/stack/address/add";
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);
    }

    public void config() throws JsonProcessingException {
        LEDStripDeviceConfig config = new LEDStripDeviceConfig(LEDStripDeviceConfig.ChipType.WS2812, 2000000, 1, 120, LEDStripDeviceConfig.ChannelMapping.BRG);
        
        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes(config));
        String topic = serviceContract.INTENT_CONFIG;
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);
        double sin1=0;
        for(int i=0;i<120;i++){
            leds[0][i]=(short)(128.0+(127*Math.sin(sin1+=0.51)));
            //leds[1][i]=(short)(128.0+(127*Math.sin(sinBlueTwo+=0.9)));
            //leds[2][i]=(short)(128.0+(127*Math.sin(sinBlueOne+=2.1234567)));
        }
        leds[1][0]=255;
        leds[2][119]=255;
        
    }
        short[][] leds=new short[3][120];
    public void leds() throws JsonProcessingException {
        short[][] leds=new short[3][120];
        for(int i=0;i<120;i++){
            leds[0][i]=this.leds[0][(i+1)%120];
            leds[1][i]=this.leds[1][(i+119)%120];
            leds[2][i]=this.leds[2][(i+1)%120];
            //leds[2][i]=(short)(128.0+(127*Math.sin(sinBlueOne+=2.1234567)));
        }
        this.leds=leds;
        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes(this.leds));
        String topic = serviceContract.INTENT_LEDs;
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);
    }

   
    public static void main(String[] args) throws Throwable {
        LEDStripAgent agent = new LEDStripAgent();
        Thread.sleep(1000);
        agent.connect();
        Thread.sleep(1000);
        agent.config();
        Thread.sleep(1000);
        while(true)
        {
            agent.leds();
            Thread.sleep(500);
        }
    }

}
