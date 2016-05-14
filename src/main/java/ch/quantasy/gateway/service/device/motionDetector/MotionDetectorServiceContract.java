/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.motionDetector;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.moisture.MoistureDevice;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;

/**
 *
 * @author reto
 */
public class MotionDetectorServiceContract extends DeviceServiceContract {

    public final String DETECTION_CYCLE_ENDED;
    public final String EVENT_DETECTION_CYCLE_ENDED;

    public final String MOTION_DETECTED;
    public final String EVENT_MOTION_DETECTED;

    public MotionDetectorServiceContract(MotionDetectorDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }
    public MotionDetectorServiceContract(String id, String device) {
        super(id, device);

        DETECTION_CYCLE_ENDED = "eventDetectionCycleEnded";
        EVENT_DETECTION_CYCLE_ENDED = EVENT + "/" + DETECTION_CYCLE_ENDED;

        MOTION_DETECTED = "motionDetected";
        EVENT_MOTION_DETECTED = EVENT + "/" + MOTION_DETECTED;
    }
}
