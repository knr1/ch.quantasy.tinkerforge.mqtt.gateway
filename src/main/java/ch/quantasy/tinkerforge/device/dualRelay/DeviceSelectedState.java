/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualRelay;
import ch.quantasy.tinkerforge.device.remoteSwitch.*;
import ch.quantasy.tinkerforge.device.moisture.*;
import com.tinkerforge.BrickletMoisture;

/**
 *
 * @author reto
 */
public class DeviceSelectedState {
    
    private short relay;
    private boolean state;

    private DeviceSelectedState() {
    }

    public DeviceSelectedState(short relay, boolean state) {
        this.relay = relay;
        this.state = state;
    }

    public short getRelay() {
        return relay;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.relay;
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
        final DeviceSelectedState other = (DeviceSelectedState) obj;
        if (this.relay != other.relay) {
            return false;
        }
        return true;
    }
    
}
