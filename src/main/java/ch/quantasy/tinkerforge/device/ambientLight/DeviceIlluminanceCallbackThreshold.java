/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.ambientLight;

import com.tinkerforge.BrickletAmbientLight;

/**
 *
 * @author reto
 */
public class DeviceIlluminanceCallbackThreshold {
    
    public char option;
    public int min;
    public int max;

    public DeviceIlluminanceCallbackThreshold() {
    }

    public DeviceIlluminanceCallbackThreshold(BrickletAmbientLight.IlluminanceCallbackThreshold threshold) {
        this.option = threshold.option;
        this.min = threshold.min;
        this.max = threshold.max;
    }
}
