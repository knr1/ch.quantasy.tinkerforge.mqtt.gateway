/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.barometer;

import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletHumidity;

/**
 *
 * @author reto
 */
public class DeviceAveraging {
    
    private short averagingPressure;
    private short averagingTemperature;
    private short movingAveragePressure;

    public DeviceAveraging() {
    }

    public DeviceAveraging(BrickletBarometer.Averaging averaging) {
        this.averagingPressure = averaging.averagePressure;
        this.averagingTemperature = averaging.averageTemperature;
        this.movingAveragePressure = averaging.movingAveragePressure;
    }

    public short getAveragingPressure() {
        return averagingPressure;
    }

    public short getAveragingTemperature() {
        return averagingTemperature;
    }

    public short getMovingAveragePressure() {
        return movingAveragePressure;
    }
    
    
}
