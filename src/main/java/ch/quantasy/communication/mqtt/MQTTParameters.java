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

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttCallback;

/**
 *
 * @author reto
 */
public class MQTTParameters {

    private URI[] serverURIs;
    private String clientID;

    private boolean isCleanSession;
    private String willTopic;
    private byte[] lastWillMessage;
    private boolean isLastWillRetained;
    private int lastWillQoS;

    private MqttCallback mqttCallback;

    private boolean inUse;

    public MQTTParameters() {

    }

    public MQTTParameters(String clientID, boolean isCleanSession, String willTopic, byte[] lastWillMessage, boolean isLastWillRetained, int lastWillQoS, MqttCallback mqttCallback, URI... serverURIs) {
        this.serverURIs = serverURIs;
        this.clientID = clientID;
        this.isCleanSession = isCleanSession;
        this.willTopic = willTopic;
        this.lastWillMessage = lastWillMessage;
        this.isLastWillRetained = isLastWillRetained;
        this.lastWillQoS = lastWillQoS;
        this.mqttCallback = mqttCallback;
    }

    public URI[] getServerURIs() {
        return serverURIs.clone();
    }

    public String[] getServerURIsAsString() {
        String[] urisAsStrings = new String[serverURIs.length];
        for (int i = 0; i < serverURIs.length; i++) {
            urisAsStrings[i] = serverURIs[i].toString();
        }
        return urisAsStrings;
    }

    public void setServerURIs(URI... serverURIs) {
        if (isInUse()) {
            return;
        }
        this.serverURIs = serverURIs.clone();
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        if (isInUse()) {
            return;
        }

        this.clientID = clientID;
    }

    public boolean isCleanSession() {
        return isCleanSession;
    }

    public void setIsCleanSession(boolean isCleanSession) {
        if (isInUse()) {
            return;
        }

        this.isCleanSession = isCleanSession;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public void setWillTopic(String willTopic) {
        if (isInUse()) {
            return;
        }

        this.willTopic = willTopic;
    }

    public byte[] getLastWillMessage() {
        return lastWillMessage.clone();
    }

    public void setLastWillMessage(byte[] lastWillMessage) {
        if (isInUse()) {
            return;
        }

        this.lastWillMessage = lastWillMessage.clone();
    }

    public boolean isLastWillRetained() {
        return isLastWillRetained;
    }

    public void setIsLastWillRetained(boolean isLastWillRetained) {
        if (isInUse()) {
            return;
        }

        this.isLastWillRetained = isLastWillRetained;
    }

    public int getLastWillQoS() {
        return lastWillQoS;
    }

    public void setLastWillQoS(int lastWillQoS) {
        if (isInUse()) {
            return;
        }
        this.lastWillQoS = lastWillQoS;
    }

    public MqttCallback getMqttCallback() {
        return mqttCallback;
    }

    public void setMqttCallback(MQTTCommunicationCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
    }

    public boolean isValid() {
        return this.clientID != null && this.serverURIs != null && willTopic != null && this.mqttCallback != null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Used by MQTTCommunication to indicate if a connection uses these
     * parameters
     *
     * @return
     */
    public boolean isInUse() {
        return inUse;
    }

    /**
     * Used by MQTTCommunication to indicate that a connection uses these
     * parameters.
     *
     * @param inUse
     */
    protected void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

}
