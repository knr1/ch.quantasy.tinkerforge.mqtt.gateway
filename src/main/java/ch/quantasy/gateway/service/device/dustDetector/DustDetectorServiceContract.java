/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.dustDetector;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDevice;

/**
 *
 * @author reto
 */
public class DustDetectorServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String DUST_DENSITY;
    public final String STATUS_CO2_CONCENTRATION;
    public final String STATUS_DUST_DENSITY_THRESHOLD;
    public final String STATUS_DUST_DENSITY_CALLBACK_PERIOD;
    public final String EVENT_DUST_DENSITY;
    public final String EVENT_DUST_DENSITY_REACHED;
    public final String INTENT_CO2_CONCENTRATION;
    public final String INTENT_DUST_DENSITY_THRESHOLD;
    public final String INTENT_DUST_DENSITY_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;
    
    public final String MOVING_AVERAGE;
    public final String INTENT_MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;


    public DustDetectorServiceContract(DustDetectorDevice device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        DUST_DENSITY = "dustDensity";
        STATUS_CO2_CONCENTRATION = STATUS + "/" + DUST_DENSITY;
        STATUS_DUST_DENSITY_THRESHOLD = STATUS_CO2_CONCENTRATION + "/" + THRESHOLD;
        STATUS_DUST_DENSITY_CALLBACK_PERIOD = STATUS_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;
        EVENT_DUST_DENSITY = EVENT + "/" + DUST_DENSITY;
        EVENT_DUST_DENSITY_REACHED = EVENT_DUST_DENSITY + "/" + REACHED;
        INTENT_CO2_CONCENTRATION = INTENT + "/" + DUST_DENSITY;
        INTENT_DUST_DENSITY_THRESHOLD = INTENT_CO2_CONCENTRATION + "/" + THRESHOLD;
        INTENT_DUST_DENSITY_CALLBACK_PERIOD = INTENT_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;

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
