/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device;

import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.Device;
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

    private Set<TinkerforgeDeviceListener> deviceListeners;

    private final TinkerforgeStackAddress address;
    private transient D device;
    private final String uid;
    private final char position;
    private final short[] firmwareVersion;
    private final short[] hardwareVersion;

    public TinkerforgeDevice(TinkerforgeStackAddress address, D device) throws NotConnectedException, TimeoutException {
        this.deviceListeners = new HashSet<>();
        this.address = address;
        this.device = device;
        this.uid = device.getIdentity().uid;
        this.position = device.getIdentity().position;
        this.firmwareVersion = device.getIdentity().firmwareVersion;
        this.hardwareVersion = device.getIdentity().hardwareVersion;
    }

    public TinkerforgeStackAddress getAddress() {
        return address;
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

    public void setDevice(D device) throws TimeoutException, NotConnectedException {
        if (device == null || device.getIdentity().uid != this.uid) {
            return;
        }
        this.device = device;
    }

    public boolean isConnected() {
        if (device == null) {
            return false;
        }

        try {
            device.getIdentity();
            return true;
        } catch (TimeoutException | NotConnectedException ex) {
            // Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

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
        hash = 61 * hash + Objects.hashCode(this.address);
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
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

    public void addListener(TinkerforgeDeviceListener listener) {
        this.deviceListeners.add(listener);
        if (isConnected()) {
            listener.connected(this);
        } else {
            listener.disconnected(this);
        }
    }

    public void removeListener(TinkerforgeDeviceListener listener) {
        this.deviceListeners.remove(listener);
    }

    @Override
    public String toString() {
        return "---\n"
                + "address: " + address + "\n"
                + "device: " + device + "\n"
                + "uid: " + uid + "\n"
                + "position: " + position + "\n"
                + "firmware: " + firmwareVersion + "\n"
                + "hardware: " + hardwareVersion + "\n"
                + "...";
    }

}
