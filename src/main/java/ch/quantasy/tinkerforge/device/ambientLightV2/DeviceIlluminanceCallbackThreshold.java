/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.ambientLightV2;

import com.tinkerforge.BrickletAmbientLightV2;


/**
 *
 * @author reto
 */
public class DeviceIlluminanceCallbackThreshold {
    
    public char option;
    public long min;
    public long max;

    public DeviceIlluminanceCallbackThreshold() {
    }

    public DeviceIlluminanceCallbackThreshold(BrickletAmbientLightV2.IlluminanceCallbackThreshold threshold) {
        this.option = threshold.option;
        this.min = threshold.min;
        this.max = threshold.max;
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }

    public char getOption() {
        return option;
    }
    
    
}
