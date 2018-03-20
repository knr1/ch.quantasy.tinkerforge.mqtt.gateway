package ch.quantasy.gateway.binding.tinkerforge.color;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ColorCallbackThresholdStatus extends AStatus {

    public DeviceColorCallbackThreshold value;

    private ColorCallbackThresholdStatus() {
    }

    public ColorCallbackThresholdStatus(DeviceColorCallbackThreshold value) {
        this.value = value;
    }
}
