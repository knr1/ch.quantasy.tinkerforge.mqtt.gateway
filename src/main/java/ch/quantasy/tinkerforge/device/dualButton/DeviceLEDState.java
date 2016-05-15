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

    public LEDState led1;
    public LEDState led2;

    public DeviceLEDState() {
    }

    public DeviceLEDState(LEDState led1, LEDState led2) {
        this.led1 = led1;
        this.led2 = led2;
    }

    public DeviceLEDState(short ledL, short ledR) throws IllegalArgumentException {
        this(LEDState.getLEDStateFor(ledL), LEDState.getLEDStateFor(ledR));
    }

    public DeviceLEDState(BrickletDualButton.LEDState ledState) {
        this(ledState.ledL, ledState.ledR);
    }

}
