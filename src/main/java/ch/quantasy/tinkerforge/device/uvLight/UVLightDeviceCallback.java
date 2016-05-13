/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.uvLight;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletUVLight;


/**
 *
 * @author reto
 */
public interface UVLightDeviceCallback extends DeviceCallback, BrickletUVLight.UVLightListener, BrickletUVLight.UVLightReachedListener{
    public void debouncePeriodChanged(long period);
    public void uvLightCallbackPeriodChanged(long period);
    public void uvLightCallbackThresholdChanged(DeviceUVLightCallbackThreshold threshold);
}
