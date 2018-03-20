package ch.quantasy.gateway.binding.tinkerforge.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;

public class VelocityStatus extends AStatus {

    @Range(from = 0, to = Integer.MAX_VALUE)
    public Integer value;

    private VelocityStatus() {
    }

    public VelocityStatus(Integer value) {
        this.value = value;
    }
}
