/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.remoteSwitch;

/**
 *
 * @author reto
 */
public class SwitchSocketBParameters {
    public static enum SwitchTo{
        ON((short)1),OFF((short)0);
        private short value;
        private SwitchTo(short value){
            this.value=value;
        }

        public short getValue() {
            return value;
        }
    }
    private long address;
    private short unit;
    private SwitchTo switchingValue;

    public SwitchSocketBParameters() {
    }
    public SwitchSocketBParameters(long address, short unit, String switchingValue) {
        this(address, unit, SwitchTo.valueOf(switchingValue));
    }

    public SwitchSocketBParameters(long address, short unit, SwitchTo switchingValue) {
        this.address = address;
        this.unit = unit;
        this.switchingValue = switchingValue;
    }

    public long getAddress() {
        return address;
    }

    public SwitchTo getSwitchingValue() {
        return switchingValue;
    }

    public short getUnit() {
        return unit;
    }
    
    
    
}
