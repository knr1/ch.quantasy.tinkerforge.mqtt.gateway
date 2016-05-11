/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.motionDetector;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletMotionDetector;

/**
 *
 * @author reto
 */
public interface MotionDetectorDeviceCallback extends DeviceCallback, BrickletMotionDetector.DetectionCycleEndedListener, BrickletMotionDetector.MotionDetectedListener {
    
}
