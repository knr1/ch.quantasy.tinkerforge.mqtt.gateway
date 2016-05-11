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
public class SwitchSocketCParameters {
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
    private char systemCode;
    private short deviceCode;
    private SwitchTo switchingValue;

    public SwitchSocketCParameters() {
    }
    public SwitchSocketCParameters(char systemCode, short deviceCode, String switchingValue) {
        this(systemCode, deviceCode, SwitchTo.valueOf(switchingValue));
    }

    public SwitchSocketCParameters(char systemCode, short deviceCode, SwitchTo switchingValue) {
        this.systemCode = systemCode;
        this.deviceCode = deviceCode;
        this.switchingValue = switchingValue;
    }

    public short getDeviceCode() {
        return deviceCode;
    }

    public SwitchTo getSwitchingValue() {
        return switchingValue;
    }

    public char getSystemCode() {
        return systemCode;
    }

    

    
    
    
    
}
