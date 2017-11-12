/*
 * /*
 *  *   "SeMqWay"
 *  *
 *  *    SeMqWay(tm): A gateway to provide an MQTT-View for any micro-service (Service MQTT-Gateway).
 *  *
 *  *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
 *  *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *    Quellgasse 21, CH-2501 Biel, Switzerland
 *  *
 *  *    Licensed under Dual License consisting of:
 *  *    1. GNU Affero General Public License (AGPL) v3
 *  *    and
 *  *    2. Commercial license
 *  *
 *  *
 *  *    1. This program is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU Affero General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     This program is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU Affero General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU Affero General Public License
 *  *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 *  *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *  *     accordance with the commercial license agreement provided with the
 *  *     Software or, alternatively, in accordance with the terms contained in
 *  *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *  *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *  *
 *  *
 *  *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *  *
 *  *
 */
package ch.quantasy.gateway.service;

import ch.quantasy.gateway.message.event.AnEvent;
import ch.quantasy.gateway.message.intent.AnIntent;
import ch.quantasy.mqtt.gateway.client.GatewayClient;
import ch.quantasy.mqtt.gateway.client.message.MessageCollector;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.mqtt.gateway.client.message.MessageReceiver;
import ch.quantasy.mqtt.gateway.client.message.PublishingMessageCollector;

/**
 *
 * @author reto
 * @param <S>
 */
public abstract class AbstractService<S extends TinkerForgeServiceContract> extends GatewayClient<S> implements MessageReceiver {

    private final MessageCollector collector;
    private PublishingMessageCollector<S> eventCollector;

    public AbstractService(URI mqttURI, String clientID, S contract) throws MqttException {
        super(mqttURI, clientID, contract);
        collector = new MessageCollector();
        connect();
        subscribe(contract.INTENT + "/#", this);
        eventCollector = new PublishingMessageCollector<S>(collector, this);
    }

    /**
     * This is called within a new runnable! Be sure this method is programmed
     * thread safe!
     *
     * @param topic This String is never null and contains the topic of the mqtt
     * message.
     * @param payload This byte[] shall never be null and contains the payload
     * of the mqtt message.
     * @throws Exception Any exception is handled 'gracefully' within
     * AbstractService.
     */
    public abstract void messageReceived(String topic, byte[] payload) throws Exception;

    //Change it back to readyToPublishEvent
    public void readyToPublishEvent(String topic, AnEvent event) {
        this.eventCollector.readyToPublish(topic, event);
    }
    
}
