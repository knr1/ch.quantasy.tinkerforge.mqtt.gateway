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
package ch.quantasy.tinkerforge.device;

import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.Device;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 */
public class TinkerforgeDevice<D extends Device> {

    private final Set<TinkerforgeDeviceListener> deviceListeners;
    private IPConnection connection;
    private final TinkerforgeStack stack;
    public transient D device;
    private final String uid;
    private final char position;
    private final short[] firmwareVersion;
    private final short[] hardwareVersion;

    public TinkerforgeDevice(TinkerforgeStack stack, D device) throws NotConnectedException, TimeoutException {
        this.deviceListeners = new HashSet<>();
        this.stack = stack;
        this.connection = stack.getIpConnection();
        this.device = device;
        this.uid = device.getIdentity().uid;
        this.position = device.getIdentity().position;
        this.firmwareVersion = device.getIdentity().firmwareVersion;
        this.hardwareVersion = device.getIdentity().hardwareVersion;
        //this.device.setResponseExpectedAll(true);
    }

    public TinkerforgeStack getStack() {
        return stack;
    }

    public D getDevice() {
        return device;
    }

    public String getUid() {
        return uid;
    }

    public char getPosition() {
        return position;
    }

    public short[] getFirmwareVersion() {
        return firmwareVersion;
    }

    public short[] getHardwareVersion() {
        return hardwareVersion;
    }

    /**
     *
     * @param device
     * @return Old device which is no longer in use
     * @throws IllegalArgumentException if new device is null or if new device
     * does not carry the same uid
     * @throws TimeoutException
     * @throws NotConnectedException
     */
    public D updateDevice(D device) throws IllegalArgumentException, TimeoutException, NotConnectedException {
        if (device == null || !device.getIdentity().uid.equals(this.uid)) {
            throw new IllegalArgumentException();
        }
        this.connection = getStack().getIpConnection();
        D oldDevice = this.device;
        this.device = device;
        //this.device.setResponseExpectedAll(true);
        return oldDevice;
    }

    public IPConnection getIPConnection() {
        return connection;
    }

    public boolean isConnected() throws TimeoutException, NotConnectedException {
        if (device == null) {
            return false;
        }
        return device.getIdentity() != null;

    }

    /**
     *
     */
    public void connected() {
        for (TinkerforgeDeviceListener listener : deviceListeners) {
            listener.connected(this);
        }

    }

    public void disconnected() {
        for (TinkerforgeDeviceListener listener : deviceListeners) {
            listener.disconnected(this);
        }

    }

    public void reconnected() {
        for (TinkerforgeDeviceListener listener : deviceListeners) {
            listener.reConnected(this);
        }

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.stack);
        hash = 61 * hash + Objects.hashCode(this.uid);
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
        final TinkerforgeDevice other = (TinkerforgeDevice) obj;
        if (!Objects.equals(this.uid, other.uid)) {
            return false;
        }
        return Objects.equals(this.stack, other.stack);
    }

    public void addListener(TinkerforgeDeviceListener listener) {
        this.deviceListeners.add(listener);
        boolean isConnected = false;
        try {
            isConnected = isConnected();
        } catch (Exception ex) {
            Logger.getLogger(TinkerforgeDevice.class.getName()).log(Level.SEVERE, null, ex);
            if (isConnected) {
                listener.connected(this);
            } else {
                listener.disconnected(this);
            }
        }
    }

    public void removeListener(TinkerforgeDeviceListener listener) {
        this.deviceListeners.remove(listener);
    }

    @Override
    public String toString() {
        return "---\n"
                + "address: " + stack + "\n"
                + "device: " + device + "\n"
                + "uid: " + uid + "\n"
                + "position: " + position + "\n"
                + "firmware: " + firmwareVersion + "\n"
                + "hardware: " + hardwareVersion + "\n"
                + "...";
    }

}
