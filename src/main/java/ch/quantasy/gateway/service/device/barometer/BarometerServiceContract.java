/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.barometer;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;

/**
 *
 * @author reto
 */
public class BarometerServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String AIR_PRESSURE;
    public final String STATUS_TOPIC_AIR_PRESSURE;
    public final String STATUS_TOPIC_AIR_PRESSURE_THRESHOLD;
    public final String STATUS_TOPIC_AIR_PRESSURE_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_AIR_PRESSURE;
    public final String EVENT_TOPIC_AIR_PRESSURE_REACHED;
    public final String INTENT_TOPIC_AIR_PRESSURE;
    public final String INTENT_TOPIC_AIR_PRESSURE_THRESHOLD;
    public final String INTENT_TOPIC_AIR_PRESSURE_CALLBACK_PERIOD;

    public final String ALTITUDE;
    public final String STATUS_TOPIC_ALTITUDE;
    public final String STATUS_TOPIC_ALTITUDE_THRESHOLD;
    public final String STATUS_TOPIC_ALTITUDE_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_ALTITUDE;
    public final String EVENT_TOPIC_ALTITUDE_REACHED;
    public final String INTENT_TOPIC_ALTITUDE;
    public final String INTENT_TOPIC_ALTITUDE_THRESHOLD;
    public final String INTENT_TOPIC_ALTITUDE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_TOPIC_DEBOUNCE;
    public final String EVENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE_PERIOD;
    public final String STATUS_TOPIC_DEBOUNCE_PERIOD;

    public final String AVERAGING;
    public final String STATUS_TOPIC_AVERAGING;
    public final String INTENT_TOPIC_AVERAGING;

    public BarometerServiceContract(BarometerDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public BarometerServiceContract(String id, String device) {
        super(id, device);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        AIR_PRESSURE = "airPressure";
        STATUS_TOPIC_AIR_PRESSURE = STATUS_TOPIC + "/" + AIR_PRESSURE;
        STATUS_TOPIC_AIR_PRESSURE_THRESHOLD = STATUS_TOPIC_AIR_PRESSURE + "/" + THRESHOLD;
        STATUS_TOPIC_AIR_PRESSURE_CALLBACK_PERIOD = STATUS_TOPIC_AIR_PRESSURE + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_AIR_PRESSURE = EVENT_TOPIC + "/" + AIR_PRESSURE;
        EVENT_TOPIC_AIR_PRESSURE_REACHED = EVENT_TOPIC_AIR_PRESSURE + "/" + REACHED;
        INTENT_TOPIC_AIR_PRESSURE = INTENT_TOPIC + "/" + AIR_PRESSURE;
        INTENT_TOPIC_AIR_PRESSURE_THRESHOLD = INTENT_TOPIC_AIR_PRESSURE + "/" + THRESHOLD;
        INTENT_TOPIC_AIR_PRESSURE_CALLBACK_PERIOD = INTENT_TOPIC_AIR_PRESSURE + "/" + CALLBACK_PERIOD;

        ALTITUDE = "altitude";
        STATUS_TOPIC_ALTITUDE = STATUS_TOPIC + "/" + ALTITUDE;
        STATUS_TOPIC_ALTITUDE_THRESHOLD = STATUS_TOPIC_ALTITUDE + "/" + THRESHOLD;
        STATUS_TOPIC_ALTITUDE_CALLBACK_PERIOD = STATUS_TOPIC_ALTITUDE + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_ALTITUDE = EVENT_TOPIC + "/" + ALTITUDE;
        EVENT_TOPIC_ALTITUDE_REACHED = EVENT_TOPIC_ALTITUDE + "/" + REACHED;
        INTENT_TOPIC_ALTITUDE = INTENT_TOPIC + "/" + ALTITUDE;
        INTENT_TOPIC_ALTITUDE_THRESHOLD = INTENT_TOPIC_ALTITUDE + "/" + THRESHOLD;
        INTENT_TOPIC_ALTITUDE_CALLBACK_PERIOD = INTENT_TOPIC_ALTITUDE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_TOPIC_DEBOUNCE = STATUS_TOPIC + "/" + DEBOUNCE;
        STATUS_TOPIC_DEBOUNCE_PERIOD = STATUS_TOPIC_DEBOUNCE + "/" + PERIOD;
        EVENT_TOPIC_DEBOUNCE = EVENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE = INTENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE_PERIOD = INTENT_TOPIC_DEBOUNCE + "/" + PERIOD;

        AVERAGING = "averaging";
        STATUS_TOPIC_AVERAGING = STATUS_TOPIC + "/" + AVERAGING;
        INTENT_TOPIC_AVERAGING = INTENT_TOPIC + "/" + AVERAGING;
    }
}
