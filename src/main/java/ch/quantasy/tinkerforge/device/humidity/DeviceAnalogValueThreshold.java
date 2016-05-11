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
public class DeviceAnalogValueThreshold {
    
    public char option;
    public int min;
    public int max;

    public DeviceAnalogValueThreshold() {
    }

    public DeviceAnalogValueThreshold(BrickletHumidity.AnalogValueCallbackThreshold threshold) {
        this.option = threshold.option;
        this.min = threshold.min;
        this.max = threshold.max;
    }
    
}
