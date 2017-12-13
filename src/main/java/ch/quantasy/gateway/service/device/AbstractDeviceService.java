/*
 * /*
 *  *   "TiMqWay"
 *  *
 *  *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
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
package ch.quantasy.gateway.service.device;

import ch.quantasy.gateway.message.device.Firmware;
import ch.quantasy.gateway.message.device.Hardware;
import ch.quantasy.gateway.message.device.Position;
import ch.quantasy.gateway.service.AbstractService;
import ch.quantasy.mqtt.gateway.client.message.Intent;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.mqtt.gateway.client.message.MessageCollector;
import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import java.net.URI;
import java.util.Comparator;
import java.util.Set;
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author reto
 * @param <G>
 * @param <S>
 */
public abstract class AbstractDeviceService<G extends GenericDevice, S extends DeviceServiceContract> extends AbstractService<S> implements DeviceCallback {

    private final G device;
    private final MessageCollector intentCollector;

    public AbstractDeviceService(URI mqttURI, G device, S serviceContract) throws MqttException {
        super(mqttURI, serviceContract.CANONICAL_TOPIC, serviceContract);
        this.device = device;
        intentCollector = new MessageCollector(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return (int) (o1.getTimeStamp() - o2.getTimeStamp());
            }
        });
        device.setCallback(this);

        readyToPublishStatus(getContract().STATUS_POSITION, new Position(device.getPosition()));
        readyToPublishStatus(getContract().STATUS_FIRMWARE, new Firmware(device.getFirmwareVersion()));
        readyToPublishStatus(getContract().STATUS_HARDWARE, new Hardware(device.getHardwareVersion()));

    }

    private final Set<String> workingSet = new HashSet();

    @Override
    public void messageReceived(String topic, byte[] payload) throws Exception {
        //Problem: topic is fully qualified, but we neeed root (without /#)
        //Class messageClass = super.getContract().getMessageTopicMap().get(topic);
        //This solution is dangerous, if there are multiple Intents for a device.
        Class messageClass = getDevice().getIntent().getClass();
        if (messageClass == null) {
            return;
        }
        Set<Message> messages = super.toMessageSet(payload, messageClass);
        intentCollector.add(topic, messages);
        synchronized (workingSet) {
            if (!workingSet.add(topic)) {
                return;
            }
        }
        //Horribly ugly! This has to be done elsewhere in a worker'thread'
        while (true) {
            Message message = intentCollector.retrieveFirstMessage(topic);
            synchronized (workingSet) {
                if (message == null) {
                    workingSet.remove(topic);
                    break;
                }
            }
            if (message instanceof Intent) {
                getDevice().update((Intent) message);
            }
        }
    }

    public G getDevice() {
        return device;
    }

}
