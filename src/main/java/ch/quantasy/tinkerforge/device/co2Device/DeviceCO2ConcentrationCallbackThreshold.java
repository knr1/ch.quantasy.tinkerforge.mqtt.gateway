/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.co2Device;

import com.tinkerforge.BrickletCO2;


/**
 *
 * @author reto
 */
public class DeviceCO2ConcentrationCallbackThreshold {
    
    public char option;
    public int min;
    public int max;

    public DeviceCO2ConcentrationCallbackThreshold() {
    }

    public DeviceCO2ConcentrationCallbackThreshold(BrickletCO2.CO2ConcentrationCallbackThreshold threshold) {
        this.option = threshold.option;
        this.min = threshold.min;
        this.max = threshold.max;
    }
    
}
