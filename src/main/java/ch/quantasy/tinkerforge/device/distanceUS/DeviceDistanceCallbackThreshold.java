/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.distanceUS;

import com.tinkerforge.BrickletDistanceUS;



/**
 *
 * @author reto
 */
public class DeviceDistanceCallbackThreshold {
    
    public char option;
    public int min;
    public int max;

    public DeviceDistanceCallbackThreshold() {
    }

    public DeviceDistanceCallbackThreshold(BrickletDistanceUS.DistanceCallbackThreshold threshold) {
        this.option = threshold.option;
        this.min = threshold.min;
        this.max = threshold.max;
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
