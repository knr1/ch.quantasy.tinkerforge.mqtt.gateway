package ch.quantasy.gateway.binding.tinkerforge.analogInV2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class VoltageCallbackThresholdStatus extends AStatus {

    public DeviceVoltageCallbackThreshold value;

    private VoltageCallbackThresholdStatus() {
    }

    public VoltageCallbackThresholdStatus(DeviceVoltageCallbackThreshold value) {
        this.value = value;
    }
}
