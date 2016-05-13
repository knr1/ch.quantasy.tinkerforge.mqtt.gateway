/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.humidity;

import com.tinkerforge.BrickletHumidity;

/**
 *
 * @author reto
 */
public class DeviceAnalogValueCallbackThreshold {

    private char option;
    private int min;
    private int max;

    public DeviceAnalogValueCallbackThreshold() {
    }

    public DeviceAnalogValueCallbackThreshold(BrickletHumidity.AnalogValueCallbackThreshold threshold) {
        this(threshold.option, threshold.min, threshold.max);
    }

    public DeviceAnalogValueCallbackThreshold(char option, int min, int max) {
        this.option = option;
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public char getOption() {
        return option;
    }
    
    

}
