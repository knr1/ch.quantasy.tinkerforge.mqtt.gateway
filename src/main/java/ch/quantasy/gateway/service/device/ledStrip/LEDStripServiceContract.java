/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.ledStrip;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.led.LEDStripDevice;

/**
 *
 * @author reto
 */
public class LEDStripServiceContract extends DeviceServiceContract {

    public final String LEDs;
    public final String EVENT_LEDs;
    public final String INTENT_LEDs;

    public final String CONFIG;
    public final String STATUS_CONFIG;
    public final String EVENT_CONFIG;
    public final String INTENT_CONFIG;

    public LEDStripServiceContract(LEDStripDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LEDStripServiceContract(String id, String device) {
        super(id, device);
        LEDs = "rgbLEDs";
        EVENT_LEDs = EVENT + "/" + LEDs;
        INTENT_LEDs = INTENT + "/" + LEDs;

        CONFIG = "config";
        STATUS_CONFIG = STATUS + "/" + CONFIG;
        EVENT_CONFIG = EVENT + "/" + CONFIG;
        INTENT_CONFIG = INTENT + "/" + CONFIG;
    }
}
