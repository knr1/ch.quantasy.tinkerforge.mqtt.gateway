/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.co2;

import com.tinkerforge.BrickletCO2;


/**
 *
 * @author reto
 */
public class DeviceCO2ConcentrationCallbackThreshold {
    
    private char option;
    private int min;
    private int max;

    public DeviceCO2ConcentrationCallbackThreshold() {
    }

    public DeviceCO2ConcentrationCallbackThreshold(char option, int min, int max) {
        this.option = option;
        this.min = min;
        this.max = max;
    }

    public DeviceCO2ConcentrationCallbackThreshold(BrickletCO2.CO2ConcentrationCallbackThreshold threshold) {
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
