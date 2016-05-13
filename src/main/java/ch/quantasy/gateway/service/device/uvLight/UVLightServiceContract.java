/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.uvLight;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDevice;

/**
 *
 * @author reto
 */
public class UVLightServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String UV_LIGHT;
    public final String STATUS_TOPIC_UV_LIGHT;
    public final String STATUS_TOPIC_UV_LIGHT_THRESHOLD;
    public final String STATUS_TOPIC_UV_LIGHT_CALLBACK_PERIOD;
    public final String EVENT_TOPIC_UV_LIGHT;
    public final String EVENT_TOPIC_UV_LIGHT_REACHED;
    public final String INTENT_TOPIC_UV_LIGHT;
    public final String INTENT_TOPIC_UV_LIGHT_THRESHOLD;
    public final String INTENT_TOPIC_UV_LIGHT_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_TOPIC_DEBOUNCE;
    public final String EVENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE;
    public final String INTENT_TOPIC_DEBOUNCE_PERIOD;
    public final String STATUS_TOPIC_DEBOUNCE_PERIOD;


    public UVLightServiceContract(UVLightDevice device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        UV_LIGHT = "uvLight";
        STATUS_TOPIC_UV_LIGHT = STATUS_TOPIC + "/" + UV_LIGHT;
        STATUS_TOPIC_UV_LIGHT_THRESHOLD = STATUS_TOPIC_UV_LIGHT + "/" + THRESHOLD;
        STATUS_TOPIC_UV_LIGHT_CALLBACK_PERIOD = STATUS_TOPIC_UV_LIGHT + "/" + CALLBACK_PERIOD;
        EVENT_TOPIC_UV_LIGHT = EVENT_TOPIC + "/" + UV_LIGHT;
        EVENT_TOPIC_UV_LIGHT_REACHED = EVENT_TOPIC_UV_LIGHT + "/" + REACHED;
        INTENT_TOPIC_UV_LIGHT = INTENT_TOPIC + "/" + UV_LIGHT;
        INTENT_TOPIC_UV_LIGHT_THRESHOLD = INTENT_TOPIC_UV_LIGHT + "/" + THRESHOLD;
        INTENT_TOPIC_UV_LIGHT_CALLBACK_PERIOD = INTENT_TOPIC_UV_LIGHT + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_TOPIC_DEBOUNCE = STATUS_TOPIC + "/" + DEBOUNCE;
        STATUS_TOPIC_DEBOUNCE_PERIOD = STATUS_TOPIC_DEBOUNCE + "/" + PERIOD;
        EVENT_TOPIC_DEBOUNCE = EVENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE = INTENT_TOPIC + "/" + DEBOUNCE;
        INTENT_TOPIC_DEBOUNCE_PERIOD = INTENT_TOPIC_DEBOUNCE + "/" + PERIOD;

    }
}
