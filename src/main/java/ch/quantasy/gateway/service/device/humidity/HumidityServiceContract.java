/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.humidity;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;

/**
 *
 * @author reto
 */
public class HumidityServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String ANALOG_VALUE;
    public final String STATUS_TOPIC_ANALOG_VALUE;
    public final String STATUS_TOPIC_ANALOG_VALUE_THRESHOLD;
    public final String STATUS_TOPIC_ANALOG_VALUE_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_ANALOG_VALUE;
    public final String EVENT_TOPIC_ANALOG_VALUE_REACHED;
    public final String INTENT_TOPIC_ANALOG_VALUE;
    public final String INTENT_TOPIC_ANALOG_VALUE_THRESHOLD;
    public final String INTENT_TOPIC_ANALOG_VALUE_CALLBACK_PERIOD;

    public final String HUMIDITY;
    public final String STATUS_TOPIC_HUMIDITY;
    public final String STATUS_TOPIC_HUMIDITY_THRESHOLD;
    public final String STATUS_TOPIC_HUMIDITY_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_HUMIDITY;
    public final String EVENT_TOPIC_HUMIDITY_REACHED;
    public final String INTENT_TOPIC_HUMIDITY;
    public final String INTENT_TOPIC_HUMIDITY_THRESHOLD;
    public final String INTENT_TOPIC_HUMIDITY_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_TOPIC_DEBOUNCE;
    public final String EVENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE_PERIOD;
    public final String STATUS_TOPIC_DEBOUNCE_PERIOD;

    public HumidityServiceContract(HumidityDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public HumidityServiceContract(String id, String device) {
        super(id, device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        ANALOG_VALUE = "analogValue";
        STATUS_TOPIC_ANALOG_VALUE = STATUS_TOPIC + "/" + ANALOG_VALUE;
        STATUS_TOPIC_ANALOG_VALUE_THRESHOLD = STATUS_TOPIC_ANALOG_VALUE + "/" + THRESHOLD;
        STATUS_TOPIC_ANALOG_VALUE_CALLBACK_PERIOD = STATUS_TOPIC_ANALOG_VALUE + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_ANALOG_VALUE = EVENT_TOPIC + "/" + ANALOG_VALUE;
        EVENT_TOPIC_ANALOG_VALUE_REACHED = EVENT_TOPIC_ANALOG_VALUE + "/" + REACHED;
        INTENT_TOPIC_ANALOG_VALUE = INTENT_TOPIC + "/" + ANALOG_VALUE;
        INTENT_TOPIC_ANALOG_VALUE_THRESHOLD = INTENT_TOPIC_ANALOG_VALUE + "/" + THRESHOLD;
        INTENT_TOPIC_ANALOG_VALUE_CALLBACK_PERIOD = INTENT_TOPIC_ANALOG_VALUE + "/" + CALLBACK_PERIOD;

        HUMIDITY = "humidity";
        STATUS_TOPIC_HUMIDITY = STATUS_TOPIC + "/" + HUMIDITY;
        STATUS_TOPIC_HUMIDITY_THRESHOLD = STATUS_TOPIC_HUMIDITY + "/" + THRESHOLD;
        STATUS_TOPIC_HUMIDITY_CALLBACK_PERIOD = STATUS_TOPIC_HUMIDITY + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_HUMIDITY = EVENT_TOPIC + "/" + HUMIDITY;
        EVENT_TOPIC_HUMIDITY_REACHED = EVENT_TOPIC_HUMIDITY + "/" + REACHED;
        INTENT_TOPIC_HUMIDITY = INTENT_TOPIC + "/" + HUMIDITY;
        INTENT_TOPIC_HUMIDITY_THRESHOLD = INTENT_TOPIC_HUMIDITY + "/" + THRESHOLD;
        INTENT_TOPIC_HUMIDITY_CALLBACK_PERIOD = INTENT_TOPIC_HUMIDITY + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_TOPIC_DEBOUNCE = STATUS_TOPIC + "/" + DEBOUNCE;
        STATUS_TOPIC_DEBOUNCE_PERIOD = STATUS_TOPIC_DEBOUNCE + "/" + PERIOD;
        EVENT_TOPIC_DEBOUNCE = EVENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE = INTENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE_PERIOD = INTENT_TOPIC_DEBOUNCE + "/" + PERIOD;
    }
}
