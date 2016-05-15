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
    public final String STATUS_AIR_PRESSURE;
    public final String STATUS_AIR_PRESSURE_THRESHOLD;
    public final String STATUS_AIR_PRESSURE_CALLBACK_PERIOD;
    public final String EVENT_AIR_PRESSURE;
    public final String EVENT_AIR_PRESSURE_REACHED;
    public final String INTENT_AIR_PRESSURE;
    public final String INTENT_AIR_PRESSURE_THRESHOLD;
    public final String INTENT_AIR_PRESSURE_CALLBACK_PERIOD;

    public final String ALTITUDE;
    public final String STATUS_ALTITUDE;
    public final String STATUS_ALTITUDE_THRESHOLD;
    public final String STATUS_ALTITUDE_CALLBACK_PERIOD;
    public final String EVENT_ALTITUDE;
    public final String EVENT_ALTITUDE_REACHED;
    public final String INTENT_ALTITUDE;
    public final String INTENT_ALTITUDE_THRESHOLD;
    public final String INTENT_ALTITUDE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String AVERAGING;
    public final String STATUS_AVERAGING;
    public final String INTENT_AVERAGING;
    
    public final String REFERENCE_AIR_PRESSURE;
    public final String STATUS_REFERENCE_AIR_PRESSURE;
    public final String INTENT_REFERENCE_AIR_PRESSURE;

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
        STATUS_AIR_PRESSURE = STATUS + "/" + AIR_PRESSURE;
        STATUS_AIR_PRESSURE_THRESHOLD = STATUS_AIR_PRESSURE + "/" + THRESHOLD;
        STATUS_AIR_PRESSURE_CALLBACK_PERIOD = STATUS_AIR_PRESSURE + "/" + CALLBACK_PERIOD;
        EVENT_AIR_PRESSURE = EVENT + "/" + AIR_PRESSURE;
        EVENT_AIR_PRESSURE_REACHED = EVENT_AIR_PRESSURE + "/" + REACHED;
        INTENT_AIR_PRESSURE = INTENT + "/" + AIR_PRESSURE;
        INTENT_AIR_PRESSURE_THRESHOLD = INTENT_AIR_PRESSURE + "/" + THRESHOLD;
        INTENT_AIR_PRESSURE_CALLBACK_PERIOD = INTENT_AIR_PRESSURE + "/" + CALLBACK_PERIOD;

        ALTITUDE = "altitude";
        STATUS_ALTITUDE = STATUS + "/" + ALTITUDE;
        STATUS_ALTITUDE_THRESHOLD = STATUS_ALTITUDE + "/" + THRESHOLD;
        STATUS_ALTITUDE_CALLBACK_PERIOD = STATUS_ALTITUDE + "/" + CALLBACK_PERIOD;
        EVENT_ALTITUDE = EVENT + "/" + ALTITUDE;
        EVENT_ALTITUDE_REACHED = EVENT_ALTITUDE + "/" + REACHED;
        INTENT_ALTITUDE = INTENT + "/" + ALTITUDE;
        INTENT_ALTITUDE_THRESHOLD = INTENT_ALTITUDE + "/" + THRESHOLD;
        INTENT_ALTITUDE_CALLBACK_PERIOD = INTENT_ALTITUDE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        AVERAGING = "averaging";
        STATUS_AVERAGING = STATUS + "/" + AVERAGING;
        INTENT_AVERAGING = INTENT + "/" + AVERAGING;
        
        REFERENCE_AIR_PRESSURE="referenceAirPressure";
        INTENT_REFERENCE_AIR_PRESSURE=INTENT+"/"+REFERENCE_AIR_PRESSURE;
        STATUS_REFERENCE_AIR_PRESSURE=STATUS+"/"+REFERENCE_AIR_PRESSURE;
    }
}
