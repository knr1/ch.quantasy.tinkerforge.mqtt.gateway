package ch.quantasy.gateway.binding.tinkerforge.accelerometer;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class AccelerationThresholdStatus extends AStatus {

    public DeviceAccelerationCallbackThreshold value;

    private AccelerationThresholdStatus() {
    }

    public AccelerationThresholdStatus(DeviceAccelerationCallbackThreshold value) {
        this.value = value;
    }
}
