/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.ambientLightV2;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.ambientLightV2.AmbientLightV2Device;
import ch.quantasy.tinkerforge.device.ambientLightV2.AmbientLightV2DeviceCallback;

/**
 *
 * @author reto
 */
public class AmbientLightV2ServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String ILLUMINANCE;
    public final String STATUS_ILLUMINANCE;
    public final String STATUS_ILLUMINANCE_THRESHOLD;
    public final String STATUS_ILLUMINANCE_CALLBACK_PERIOD;
    public final String EVENT_IllUMINANCE;
    public final String EVENT_ILLUMINANCE_REACHED;
    public final String INTENT_ILLUMINANCE;
    public final String INTENT_ILLUMINANCE_THRESHOLD;
    public final String INTENT_ILLUMINANCE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String STATUS_CONFIGURATION;
    public final String INTENT_CONFIGURATION;

    public AmbientLightV2ServiceContract(AmbientLightV2Device device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        ILLUMINANCE = "illuminance";
        STATUS_ILLUMINANCE = STATUS + "/" + ILLUMINANCE;
        STATUS_ILLUMINANCE_THRESHOLD = STATUS_ILLUMINANCE + "/" + THRESHOLD;
        STATUS_ILLUMINANCE_CALLBACK_PERIOD = STATUS_ILLUMINANCE + "/" + CALLBACK_PERIOD;
        EVENT_IllUMINANCE = EVENT + "/" + ILLUMINANCE;
        EVENT_ILLUMINANCE_REACHED = EVENT_IllUMINANCE + "/" + REACHED;
        INTENT_ILLUMINANCE = INTENT + "/" + ILLUMINANCE;
        INTENT_ILLUMINANCE_THRESHOLD = INTENT_ILLUMINANCE + "/" + THRESHOLD;
        INTENT_ILLUMINANCE_CALLBACK_PERIOD = INTENT_ILLUMINANCE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;
        INTENT_CONFIGURATION = INTENT + "/" + CONFIGURATION;

    }
}
