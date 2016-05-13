/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.distanceUS;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.distanceUS.DistanceUSDevice;

/**
 *
 * @author reto
 */
public class DistanceUSServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String DISTANCE;
    public final String STATUS_TOPIC_DISTANCE;
    public final String STATUS_TOPIC_DISTANCE_THRESHOLD;
    public final String STATUS_TOPIC_DISTANCE_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_DISTANCE;
    public final String EVENT_TOPIC_DISTANCE_REACHED;
    public final String INTENT_TOPIC_DISTANCE;
    public final String INTENT_TOPIC_DISTANCE_THRESHOLD;
    public final String INTENT_TOPIC_DISTANCE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_TOPIC_DEBOUNCE;
    public final String EVENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE_PERIOD;
    public final String STATUS_TOPIC_DEBOUNCE_PERIOD;

    public final String MOVING_AVERAGE;
    public final String STATUS_TOPIC_MOVING_AVERAGE;
    public final String INTENT_TOPIC_MOVING_AVERAGE;

    public DistanceUSServiceContract(DistanceUSDevice device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        DISTANCE = "distance";
        STATUS_TOPIC_DISTANCE = STATUS_TOPIC + "/" + DISTANCE;
        STATUS_TOPIC_DISTANCE_THRESHOLD = STATUS_TOPIC_DISTANCE + "/" + THRESHOLD;
        STATUS_TOPIC_DISTANCE_CALLBACK_PERIOD = STATUS_TOPIC_DISTANCE + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_DISTANCE = EVENT_TOPIC + "/" + DISTANCE;
        EVENT_TOPIC_DISTANCE_REACHED = EVENT_TOPIC_DISTANCE + "/" + REACHED;
        INTENT_TOPIC_DISTANCE = INTENT_TOPIC + "/" + DISTANCE;
        INTENT_TOPIC_DISTANCE_THRESHOLD = INTENT_TOPIC_DISTANCE + "/" + THRESHOLD;
        INTENT_TOPIC_DISTANCE_CALLBACK_PERIOD = INTENT_TOPIC_DISTANCE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_TOPIC_DEBOUNCE = STATUS_TOPIC + "/" + DEBOUNCE;
        STATUS_TOPIC_DEBOUNCE_PERIOD = STATUS_TOPIC_DEBOUNCE + "/" + PERIOD;
        EVENT_TOPIC_DEBOUNCE = EVENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE = INTENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE_PERIOD = INTENT_TOPIC_DEBOUNCE + "/" + PERIOD;

        MOVING_AVERAGE = "movingAverage";
        STATUS_TOPIC_MOVING_AVERAGE = STATUS_TOPIC + "/" + MOVING_AVERAGE;
        INTENT_TOPIC_MOVING_AVERAGE = INTENT_TOPIC + "/" + MOVING_AVERAGE;

    }
}
