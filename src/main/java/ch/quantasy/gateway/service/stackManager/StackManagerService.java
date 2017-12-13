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
package ch.quantasy.gateway.service.stackManager;

import ch.quantasy.gateway.message.stackManager.StackAddressEvent;
import ch.quantasy.gateway.message.stack.TinkerforgeStackIntent;
import ch.quantasy.gateway.tinkerforge.TinkerForgeManager;
import ch.quantasy.gateway.tinkerforge.TinkerforgeFactoryListener;
import ch.quantasy.gateway.service.AbstractService;
import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceListener;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.gateway.message.stack.TinkerforgeStackAddress;
import ch.quantasy.gateway.message.stackManager.ConnectStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.mqtt.gateway.client.message.MessageCollector;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackListener;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class StackManagerService extends AbstractService<StackManagerServiceContract> implements TinkerforgeFactoryListener, TinkerforgeStackListener, TinkerforgeDeviceListener {

    private TinkerForgeManager manager;

    private static String computerName;

    static {
        try {
            computerName = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(StackManagerServiceContract.class.getName()).log(Level.SEVERE, null, ex);
            computerName = "undefined";
        }
    }

    public StackManagerService(TinkerForgeManager manager, URI mqttURI) throws MqttException {
        super(mqttURI, "TinkerforgeStackManager", new StackManagerServiceContract(computerName));
        intentCollector = new MessageCollector(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return (int) (o1.getTimeStamp() - o2.getTimeStamp());
            }
        });
        this.manager = manager;
        manager.addListener(this);
        updateStatus();
    }
    private final MessageCollector intentCollector;

    @Override
    public void messageReceived(String topic, byte[] payload) throws Exception {
        Class messageClass = TinkerforgeStackIntent.class;
        if (messageClass == null) {
            return;
        }
        Set<Message> messages = super.toMessageSet(payload, messageClass);
        intentCollector.add(topic, messages);
        while (true) {
            Message message = intentCollector.retrieveFirstMessage(topic);
            if (message == null) {
                break;
            }
            if (message instanceof TinkerforgeStackIntent) {
                TinkerforgeStackIntent intent = (TinkerforgeStackIntent) message;
                if (!intent.isValid()) {
                    return;
                }
                if (intent.connect) {
                    manager.addStack(intent.address);
                }
                if (!intent.connect) {
                    manager.removeStack(intent.address);
                    //System.out.println(">>" + getMapper().readValue(payload, String.class));
                }
            }
        }
    }

    @Override
    public void connected(TinkerforgeStack stack) {
        TinkerforgeStackAddress address = stack.getStackAddress();
        String topic = getContract().STATUS_STACK_ADDRESS + "/" + address.getHostName() + ":" + address.getPort();
        readyToPublishStatus(topic, new ConnectStatus(stack.isConnected()));

    }

    @Override
    public void disconnected(TinkerforgeStack stack
    ) {
        TinkerforgeStackAddress address = stack.getStackAddress();
        String topic = getContract().STATUS_STACK_ADDRESS + "/" + address.getHostName() + ":" + address.getPort();
        readyToPublishStatus(topic, new ConnectStatus(stack.isConnected()));
    }

    public void updateStatus() {
        Set<TinkerforgeStackAddress> addresses = manager.getStackAddresses();
        for (TinkerforgeStackAddress address : addresses) {
            TinkerforgeStack stack = manager.getStackFactory().getStack(address);
            this.stackAdded(stack);
        }
    }

    @Override
    public void stackAdded(TinkerforgeStack stack) {
        stack.addListener((TinkerforgeStackListener) this);
        stack.addListener((TinkerforgeDeviceListener) this);

        TinkerforgeStackAddress address = stack.getStackAddress();
        readyToPublishStatus(getContract().STATUS_STACK_ADDRESS + "/" + address.getHostName() + ":" + address.getPort(), new ConnectStatus(stack.isConnected()));
        readyToPublishEvent(getContract().EVENT_STACK_ADDRESS_ADDED, new StackAddressEvent(true, address));
    }

    @Override
    public void stackRemoved(TinkerforgeStack stack) {

        if (stack == null) {
            return;
        }
        {
            stack.removeListener((TinkerforgeStackListener) this);
            TinkerforgeStackAddress address = stack.getStackAddress();
            String topic = getContract().STATUS_STACK_ADDRESS + "/" + address.getHostName() + ":" + address.getPort();
            clearPublish(topic);
            readyToPublishEvent(getContract().EVENT_STACK_ADDRESS_REMOVED, new StackAddressEvent(false, address));
        }
        for (TinkerforgeDevice device : stack.getDevices()) {
            device.removeListener(this);
            String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
            clearPublish(topic);
        }
    }

//    private void updateDevice(TinkerforgeDevice device) {
//        String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
//        String connection = null;
//        try {
//            connection = "" + device.isConnected();
//        } catch (Exception ex) {
//            connection = ex.getMessage();
//        }
//        readyToPublishStatus(topic, connection + "updated");
//    }
    @Override
    public void connected(TinkerforgeDevice device) {
        String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        readyToPublishStatus(topic, new ConnectStatus(true));
    }

    @Override
    public void reConnected(TinkerforgeDevice device) {
        String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        readyToPublishStatus(topic, new ConnectStatus(true));
    }

    @Override
    public void disconnected(TinkerforgeDevice device) {
        String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        readyToPublishStatus(topic, new ConnectStatus(false));
    }
}
