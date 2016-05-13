/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.uvLight;

import com.tinkerforge.BrickletUVLight;



/**
 *
 * @author reto
 */
public class DeviceUVLightCallbackThreshold {
    
    private char option;
    private long min;
    private long max;

    public DeviceUVLightCallbackThreshold() {
    }

    public DeviceUVLightCallbackThreshold(char option, long min, long max) {
        this.option = option;
        this.min = min;
        this.max = max;
    }

    public DeviceUVLightCallbackThreshold(BrickletUVLight.UVLightCallbackThreshold threshold) {
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
