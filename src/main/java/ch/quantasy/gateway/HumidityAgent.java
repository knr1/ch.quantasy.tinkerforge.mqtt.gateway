/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway;

import ch.quantasy.gateway.service.device.humidity.HumidityServiceContract;
import ch.quantasy.mqtt.communication.MQTTCommunication;
import ch.quantasy.mqtt.communication.MQTTCommunicationCallback;
import ch.quantasy.mqtt.communication.MQTTParameters;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
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
public class HumidityAgent implements MQTTCommunicationCallback {

    private final MQTTCommunication communication;
    private final ObjectMapper mapper;
    private final HumidityServiceContract humidityServiceContract;
    private final Map<String, MqttMessage> messageMap;

    public HumidityAgent() throws MqttException {
        messageMap = new HashMap<>();
        humidityServiceContract = new HumidityServiceContract("k9P",TinkerforgeDeviceClass.Humidity.toString());
        mapper = new ObjectMapper(new YAMLFactory());
        communication = new MQTTCommunication();
        MQTTParameters parameters = new MQTTParameters();
        parameters.setClientID("HumidityAgent");
        parameters.setIsCleanSession(false);
        parameters.setIsLastWillRetained(true);
        parameters.setLastWillMessage(humidityServiceContract.OFFLINE.getBytes());
        parameters.setLastWillQoS(1);
        parameters.setServerURIs(URI.create("tcp://127.0.0.1:1883"));
        parameters.setWillTopic(humidityServiceContract.STATUS_TOPIC_CONNECTION);
        parameters.setMqttCallback(this);
        communication.connect(parameters);
        communication.publishActualWill(humidityServiceContract.ONLINE.getBytes());

        communication.subscribe(humidityServiceContract.ID_TOPIC + "/#", 1);
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Uuups, connection lost");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.printf("Topic: %s (%s)\n", string, new String(mm.getPayload()));
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

    public void analogValue() throws JsonProcessingException {
        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes("100"));
        String topic = "TF/Humidity/k9P/intent/analogValue/callbackPeriod";
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);
    }

    public void analogThreshold() throws JsonProcessingException {
         Tester.MyHumididtyCallbackThreshold th=new Tester.MyHumididtyCallbackThreshold('>', 2000, 2000);
         
        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes(th));
        String topic = "TF/Humidity/k9P/intent/analogValue/threshold";
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);
    }

    public static void main(String[] args) throws Throwable {
        HumidityAgent agent = new HumidityAgent();
        Thread.sleep(1000);
        agent.connect();
        Thread.sleep(1000);
        agent.analogValue();
        agent.analogThreshold();
    }

}
