/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.distanceIR;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.distanceIR.DistanceIRDevice;

/**
 *
 * @author reto
 */
public class DistanceIRServiceContract extends DeviceServiceContract {

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

    public final String ILLUMINANCE;
    public final String STATUS_TOPIC_ILLUMINANCE;
    public final String STATUS_TOPIC_DISTANCE_THRESHOLD;
    public final String STATUS_TOPIC_ILLUMINANCE_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_DISTANCE;
    public final String EVENT_TOPIC_DISTANCE_REACHED;
    public final String INTENT_TOPIC_ILLUMINANCE;
    public final String INTENT_TOPIC_DISTANCE_THRESHOLD;
    public final String INTENT_TOPIC_DISTANCE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_TOPIC_DEBOUNCE;
    public final String EVENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE_PERIOD;
    public final String STATUS_TOPIC_DEBOUNCE_PERIOD;

    public DistanceIRServiceContract(DistanceIRDevice device) {
        super(device);

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

        ILLUMINANCE = "illuminance";
        STATUS_TOPIC_ILLUMINANCE = STATUS_TOPIC + "/" + ILLUMINANCE;
        STATUS_TOPIC_DISTANCE_THRESHOLD = STATUS_TOPIC_ILLUMINANCE + "/" + THRESHOLD;
        STATUS_TOPIC_ILLUMINANCE_CALLBACK_PERIOD = STATUS_TOPIC_ILLUMINANCE + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_DISTANCE = EVENT_TOPIC + "/" + ILLUMINANCE;
        EVENT_TOPIC_DISTANCE_REACHED = EVENT_TOPIC_DISTANCE + "/" + REACHED;
        INTENT_TOPIC_ILLUMINANCE = INTENT_TOPIC + "/" + ILLUMINANCE;
        INTENT_TOPIC_DISTANCE_THRESHOLD = INTENT_TOPIC_ILLUMINANCE + "/" + THRESHOLD;
        INTENT_TOPIC_DISTANCE_CALLBACK_PERIOD = INTENT_TOPIC_ILLUMINANCE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_TOPIC_DEBOUNCE = STATUS_TOPIC + "/" + DEBOUNCE;
        STATUS_TOPIC_DEBOUNCE_PERIOD = STATUS_TOPIC_DEBOUNCE + "/" + PERIOD;
        EVENT_TOPIC_DEBOUNCE = EVENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE = INTENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE_PERIOD = INTENT_TOPIC_DEBOUNCE + "/" + PERIOD;
    }
}
