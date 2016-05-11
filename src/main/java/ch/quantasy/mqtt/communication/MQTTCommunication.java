/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.mqtt.communication;

import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author reto
 */
public class MQTTCommunication implements IMqttActionListener {

    private MQTTParameters connectionParameters;
    private final MqttConnectOptions connectOptions;
    private IMqttAsyncClient mqttClient;
    private Thread publisherThread;
    private final Publisher publisher;

    public MQTTCommunication() {
        this.connectOptions = new MqttConnectOptions();
        this.publisher = new Publisher();
    }

    public synchronized void connect(MQTTParameters connectionParameters) throws MqttException {
        if (connectionParameters == null || !connectionParameters.isValid()) {
            return;
        }
        if (mqttClient != null && mqttClient.isConnected()) {
            return;
        }
        connectionParameters.setInUse(true);
        this.connectionParameters = connectionParameters;
        if (mqttClient == null) {
            mqttClient = new MqttAsyncClient(getMQTTParameters().getServerURIsAsString()[0], connectionParameters.getClientID(), new MemoryPersistence());
        }
        connectOptions.setServerURIs(connectionParameters.getServerURIsAsString());
        mqttClient.setCallback(connectionParameters.getMqttCallback());
        connectOptions.setCleanSession(connectionParameters.isCleanSession());
        connectOptions.setWill(connectionParameters.getWillTopic(), connectionParameters.getLastWillMessage(), connectionParameters.getLastWillQoS(), connectionParameters.isLastWillRetained());
        mqttClient.connect(connectOptions).waitForCompletion();
        publisherThread = new Thread(publisher);
        publisherThread.setDaemon(true);
        publisherThread.start();
    }

    public synchronized IMqttDeliveryToken publishActualWill(byte[] actualWill) {
        MqttMessage message = new MqttMessage(actualWill);
        message.setQos(connectionParameters.getLastWillQoS());
        message.setRetained(connectionParameters.isLastWillRetained());
        return this.publish(connectionParameters.getWillTopic(), message);
    }

    public void readyToPublish(PublisherCallback publisherCallback, String topic) {
        publisher.readyToPublish(publisherCallback,topic);
    }

    private synchronized IMqttDeliveryToken publish(String topic, MqttMessage message) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                return null;
            }
            return mqttClient.publish(topic, message);
        } catch (Exception ex) {
            return null;
        }
    }

    public synchronized IMqttToken subscribe(String topic, int qualityOfService) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                return null;
            }
            return mqttClient.subscribe(topic, qualityOfService, null, this);
        } catch (Exception ex) {
            return null;
        }
    }

    public synchronized void disconnect() throws MqttException {
        if (mqttClient == null) {
            return;
        }
        mqttClient.disconnect();
        connectionParameters.setInUse(false);
    }

    public synchronized void disconnectForcibly() throws MqttException {
        if (mqttClient == null) {
            return;
        }
        mqttClient.disconnectForcibly();
        connectionParameters.setInUse(false);
    }

    public MQTTParameters getMQTTParameters() {
        return connectionParameters;
    }

    public synchronized boolean isConnected() {
        if (mqttClient == null) {
            return false;
        }
        return mqttClient.isConnected();
    }

    @Override
    public void onSuccess(IMqttToken imt) {
        System.out.printf("Success");
    }

    @Override
    public void onFailure(IMqttToken imt, Throwable thrwbl) {
        System.out.println("Fail" + thrwbl.toString());
    }

    class Publisher implements Runnable {

        private final BlockingDeque<PublishRequest> publishingQueue;

        public Publisher() {
            this.publishingQueue = new LinkedBlockingDeque<>();
        }

        public synchronized void readyToPublish(PublisherCallback callback, String topic) {
            PublishRequest publishRequest=new PublishRequest(callback, topic);
            if (this.publishingQueue.contains(publishRequest)) {
                return;
            }
            this.publishingQueue.add(publishRequest);
            return;
        }

        @Override
        public void run() {
            while (MQTTCommunication.this.isConnected()) {
                try {
                    PublishRequest publishRequest = publishingQueue.take();
                    MqttMessage message = publishRequest.getMessage();
                    if (MQTTCommunication.this.isConnected() && message != null) {
                        IMqttDeliveryToken token = publish(publishRequest.topic, message);
                        if (token == null) {
                            Logger.getLogger(MQTTCommunication.class.getName()).log(Level.SEVERE, null, "Message for " + publishRequest + " lost.");
                        } else {
                            token.waitForCompletion();
                        }
                    }
                } catch (InterruptedException | MqttException ex) {
                    Logger.getLogger(MQTTCommunication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    class PublishRequest{
        public final String topic;
        public final PublisherCallback publisherCallback;

        public PublishRequest(PublisherCallback publisherCallback, String topic) {
            this.topic = topic;
            this.publisherCallback = publisherCallback;
        }
        
        public MqttMessage getMessage(){
            return this.publisherCallback.getMessageToPublish(topic);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 59 * hash + Objects.hashCode(this.topic);
            hash = 59 * hash + Objects.hashCode(this.publisherCallback);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final PublishRequest other = (PublishRequest) obj;
            if (!Objects.equals(this.topic, other.topic)) {
                return false;
            }
            if (!Objects.equals(this.publisherCallback, other.publisherCallback)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "PublishRequest{" + "publisherCallback=" + publisherCallback + ", topic=" + topic + '}';
        }
   
        
    }
}
