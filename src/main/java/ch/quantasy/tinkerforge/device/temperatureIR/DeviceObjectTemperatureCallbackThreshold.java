/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.temperatureIR;

import com.tinkerforge.BrickletTemperatureIR;


/**
 *
 * @author reto
 */
public class DeviceObjectTemperatureCallbackThreshold {
    
    public char option;
    public short min;
    public short max;

    public DeviceObjectTemperatureCallbackThreshold() {
    }

    public DeviceObjectTemperatureCallbackThreshold(char option, short min, short max) {
        this.option = option;
        this.min = min;
        this.max = max;
    }
    
    

    public DeviceObjectTemperatureCallbackThreshold(BrickletTemperatureIR.ObjectTemperatureCallbackThreshold threshold) {
        this(threshold.option,threshold.min,threshold.max);
    }
    
}
