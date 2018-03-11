package ch.quantasy.gateway.message.line;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ReflectivityCallbackThresholdStatus extends AStatus {

    public DeviceReflectivityCallbackThreshold value;

    private ReflectivityCallbackThresholdStatus() {
    }

    public ReflectivityCallbackThresholdStatus(DeviceReflectivityCallbackThreshold value) {
        this.value = value;
    }
}
