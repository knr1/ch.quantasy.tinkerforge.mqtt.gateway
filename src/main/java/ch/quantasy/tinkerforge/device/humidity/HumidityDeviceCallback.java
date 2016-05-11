/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.humidity;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletHumidity.AnalogValueCallbackThreshold;
import com.tinkerforge.BrickletHumidity.HumidityCallbackThreshold;

/**
 *
 * @author reto
 */
public interface HumidityDeviceCallback extends DeviceCallback, BrickletHumidity.AnalogValueListener, BrickletHumidity.AnalogValueReachedListener, BrickletHumidity.HumidityListener, BrickletHumidity.HumidityReachedListener {
    public void analogValueCallbackPeriodChanged(long period);
    public void humidityCallbackPeriodChanged(long period);
    public void debouncePeriodChanged(long period);
    public void analogValueCallbackThresholdChanged(AnalogValueCallbackThreshold threshold);
    public void humidityCallbackThresholdChanged(HumidityCallbackThreshold threshold);
}
