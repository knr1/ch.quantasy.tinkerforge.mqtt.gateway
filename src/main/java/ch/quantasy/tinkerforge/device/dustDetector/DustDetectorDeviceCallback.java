/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dustDetector;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletDustDetector;

/**
 *
 * @author reto
 */
public interface DustDetectorDeviceCallback extends DeviceCallback, BrickletDustDetector.DustDensityListener, BrickletDustDetector.DustDensityReachedListener {

   
    public void dustDensityCallbackPeriodChanged(long period);

    public void debouncePeriodChanged(long period);

    public void dustDensityCallbackThresholdChanged(DeviceDustDensityCallbackThreshold threshold);
    
    public void movingAverageChanged(short movingAverage);

   }
