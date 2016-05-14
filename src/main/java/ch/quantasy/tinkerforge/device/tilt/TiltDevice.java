/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.tilt;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TiltDevice extends GenericDevice<BrickletTilt, TiltDeviceCallback> {

    

    public TiltDevice(TinkerforgeStackAddress address, BrickletTilt device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addTiltStateListener(super.getCallback());
       
       
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeTiltStateListener(super.getCallback());
    }

    
}
