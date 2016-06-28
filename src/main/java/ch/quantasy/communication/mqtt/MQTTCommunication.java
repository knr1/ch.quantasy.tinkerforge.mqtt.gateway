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
package ch.quantasy.communication.mqtt;

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
    
    public synchronized IMqttToken unsubscribe(String topic) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                return null;
            }
            return mqttClient.unsubscribe(topic);
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
