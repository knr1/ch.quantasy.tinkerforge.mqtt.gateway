/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.tilt;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletTilt;

/**
 *
 * @author reto
 */
public interface TiltDeviceCallback extends DeviceCallback, BrickletTilt.TiltStateListener {
    
}
