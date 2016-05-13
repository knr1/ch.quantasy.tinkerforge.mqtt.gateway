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
    
    private char option;
    private long min;
    private long max;

    public DeviceIlluminanceCallbackThreshold() {
    }

    public DeviceIlluminanceCallbackThreshold(char option, long min, long max) {
        this.option = option;
        this.min = min;
        this.max = max;
    }

    public DeviceIlluminanceCallbackThreshold(BrickletAmbientLightV2.IlluminanceCallbackThreshold threshold) {
        this(threshold.option,threshold.min,threshold.max);
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
