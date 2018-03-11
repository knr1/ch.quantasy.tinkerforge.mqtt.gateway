package ch.quantasy.gateway.message.ambientLight;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class AnalogValueThresholdStatus extends AStatus {

    public DeviceAnalogValueCallbackThreshold value;

    private AnalogValueThresholdStatus() {
    }

    public AnalogValueThresholdStatus(DeviceAnalogValueCallbackThreshold value) {
        this.value = value;
    }
}
