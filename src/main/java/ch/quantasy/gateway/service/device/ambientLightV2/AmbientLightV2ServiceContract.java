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
    public final String STATUS_TOPIC_ILLUMINANCE;
    public final String STATUS_TOPIC_ILLUMINANCE_THRESHOLD;
    public final String STATUS_TOPIC_ILLUMINANCE_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_IllUMINANCE;
    public final String EVENT_TOPIC_ILLUMINANCE_REACHED;
    public final String INTENT_TOPIC_ILLUMINANCE;
    public final String INTENT_TOPIC_ILLUMINANCE_THRESHOLD;
    public final String INTENT_TOPIC_ILLUMINANCE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_TOPIC_DEBOUNCE;
    public final String EVENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE_PERIOD;
    public final String STATUS_TOPIC_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String STATUS_TOPIC_CONFIGURATION;
    public final String INTENT_TOPIC_CONFIGURATION;

    public AmbientLightV2ServiceContract(AmbientLightV2Device device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        ILLUMINANCE = "illuminance";
        STATUS_TOPIC_ILLUMINANCE = STATUS_TOPIC + "/" + ILLUMINANCE;
        STATUS_TOPIC_ILLUMINANCE_THRESHOLD = STATUS_TOPIC_ILLUMINANCE + "/" + THRESHOLD;
        STATUS_TOPIC_ILLUMINANCE_CALLBACK_PERIOD = STATUS_TOPIC_ILLUMINANCE + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_IllUMINANCE = EVENT_TOPIC + "/" + ILLUMINANCE;
        EVENT_TOPIC_ILLUMINANCE_REACHED = EVENT_TOPIC_IllUMINANCE + "/" + REACHED;
        INTENT_TOPIC_ILLUMINANCE = INTENT_TOPIC + "/" + ILLUMINANCE;
        INTENT_TOPIC_ILLUMINANCE_THRESHOLD = INTENT_TOPIC_ILLUMINANCE + "/" + THRESHOLD;
        INTENT_TOPIC_ILLUMINANCE_CALLBACK_PERIOD = INTENT_TOPIC_ILLUMINANCE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_TOPIC_DEBOUNCE = STATUS_TOPIC + "/" + DEBOUNCE;
        STATUS_TOPIC_DEBOUNCE_PERIOD = STATUS_TOPIC_DEBOUNCE + "/" + PERIOD;
        EVENT_TOPIC_DEBOUNCE = EVENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE = INTENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE_PERIOD = INTENT_TOPIC_DEBOUNCE + "/" + PERIOD;

        CONFIGURATION = "configuration";
        STATUS_TOPIC_CONFIGURATION = STATUS_TOPIC + "/" + CONFIGURATION;
        INTENT_TOPIC_CONFIGURATION = INTENT_TOPIC + "/" + CONFIGURATION;

    }
}
