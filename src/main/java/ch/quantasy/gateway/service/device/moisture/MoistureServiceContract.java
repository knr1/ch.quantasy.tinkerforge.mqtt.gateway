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
    public final String STATUS_TOPIC_MOISTURE;
    public final String STATUS_TOPIC_MOISTURE_THRESHOLD;
    public final String STATUS_TOPIC_MOISTURE_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_MOISTURE;
    public final String EVENT_TOPIC_MOISTURE_REACHED;
    public final String INTENT_TOPIC_MOISTURE;
    public final String INTENT_TOPIC_MOISTURE_THRESHOLD;
    public final String INTENT_TOPIC_MOISTURE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_TOPIC_DEBOUNCE;
    public final String EVENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE_PERIOD;
    public final String STATUS_TOPIC_DEBOUNCE_PERIOD;

    public final String MOVING_AVERAGE;
    public final String STATUS_TOPIC_MOVING_AVERAGE;

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
        STATUS_TOPIC_MOISTURE = STATUS_TOPIC + "/" + MOISTURE;
        STATUS_TOPIC_MOISTURE_THRESHOLD = STATUS_TOPIC_MOISTURE + "/" + THRESHOLD;
        STATUS_TOPIC_MOISTURE_CALLBACK_PERIOD = STATUS_TOPIC_MOISTURE + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_MOISTURE = EVENT_TOPIC + "/" + MOISTURE;
        EVENT_TOPIC_MOISTURE_REACHED = EVENT_TOPIC_MOISTURE + REACHED_TOPIC;
        INTENT_TOPIC_MOISTURE = INTENT_TOPIC + "/" + MOISTURE;
        INTENT_TOPIC_MOISTURE_THRESHOLD = INTENT_TOPIC_MOISTURE + "/" + THRESHOLD;
        INTENT_TOPIC_MOISTURE_CALLBACK_PERIOD = INTENT_TOPIC_MOISTURE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_TOPIC_DEBOUNCE = STATUS_TOPIC + "/" + DEBOUNCE;
        STATUS_TOPIC_DEBOUNCE_PERIOD = STATUS_TOPIC_DEBOUNCE + "/" + PERIOD;
        EVENT_TOPIC_DEBOUNCE = EVENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE = INTENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE_PERIOD = INTENT_TOPIC_DEBOUNCE + "/" + PERIOD;

        MOVING_AVERAGE = "movingAverage";
        STATUS_TOPIC_MOVING_AVERAGE = STATUS_TOPIC + "/" + MOVING_AVERAGE;
    }
}
