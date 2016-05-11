/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway;

import ch.quantasy.gateway.service.device.dualButton.DualButtonService;
import ch.quantasy.gateway.service.device.dualButton.DualButtonServiceContract;
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
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class LEDStripDualButtonAgent implements MQTTCommunicationCallback {

    private final MQTTCommunication communication;
    private final ObjectMapper mapper;
    private final LEDStripServiceContract serviceContractLED;
    private final DualButtonServiceContract serviceContractDualButton;

    private final Map<String, MqttMessage> messageMap;

    public LEDStripDualButtonAgent() throws MqttException {
        messageMap = new HashMap<>();
        serviceContractLED = new LEDStripServiceContract("oZU",TinkerforgeDeviceClass.LEDStrip.toString());
        serviceContractDualButton = new DualButtonServiceContract("j2e",TinkerforgeDeviceClass.DualButton.toString());
        mapper = new ObjectMapper(new YAMLFactory());
        communication = new MQTTCommunication();
        MQTTParameters parameters = new MQTTParameters();
        parameters.setClientID("TinkerforgeLEDStripDualButtonAgent");
        parameters.setIsCleanSession(false);
        parameters.setIsLastWillRetained(true);
        parameters.setLastWillMessage(serviceContractLED.OFFLINE.getBytes());
        parameters.setLastWillQoS(1);
        parameters.setServerURIs(URI.create("tcp://localhost:1883"));
        parameters.setWillTopic("LEDStripDualButtonAgent/status/online");
        parameters.setMqttCallback(this);
        communication.connect(parameters);
        communication.publishActualWill(serviceContractLED.ONLINE.getBytes());

        communication.subscribe(serviceContractLED.ID_TOPIC + "/#", 1);
        communication.subscribe(serviceContractDualButton.ID_TOPIC + "/#", 1);

    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        thrwbl.printStackTrace();
        System.out.println("Uuups, connection lost");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println(string);
        if (string.equals(serviceContractDualButton.EVENT_TOPIC_STATE_CHANGED)) {
            System.out.println(new String(mm.getPayload()));
            DualButtonService.StateChangedEvent[] stateChangedEvents = mapper.readValue(mm.getPayload(), DualButtonService.StateChangedEvent[].class);
            if (stateChangedEvents.length > 0) {
                if (stateChangedEvents[stateChangedEvents.length - 1].getLed1() == 0) {
                    leds(0);
                }
                if (stateChangedEvents[stateChangedEvents.length - 1].getLed1() == 1) {
                    leds(1);
                }
                
            }
        }
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
        String topic = serviceContractLED.INTENT_TOPIC_CONFIG;
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);

        for (int i = 0; i < 120; i++) {
            leds[0][0][i] = (short) (255);
            leds[0][1][i] = (short) (150);
            leds[0][2][i] = (short) (50);
            leds[1][0][i] = (short) (0);
            leds[1][1][i] = (short) (0);
            leds[1][2][i] = (short) (0);

        }
    }
    short[][][] leds = new short[2][3][120];

    public void leds(int switcher) throws JsonProcessingException {

        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes(this.leds[switcher]));
        String topic = serviceContractLED.INTENT_TOPIC_LEDs;
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);

    }

    public static void main(String[] args) throws Throwable {
        LEDStripDualButtonAgent agent = new LEDStripDualButtonAgent();
        Thread.sleep(1000);
        agent.connect();
        Thread.sleep(1000);

        agent.config();
        Thread.sleep(1000);
        agent.leds(0);
        Thread.sleep(1000);
        agent.leds(1);
        System.in.read();
    }

}
