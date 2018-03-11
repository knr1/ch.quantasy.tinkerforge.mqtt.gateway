package ch.quantasy.gateway.message.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;

public class MotorCurrentStatus extends AStatus {

    @Range(from = 100, to = 2291)
    public Integer value;

    private MotorCurrentStatus() {
    }

    public MotorCurrentStatus(Integer value) {
        this.value = value;
    }
}
