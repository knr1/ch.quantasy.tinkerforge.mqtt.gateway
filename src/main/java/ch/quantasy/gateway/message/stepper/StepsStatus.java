package ch.quantasy.gateway.message.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;

public class StepsStatus extends AStatus {

    @Range(from = 0, to = Integer.MAX_VALUE)
    public Integer value;

    private StepsStatus() {
    }

    public StepsStatus(Integer value) {
        this.value = value;
    }
}
