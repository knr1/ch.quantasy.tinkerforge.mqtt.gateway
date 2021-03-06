package ch.quantasy.gateway.binding.tinkerforge.distanceUS;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class DistanceCallbackThresholdStatus extends AStatus {

    public DeviceDistanceCallbackThreshold value;

    private DistanceCallbackThresholdStatus() {
    }

    public DistanceCallbackThresholdStatus(DeviceDistanceCallbackThreshold value) {
        this.value = value;
    }
}
