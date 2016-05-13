/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.ambientLightV2;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletAmbientLightV2.IlluminanceCallbackThreshold;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.BrickletAmbientLightV2.Configuration;

/**
 *
 * @author reto
 */
public interface AmbientLightV2DeviceCallback extends DeviceCallback, BrickletAmbientLightV2.IlluminanceListener, BrickletAmbientLightV2.IlluminanceReachedListener {
    public void debouncePeriodChanged(long period);
    public void illuminanceCallbackPeriodChanged(long period);
    public void illuminanceCallbackThresholdChanged(DeviceIlluminanceCallbackThreshold threshold);
    public void configurationChanged(DeviceConfiguration average);
}
