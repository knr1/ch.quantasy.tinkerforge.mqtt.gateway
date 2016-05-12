/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.distanceUS;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletDistanceUS;

/**
 *
 * @author reto
 */
public interface DistanceUSDeviceCallback extends DeviceCallback, BrickletDistanceUS.DistanceListener,BrickletDistanceUS.DistanceReachedListener {
    public void debouncePeriodChanged(long period);
    public void distanceCallbackPeriodChanged(long period);
    public void distanceCallbackThresholdChanged(DeviceDistanceCallbackThreshold threshold);
    public void movingAverageChanged(short average);
}
