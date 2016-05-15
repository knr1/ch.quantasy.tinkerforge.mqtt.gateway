/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualButton;

/**
 *
 * @author reto
 */
public class DeviceSelectedLEDStateParameters {
    
    private short led;
    private LEDState state;

    public DeviceSelectedLEDStateParameters() {
    }

    public DeviceSelectedLEDStateParameters(short led, LEDState state){
        this.led = led;
        this.state = state;
    }
    public DeviceSelectedLEDStateParameters(short led, short state)throws IllegalArgumentException {
        this(led,LEDState.getLEDStateFor(state));
    }

    public short getLed() {
        return led;
    }

    public LEDState getState() {
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
