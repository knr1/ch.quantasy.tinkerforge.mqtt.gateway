/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway;

import ch.quantasy.gateway.*;
import ch.quantasy.gateway.service.device.humidity.HumidityServiceContract;
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
public class BarometerAgent implements MQTTCommunicationCallback {

    private final MQTTCommunication communication;
    private final ObjectMapper mapper;
    private final BarometerServiceContract serviceContract;
    private final Map<String, MqttMessage> messageMap;

    public BarometerAgent() throws MqttException {
        messageMap = new HashMap<>();
        serviceContract = new BarometerServiceContract("jod", "Barometer");
        mapper = new ObjectMapper(new YAMLFactory());
        communication = new MQTTCommunication();
        MQTTParameters parameters = new MQTTParameters();
        parameters.setClientID("BarometerAgent");
        parameters.setIsCleanSession(false);
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

    public void airPressure() throws JsonProcessingException {
        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes("1"));
        String topic = serviceContract.INTENT_TOPIC_AIR_PRESSURE_CALLBACK_PERIOD;
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);
    }

    public void averaging() throws JsonProcessingException {
        MqttMessage message = new MqttMessage(mapper.writeValueAsBytes("averagingPressure: 0\naveragingTemperature: 0\n movingAveragePressure: 0"));
        String topic = serviceContract.INTENT_TOPIC_AIR_PRESSURE;
        messageMap.put(topic, message);
        communication.readyToPublish(this, topic);
    }

    public static void main(String[] args) throws Throwable {
        BarometerAgent agent = new BarometerAgent();
        Thread.sleep(1000);
        agent.connect();
        Thread.sleep(1000);
        agent.airPressure();
        agent.averaging();
        System.in.read();
    }

}
