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
        this(averaging.averagePressure,averaging.averageTemperature,averaging.movingAveragePressure);
    }

    public DeviceAveraging(short averagingPressure, short averagingTemperature, short movingAveragePressure) {
        this.averagingPressure = averagingPressure;
        this.averagingTemperature = averagingTemperature;
        this.movingAveragePressure = movingAveragePressure;
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
