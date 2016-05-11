/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.co2;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.co2Device.CO2Device;

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
    public final String STATUS_TOPIC_CO2_CONCENTRATION;
    public final String STATUS_TOPIC_CO2_CONCENTRATION_THRESHOLD;
    public final String STATUS_TOPIC_CO2_CONCENTRATION_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_CO2_CONCENTRATION;
    public final String EVENT_TOPIC_CO2_CONCENTRATION_REACHED;
    public final String INTENT_TOPIC_CO2_CONCENTRATION;
    public final String INTENT_TOPIC_CO2_CONCENTRATION_THRESHOLD;
    public final String INTENT_TOPIC_CO2_CONCENTRATION_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_TOPIC_DEBOUNCE;
    public final String EVENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE_PERIOD;
    public final String STATUS_TOPIC_DEBOUNCE_PERIOD;


    public CO2ServiceContract(CO2Device device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        CO2_CONCENTRATION = "CO2Concentration";
        STATUS_TOPIC_CO2_CONCENTRATION = STATUS_TOPIC + "/" + CO2_CONCENTRATION;
        STATUS_TOPIC_CO2_CONCENTRATION_THRESHOLD = STATUS_TOPIC_CO2_CONCENTRATION + "/" + THRESHOLD;
        STATUS_TOPIC_CO2_CONCENTRATION_CALLBACK_PERIOD = STATUS_TOPIC_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_CO2_CONCENTRATION = EVENT_TOPIC + "/" + CO2_CONCENTRATION;
        EVENT_TOPIC_CO2_CONCENTRATION_REACHED = EVENT_TOPIC_CO2_CONCENTRATION + "/" + REACHED;
        INTENT_TOPIC_CO2_CONCENTRATION = INTENT_TOPIC + "/" + CO2_CONCENTRATION;
        INTENT_TOPIC_CO2_CONCENTRATION_THRESHOLD = INTENT_TOPIC_CO2_CONCENTRATION + "/" + THRESHOLD;
        INTENT_TOPIC_CO2_CONCENTRATION_CALLBACK_PERIOD = INTENT_TOPIC_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_TOPIC_DEBOUNCE = STATUS_TOPIC + "/" + DEBOUNCE;
        STATUS_TOPIC_DEBOUNCE_PERIOD = STATUS_TOPIC_DEBOUNCE + "/" + PERIOD;
        EVENT_TOPIC_DEBOUNCE = EVENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE = INTENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE_PERIOD = INTENT_TOPIC_DEBOUNCE + "/" + PERIOD;
    }
}
