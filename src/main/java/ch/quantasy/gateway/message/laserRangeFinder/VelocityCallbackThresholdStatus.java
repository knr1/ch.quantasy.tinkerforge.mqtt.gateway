package ch.quantasy.gateway.message.laserRangeFinder;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class VelocityCallbackThresholdStatus extends AStatus {

    public DeviceVelocityCallbackThreshold value;

    private VelocityCallbackThresholdStatus() {
    }

    public VelocityCallbackThresholdStatus(DeviceVelocityCallbackThreshold value) {
        this.value = value;
    }
}
