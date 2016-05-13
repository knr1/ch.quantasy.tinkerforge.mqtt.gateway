/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.ambientLight;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLight.AnalogValueCallbackThreshold;
import com.tinkerforge.BrickletAmbientLight.IlluminanceCallbackThreshold;

/**
 *
 * @author reto
 */
public interface AmbientLightDeviceCallback extends DeviceCallback, BrickletAmbientLight.AnalogValueListener, BrickletAmbientLight.AnalogValueReachedListener, BrickletAmbientLight.IlluminanceListener, BrickletAmbientLight.IlluminanceReachedListener {

    public void illuminanceCallbackPeriodChanged(long period);

    public void analogValueCallbackPeriodChanged(long period);

    public void debouncePeriodChanged(long period);

    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold);

    public void illuminanceCallbackThresholdChanged(DeviceIlluminanceCallbackThreshold threshold);
}
