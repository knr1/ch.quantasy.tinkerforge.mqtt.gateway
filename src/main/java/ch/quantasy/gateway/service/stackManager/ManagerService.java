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

import ch.quantasy.gateway.tinkerforge.TinkerForgeManager;
import ch.quantasy.gateway.tinkerforge.TinkerforgeFactoryListener;
import ch.quantasy.gateway.service.AbstractService;
import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceListener;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackListener;
import java.net.URI;
import java.util.Set;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class ManagerService extends AbstractService<ManagerServiceContract> implements TinkerforgeFactoryListener, TinkerforgeStackListener, TinkerforgeDeviceListener {

    private TinkerForgeManager manager;

    public ManagerService(TinkerForgeManager manager, URI mqttURI) throws MqttException {
        super(mqttURI, "TinkerforgeStackManager", new ManagerServiceContract("Manager"));
        this.manager = manager;
        addDescription(getContract().INTENT_STACK_ADDRESS_ADD, "hostName: <String>\n prot: [0..4223..65536]");
        addDescription(getContract().INTENT_STACK_ADDRESS_REMOVE, "hostName: <String>\n prot: [0..4223..65536]");
        addDescription(getContract().EVENT_ADDRESS_CONNECTED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n   hostName: <String>\n prot: [0..4223..65536]");
        addDescription(getContract().EVENT_ADDRESS_DISCONNECTED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n  hostName: <String>\n prot: [0..4223..65536]");
        addDescription(getContract().EVENT_STACK_ADDRESS_ADDED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n   hostName: <String>\n prot: [0..4223..65536]");
        addDescription(getContract().EVENT_STACK_ADDRESS_REMOVED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n   hostName: <String>\n prot: [0..4223..65536]");
        addDescription(getContract().STATUS_STACK_ADDRESS + "/<address>/connected", "[true|false]");

        manager.addListener(this);
        updateStatus();
    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {
        if (string.startsWith(getContract().INTENT_STACK_ADDRESS_ADD)) {

            String payloadString = new String(payload);
            TinkerforgeStackAddress address = getMapper().readValue(payloadString, TinkerforgeStackAddress.class);
            if (manager.containsStack(address)) {
                return;
            }
            manager.addStack(address);

            //System.out.println(">>" + getMapper().readValue(payload, String.class));
        }
        if (string.startsWith(getContract().INTENT_STACK_ADDRESS_REMOVE)) {

            String payloadString = new String(payload);
            TinkerforgeStackAddress address = getMapper().readValue(payloadString, TinkerforgeStackAddress.class);
            if (!manager.containsStack(address)) {
                return;
            }
            manager.removeStack(address);
            //System.out.println(">>" + getMapper().readValue(payload, String.class));
        }

    }

    @Override
    public void connected(TinkerforgeStack stack) {
        TinkerforgeStackAddress address = stack.getStackAddress();
        String topic = getContract().STATUS_STACK_ADDRESS + "/" + address.getHostName() + ":" + address.getPort();
        addStatus(topic, stack.isConnected());

    }

    @Override
    public void disconnected(TinkerforgeStack stack) {
        TinkerforgeStackAddress address = stack.getStackAddress();
        String topic = getContract().STATUS_STACK_ADDRESS + "/" + address.getHostName() + ":" + address.getPort();
        addStatus(topic, stack.isConnected());
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
        addStatus(getContract().STATUS_STACK_ADDRESS + "/" + address.getHostName() + ":" + address.getPort(), stack.isConnected());
        addEvent(getContract().EVENT_STACK_ADDRESS_ADDED, address);
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
            addStatus(topic, null);
            addEvent(getContract().EVENT_STACK_ADDRESS_REMOVED, address);
        }
        for (TinkerforgeDevice device : stack.getDevices()) {
            device.removeListener(this);
            String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
            addStatus(topic, null);
        }
    }

    private void updateDevice(TinkerforgeDevice device) {
        String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        String connection = null;
        try {
            connection = "" + device.isConnected();
        } catch (Exception ex) {
            connection = ex.getMessage();
        }
        addStatus(topic, connection+"updated");
    }

    @Override
    public void connected(TinkerforgeDevice device) {
        String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        addStatus(topic, true);
    }

    @Override
    public void reConnected(TinkerforgeDevice device) {
        String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        addStatus(topic, true);
    }

    @Override
    public void disconnected(TinkerforgeDevice device) {
        String topic = getContract().STATUS_DEVICE + "/" + device.getStack().getStackAddress().getHostName() + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        addStatus(topic, false+"disconnected-device");
    }
}
