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

import ch.quantasy.gateway.service.AbstractService;
import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceListener;
import ch.quantasy.tinkerforge.factory.TinkerforgeStackFactory;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.gateway.binding.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 */
public class TinkerForgeManager implements TinkerforgeDeviceListener {

    private final Set<TinkerforgeFactoryListener> listeners;

    private final TinkerforgeStackFactory stackFactory;

    private final URI mqttURI;

    public TinkerForgeManager(URI mqttURI) {
        this.mqttURI = mqttURI;
        stackFactory = TinkerforgeStackFactory.getInstance();
        this.listeners = new HashSet<>();
    }

    public void addListener(TinkerforgeFactoryListener listener) {
        this.listeners.add(listener);

    }

    public void removeListener(TinkerforgeFactoryListener listener) {
        this.listeners.remove(listener);
    }

    public void addStack(TinkerforgeStackAddress address) {
        TinkerforgeStack stack = null;
        if (stackFactory.addStack(address)) {
            stack = stackFactory.getStack(address);
            for (TinkerforgeFactoryListener listener : listeners) {
                listener.stackAdded(stack);
            }
            stack.addListener(this);
            stack.connect();
        }
    }

    public void removeStack(TinkerforgeStackAddress address) {
        TinkerforgeStack stack = stackFactory.removeStack(address);
        if (stack != null) {
            stack.disconnect();
            for (TinkerforgeDevice device : stack.getDevices()) {
                this.managedServices.remove(device).quit();
            }
            for (TinkerforgeFactoryListener listener : listeners) {
                listener.stackRemoved(stack);
            }
            stack.removeListener(this);
        }
    }

    public Set<TinkerforgeStackAddress> getStackAddresses() {
        Set<TinkerforgeStackAddress> stackAddresses = new HashSet<>();
        for (TinkerforgeStack stack : stackFactory.getTinkerforgeStacks()) {
            stackAddresses.add(stack.getStackAddress());
        }
        return stackAddresses;
    }

    public TinkerforgeStackFactory getStackFactory() {
        return stackFactory;
    }

    //private Set<TinkerforgeDevice> managedDevices = new HashSet<>();
    //private Set<AbstractService> services = new HashSet<>();
    private Map<TinkerforgeDevice, AbstractService> managedServices = new HashMap<>();

    @Override
    public void connected(TinkerforgeDevice tinkerforgeDevice) {
        if (managedServices.containsKey(tinkerforgeDevice)) {
            return;
        }
        try {
            AbstractService service = TinkerforgeServiceMapper.getService(tinkerforgeDevice, mqttURI);
            if (service != null) {
                managedServices.put(tinkerforgeDevice, service);
            }
        } catch (Exception ex) {
            Logger.getLogger(TinkerForgeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reConnected(TinkerforgeDevice tinkerforgeDevice) {
        //Nothing to do
    }

    @Override
    public void disconnected(TinkerforgeDevice tinkerforgeDevice) {
        //Nothing to do 
    }
}
