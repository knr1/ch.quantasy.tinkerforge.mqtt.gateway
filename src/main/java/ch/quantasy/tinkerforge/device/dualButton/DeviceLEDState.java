/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualButton;

import com.tinkerforge.BrickletDualButton;

/**
 *
 * @author reto
 */
public class DeviceLEDState {
    
    public short leftLED;
    public short rightLED;

    public DeviceLEDState() {
    }

    public DeviceLEDState(BrickletDualButton.LEDState ledState) {
        this.leftLED = ledState.ledL;
        this.rightLED = ledState.ledR;
    }
    
}
