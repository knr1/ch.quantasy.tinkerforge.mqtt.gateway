/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.tilt;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;
import ch.quantasy.tinkerforge.device.tilt.TiltDevice;

/**
 *
 * @author reto
 */
public class TiltServiceContract extends DeviceServiceContract {

    public final String TILT_STATE;
    public final String EVENT_TOPIC_TILT_STATE;

    public TiltServiceContract(TiltDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }
    public TiltServiceContract(String id, String device) {
        super(id, device);

        TILT_STATE = "tiltState";
        EVENT_TOPIC_TILT_STATE = EVENT_TOPIC + "/" + TILT_STATE;
    }
}
