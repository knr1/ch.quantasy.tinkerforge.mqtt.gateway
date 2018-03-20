package ch.quantasy.gateway.binding.tinkerforge.loadCell;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class WeightCallbackThresholdStatus extends AStatus {

    public DeviceWeightCallbackThreshold value;

    private WeightCallbackThresholdStatus() {
    }

    public WeightCallbackThresholdStatus(DeviceWeightCallbackThreshold value) {
        this.value = value;
    }
}
