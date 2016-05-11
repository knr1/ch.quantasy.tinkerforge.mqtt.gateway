/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualButton;
import ch.quantasy.tinkerforge.device.dualRelay.*;
import ch.quantasy.tinkerforge.device.remoteSwitch.*;
import ch.quantasy.tinkerforge.device.moisture.*;
import com.tinkerforge.BrickletMoisture;

/**
 *
 * @author reto
 */
public class DeviceSelectedLEDStateParameters {
    
    private short led;
    private short state;

    public DeviceSelectedLEDStateParameters() {
    }

    public DeviceSelectedLEDStateParameters(short led, short state) {
        this.led = led;
        this.state = state;
    }

    public short getLed() {
        return led;
    }

    public short getState() {
        return state;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.led;
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
        final DeviceSelectedLEDStateParameters other = (DeviceSelectedLEDStateParameters) obj;
        if (this.led != other.led) {
            return false;
        }
        return true;
    }
    
}
