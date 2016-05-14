/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.co2;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.co2.CO2Device;

/**
 *
 * @author reto
 */
public class CO2ServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String CO2_CONCENTRATION;
    public final String STATUS_CO2_CONCENTRATION;
    public final String STATUS_CO2_CONCENTRATION_THRESHOLD;
    public final String STATUS_CO2_CONCENTRATION_CALLBACK_PERIOD;
    public final String EVENT_CO2_CONCENTRATION;
    public final String EVENT_CO2_CONCENTRATION_REACHED;
    public final String INTENT_CO2_CONCENTRATION;
    public final String INTENT_CO2_CONCENTRATION_THRESHOLD;
    public final String INTENT_CO2_CONCENTRATION_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;


    public CO2ServiceContract(CO2Device device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        CO2_CONCENTRATION = "CO2Concentration";
        STATUS_CO2_CONCENTRATION = STATUS + "/" + CO2_CONCENTRATION;
        STATUS_CO2_CONCENTRATION_THRESHOLD = STATUS_CO2_CONCENTRATION + "/" + THRESHOLD;
        STATUS_CO2_CONCENTRATION_CALLBACK_PERIOD = STATUS_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;
        EVENT_CO2_CONCENTRATION = EVENT + "/" + CO2_CONCENTRATION;
        EVENT_CO2_CONCENTRATION_REACHED = EVENT_CO2_CONCENTRATION + "/" + REACHED;
        INTENT_CO2_CONCENTRATION = INTENT + "/" + CO2_CONCENTRATION;
        INTENT_CO2_CONCENTRATION_THRESHOLD = INTENT_CO2_CONCENTRATION + "/" + THRESHOLD;
        INTENT_CO2_CONCENTRATION_CALLBACK_PERIOD = INTENT_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;
    }
}
