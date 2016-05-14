/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
