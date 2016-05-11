/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.led;

import ch.quantasy.tinkerforge.device.humidity.*;
import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletHumidity.AnalogValueCallbackThreshold;
import com.tinkerforge.BrickletHumidity.HumidityCallbackThreshold;

/**
 *
 * @author reto
 */
public interface LEDStripDeviceCallback extends DeviceCallback{
    public void configurationChanged(LEDStripDeviceConfig config);
    public short[][] getLEDsToPublish();
}
