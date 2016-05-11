/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.barometer;

import com.tinkerforge.BrickletBarometer;


/**
 *
 * @author reto
 */
public class DeviceAirPressureCallbackThreshold {
    
    public char option;
    public int min;
    public int max;

    public DeviceAirPressureCallbackThreshold() {
    }

    public DeviceAirPressureCallbackThreshold(BrickletBarometer.AirPressureCallbackThreshold threshold) {
        this.option = threshold.option;
        this.min = threshold.min;
        this.max = threshold.max;
    }
    
}
