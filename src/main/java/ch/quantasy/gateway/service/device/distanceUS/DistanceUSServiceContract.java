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
    public final String STATUS_DISTANCE;
    public final String STATUS_DISTANCE_THRESHOLD;
    public final String STATUS_DISTANCE_CALLBACK_PERIOD;
    public final String EVENT_DISTANCE;
    public final String EVENT_DISTANCE_REACHED;
    public final String INTENT_DISTANCE;
    public final String INTENT_DISTANCE_THRESHOLD;
    public final String INTENT_DISTANCE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;
    public final String INTENT_MOVING_AVERAGE;

    public DistanceUSServiceContract(DistanceUSDevice device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        DISTANCE = "distance";
        STATUS_DISTANCE = STATUS + "/" + DISTANCE;
        STATUS_DISTANCE_THRESHOLD = STATUS_DISTANCE + "/" + THRESHOLD;
        STATUS_DISTANCE_CALLBACK_PERIOD = STATUS_DISTANCE + "/" + CALLBACK_PERIOD;
        EVENT_DISTANCE = EVENT + "/" + DISTANCE;
        EVENT_DISTANCE_REACHED = EVENT_DISTANCE + "/" + REACHED;
        INTENT_DISTANCE = INTENT + "/" + DISTANCE;
        INTENT_DISTANCE_THRESHOLD = INTENT_DISTANCE + "/" + THRESHOLD;
        INTENT_DISTANCE_CALLBACK_PERIOD = INTENT_DISTANCE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        MOVING_AVERAGE = "movingAverage";
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;
        INTENT_MOVING_AVERAGE = INTENT + "/" + MOVING_AVERAGE;

    }
}
