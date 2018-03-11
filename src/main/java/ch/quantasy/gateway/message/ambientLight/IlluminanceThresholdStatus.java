package ch.quantasy.gateway.message.ambientLight;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class IlluminanceThresholdStatus extends AStatus {

    public DeviceIlluminanceCallbackThreshold value;

    private IlluminanceThresholdStatus() {
    }

    public IlluminanceThresholdStatus(DeviceIlluminanceCallbackThreshold value) {
        this.value = value;
    }
}
