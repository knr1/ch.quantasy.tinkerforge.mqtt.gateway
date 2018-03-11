package ch.quantasy.gateway.message.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class SpeedRampStatus extends AStatus {

    public DeviceSpeedRamp value;

    private SpeedRampStatus() {
    }

    public SpeedRampStatus(DeviceSpeedRamp value) {
        this.value = value;
    }
}
