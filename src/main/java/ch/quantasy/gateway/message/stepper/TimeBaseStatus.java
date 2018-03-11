package ch.quantasy.gateway.message.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;

public class TimeBaseStatus extends AStatus {

    @Range(from = 0, to = Long.MAX_VALUE)
    public Long value;

    private TimeBaseStatus() {
    }

    public TimeBaseStatus(Long value) {
        this.value = value;
    }
}
