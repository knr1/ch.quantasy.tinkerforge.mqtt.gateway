package ch.quantasy.gateway.message.motorizedLinearPoti;

import ch.quantasy.gateway.message.motorizedLinearPoti.DeviceMotorPosition;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class MotorPositionStatus extends AStatus {

    public DeviceMotorPosition value;

    private MotorPositionStatus() {
    }

    public MotorPositionStatus(DeviceMotorPosition value) {
        this.value = value;
    }
}
