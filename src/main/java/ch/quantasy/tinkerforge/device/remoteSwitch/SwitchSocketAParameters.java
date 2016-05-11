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
public class SwitchSocketAParameters {
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
    private short houseCode;
    private short receiverCode;
    private SwitchTo switchingValue;

    public SwitchSocketAParameters() {
    }
    public SwitchSocketAParameters(short houseCode, short receiverCode, String switchingValue) {
        this(houseCode, receiverCode, SwitchTo.valueOf(switchingValue));
    }

    public SwitchSocketAParameters(short houseCode, short receiverCode, SwitchTo switchingValue) {
        this.houseCode = houseCode;
        this.receiverCode = receiverCode;
        this.switchingValue = switchingValue;
    }

    public short getHouseCode() {
        return houseCode;
    }

    public short getReceiverCode() {
        return receiverCode;
    }

    public SwitchTo getSwitchingValue() {
        return switchingValue;
    }

    
    
    
    
}
