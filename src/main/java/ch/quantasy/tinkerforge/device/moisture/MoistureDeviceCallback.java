/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.moisture;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletMoisture;

/**
 *
 * @author reto
 */
public interface MoistureDeviceCallback extends DeviceCallback, BrickletMoisture.MoistureListener, BrickletMoisture.MoistureReachedListener {
    public void debouncePeriodChanged(long period);
    public void moistureCallbackPeriodChanged(long period);
    public void moistureCallbackThresholdChanged(DeviceMoistureCallbackThreshold threshold);
    public void movingAverageChanged(short average);
}
