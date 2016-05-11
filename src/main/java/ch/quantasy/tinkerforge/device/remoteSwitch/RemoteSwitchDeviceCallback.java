/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.remoteSwitch;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletRemoteSwitch;

/**
 *
 * @author reto
 */
public interface RemoteSwitchDeviceCallback extends DeviceCallback, BrickletRemoteSwitch.SwitchingDoneListener {
    public void repeatsChanged(short repeats);
}
