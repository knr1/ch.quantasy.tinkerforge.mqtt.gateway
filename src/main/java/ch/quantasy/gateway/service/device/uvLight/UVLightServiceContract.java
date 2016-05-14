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
    public final String STATUS_UV_LIGHT;
    public final String STATUS_UV_LIGHT_THRESHOLD;
    public final String STATUS_UV_LIGHT_CALLBACK_PERIOD;
    public final String EVENT_UV_LIGHT;
    public final String EVENT_UV_LIGHT_REACHED;
    public final String INTENT_UV_LIGHT;
    public final String INTENT_UV_LIGHT_THRESHOLD;
    public final String INTENT_UV_LIGHT_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;


    public UVLightServiceContract(UVLightDevice device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        UV_LIGHT = "uvLight";
        STATUS_UV_LIGHT = STATUS + "/" + UV_LIGHT;
        STATUS_UV_LIGHT_THRESHOLD = STATUS_UV_LIGHT + "/" + THRESHOLD;
        STATUS_UV_LIGHT_CALLBACK_PERIOD = STATUS_UV_LIGHT + "/" + CALLBACK_PERIOD;
        EVENT_UV_LIGHT = EVENT + "/" + UV_LIGHT;
        EVENT_UV_LIGHT_REACHED = EVENT_UV_LIGHT + "/" + REACHED;
        INTENT_UV_LIGHT = INTENT + "/" + UV_LIGHT;
        INTENT_UV_LIGHT_THRESHOLD = INTENT_UV_LIGHT + "/" + THRESHOLD;
        INTENT_UV_LIGHT_CALLBACK_PERIOD = INTENT_UV_LIGHT + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

    }
}
