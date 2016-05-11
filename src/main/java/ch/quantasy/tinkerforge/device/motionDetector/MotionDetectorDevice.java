/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.motionDetector;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MotionDetectorDevice extends GenericDevice<BrickletMotionDetector, MotionDetectorDeviceCallback> {

    

    public MotionDetectorDevice(TinkerforgeStackAddress address, BrickletMotionDetector device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addDetectionCycleEndedListener(super.getCallback());
        getDevice().addMotionDetectedListener(super.getCallback());
       
       
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeDetectionCycleEndedListener(super.getCallback());
        getDevice().removeMotionDetectedListener(super.getCallback());
    }

    
}
