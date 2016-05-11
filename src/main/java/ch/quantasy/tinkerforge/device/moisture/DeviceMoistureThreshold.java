/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.moisture;
import com.tinkerforge.BrickletMoisture;

/**
 *
 * @author reto
 */
public class DeviceMoistureThreshold {
    
    public char option;
    public int min;
    public int max;

    public DeviceMoistureThreshold() {
    }

    public DeviceMoistureThreshold(BrickletMoisture.MoistureCallbackThreshold threshold) {
        this.option = threshold.option;
        this.min = threshold.min;
        this.max = threshold.max;
    }
    
}
