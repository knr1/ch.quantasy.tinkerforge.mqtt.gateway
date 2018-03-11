package ch.quantasy.gateway.message.barometer;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class AirPressureCallbackThresholdStatus extends AStatus {

    public DeviceAirPressureCallbackThreshold value;

    private AirPressureCallbackThresholdStatus() {
    }

    public AirPressureCallbackThresholdStatus(DeviceAirPressureCallbackThreshold value) {
        this.value = value;
    }
}
