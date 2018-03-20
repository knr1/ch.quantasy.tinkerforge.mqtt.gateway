package ch.quantasy.gateway.binding.tinkerforge.ambientLightV2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class IlluminanceCallbackThresholdStatus extends AStatus {

    public DeviceIlluminanceCallbackThreshold value;

    private IlluminanceCallbackThresholdStatus() {
    }

    public IlluminanceCallbackThresholdStatus(DeviceIlluminanceCallbackThreshold value) {
        this.value = value;
    }
}
