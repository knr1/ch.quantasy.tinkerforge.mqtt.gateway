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
    public final String STATUS_ANALOG_VALUE;
    public final String STATUS_ANALOG_VALUE_THRESHOLD;
    public final String STATUS_ANALOG_VALUE_CALLBACK_PERIOD;
    public final String EVENT_ANALOG_VALUE;
    public final String EVENT_ANALOG_VALUE_REACHED;
    public final String INTENT_ANALOG_VALUE;
    public final String INTENT_ANALOG_VALUE_THRESHOLD;
    public final String INTENT_ANALOG_VALUE_CALLBACK_PERIOD;

    public final String HUMIDITY;
    public final String STATUS_HUMIDITY;
    public final String STATUS_HUMIDITY_THRESHOLD;
    public final String STATUS_HUMIDITY_CALLBACK_PERIOD;
    public final String EVENT_HUMIDITY;
    public final String EVENT_HUMIDITY_REACHED;
    public final String INTENT_HUMIDITY;
    public final String INTENT_HUMIDITY_THRESHOLD;
    public final String INTENT_HUMIDITY_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

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
        STATUS_ANALOG_VALUE = STATUS + "/" + ANALOG_VALUE;
        STATUS_ANALOG_VALUE_THRESHOLD = STATUS_ANALOG_VALUE + "/" + THRESHOLD;
        STATUS_ANALOG_VALUE_CALLBACK_PERIOD = STATUS_ANALOG_VALUE + "/" + CALLBACK_PERIOD;
        EVENT_ANALOG_VALUE = EVENT + "/" + ANALOG_VALUE;
        EVENT_ANALOG_VALUE_REACHED = EVENT_ANALOG_VALUE + "/" + REACHED;
        INTENT_ANALOG_VALUE = INTENT + "/" + ANALOG_VALUE;
        INTENT_ANALOG_VALUE_THRESHOLD = INTENT_ANALOG_VALUE + "/" + THRESHOLD;
        INTENT_ANALOG_VALUE_CALLBACK_PERIOD = INTENT_ANALOG_VALUE + "/" + CALLBACK_PERIOD;

        HUMIDITY = "humidity";
        STATUS_HUMIDITY = STATUS + "/" + HUMIDITY;
        STATUS_HUMIDITY_THRESHOLD = STATUS_HUMIDITY + "/" + THRESHOLD;
        STATUS_HUMIDITY_CALLBACK_PERIOD = STATUS_HUMIDITY + "/" + CALLBACK_PERIOD;
        EVENT_HUMIDITY = EVENT + "/" + HUMIDITY;
        EVENT_HUMIDITY_REACHED = EVENT_HUMIDITY + "/" + REACHED;
        INTENT_HUMIDITY = INTENT + "/" + HUMIDITY;
        INTENT_HUMIDITY_THRESHOLD = INTENT_HUMIDITY + "/" + THRESHOLD;
        INTENT_HUMIDITY_CALLBACK_PERIOD = INTENT_HUMIDITY + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;
    }
}
