package ch.quantasy.gateway.binding.tinkerforge.humidity;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class HumidityCallbackThresholdStatus extends AStatus {

    public DevicePositionCallbackThreshold value;

    private HumidityCallbackThresholdStatus() {
    }

    public HumidityCallbackThresholdStatus(DevicePositionCallbackThreshold value) {
        this.value = value;
    }
}
