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
    public final String EVENT_TOPIC_LEDs;
    public final String INTENT_TOPIC_LEDs;

    public final String CONFIG;
    public final String STATUS_TOPIC_CONFIG;
    public final String EVENT_TOPIC_CONFIG;
    public final String INTENT_TOPIC_CONFIG;

    public LEDStripServiceContract(LEDStripDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LEDStripServiceContract(String id, String device) {
        super(id, device);
        LEDs = "rgbLEDs";
        EVENT_TOPIC_LEDs = EVENT_TOPIC + "/" + LEDs;
        INTENT_TOPIC_LEDs = INTENT_TOPIC + "/" + LEDs;

        CONFIG = "config";
        STATUS_TOPIC_CONFIG = STATUS_TOPIC + "/" + CONFIG;
        EVENT_TOPIC_CONFIG = EVENT_TOPIC + "/" + CONFIG;
        INTENT_TOPIC_CONFIG = INTENT_TOPIC + "/" + CONFIG;
    }
}
