/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dustDetector;

import com.tinkerforge.BrickletDustDetector;




/**
 *
 * @author reto
 */
public class DeviceDustDensityCallbackThreshold {
    
    private char option;
    private int min;
    private int max;

    public DeviceDustDensityCallbackThreshold() {
    }

    public DeviceDustDensityCallbackThreshold(char option, int min, int max) {
        this.option = option;
        this.min = min;
        this.max = max;
    }

    public DeviceDustDensityCallbackThreshold(BrickletDustDetector.DustDensityCallbackThreshold threshold) {
        this(threshold.option,threshold.min,threshold.max);
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
