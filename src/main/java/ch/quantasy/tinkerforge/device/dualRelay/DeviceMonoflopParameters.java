/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualRelay;

/**
 *
 * @author reto
 */
public class DeviceMonoflopParameters {
    
    private short relay;
    private boolean state;
    private long period;

    private DeviceMonoflopParameters() {
    }

    public DeviceMonoflopParameters(short relay, boolean state, long period) {
        this.relay = relay;
        this.state = state;
        this.period = period;
    }

    public short getRelay() {
        return relay;
    }

    public long getPeriod() {
        return period;
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
        final DeviceMonoflopParameters other = (DeviceMonoflopParameters) obj;
        if (this.relay != other.relay) {
            return false;
        }
        return true;
    }
    
}
