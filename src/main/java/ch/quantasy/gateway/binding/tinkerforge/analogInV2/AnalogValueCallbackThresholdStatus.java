package ch.quantasy.gateway.binding.tinkerforge.analogInV2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class AnalogValueCallbackThresholdStatus extends AStatus {

    public DeviceAnalogValueCallbackThreshold value;

    private AnalogValueCallbackThresholdStatus() {
    }

    public AnalogValueCallbackThresholdStatus(DeviceAnalogValueCallbackThreshold value) {
        this.value = value;
    }
}
