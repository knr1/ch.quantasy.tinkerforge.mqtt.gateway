/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.temperatureIR;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletTemperatureIR;

/**
 *
 * @author reto
 */
public interface TemperatureIRDeviceCallback extends DeviceCallback, BrickletTemperatureIR.AmbientTemperatureListener, BrickletTemperatureIR.AmbientTemperatureReachedListener,BrickletTemperatureIR.ObjectTemperatureListener,BrickletTemperatureIR.ObjectTemperatureReachedListener{
    public void objectTemperatureCallbackPeriodChanged(long period);

    public void ambientTemperatureCallbackPeriodChanged(long period);

    public void debouncePeriodChanged(long period);

    public void objectTemperatureCallbackThresholdChanged(DeviceObjectTemperatureCallbackThreshold threshold);

    public void ambientTemperatureCallbackThresholdChanged(DeviceAmbientTemperatureCallbackThreshold threshold);
}
