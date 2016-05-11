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
public class DeviceHumidityThreshold {
    
    public char option;
    public int min;
    public int max;

    public DeviceHumidityThreshold() {
    }

    public DeviceHumidityThreshold(BrickletHumidity.HumidityCallbackThreshold threshold) {
        this.option = threshold.option;
        this.min = threshold.min;
        this.max = threshold.max;
    }
    
}
