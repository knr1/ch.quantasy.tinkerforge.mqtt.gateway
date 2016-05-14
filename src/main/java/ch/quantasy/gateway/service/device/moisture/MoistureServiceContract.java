/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.moisture;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.led.LEDStripDevice;
import ch.quantasy.tinkerforge.device.moisture.MoistureDevice;

/**
 *
 * @author reto
 */
public class MoistureServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String REACHED_TOPIC;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String MOISTURE;
    public final String STATUS_MOISTURE;
    public final String STATUS_MOISTURE_THRESHOLD;
    public final String STATUS_MOISTURE_CALLBACK_PERIOD;
    public final String EVENT_MOISTURE;
    public final String EVENT_MOISTURE_REACHED;
    public final String INTENT_MOISTURE;
    public final String INTENT_MOISTURE_THRESHOLD;
    public final String INTENT_MOISTURE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;

    public MoistureServiceContract(MoistureDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }
    public MoistureServiceContract(String id, String device) {
        super(id, device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        REACHED_TOPIC = "/" + REACHED;

        MOISTURE = "moisture";
        STATUS_MOISTURE = STATUS + "/" + MOISTURE;
        STATUS_MOISTURE_THRESHOLD = STATUS_MOISTURE + "/" + THRESHOLD;
        STATUS_MOISTURE_CALLBACK_PERIOD = STATUS_MOISTURE + "/" + CALLBACK_PERIOD;
        EVENT_MOISTURE = EVENT + "/" + MOISTURE;
        EVENT_MOISTURE_REACHED = EVENT_MOISTURE + REACHED_TOPIC;
        INTENT_MOISTURE = INTENT + "/" + MOISTURE;
        INTENT_MOISTURE_THRESHOLD = INTENT_MOISTURE + "/" + THRESHOLD;
        INTENT_MOISTURE_CALLBACK_PERIOD = INTENT_MOISTURE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        MOVING_AVERAGE = "movingAverage";
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;
    }
}
