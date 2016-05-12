/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualButton;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletDualButton;

/**
 *
 * @author reto
 */
public interface DualButtonDeviceCallback extends DeviceCallback, BrickletDualButton.StateChangedListener {
    public void ledStateChanged(DeviceLEDState state);
}
