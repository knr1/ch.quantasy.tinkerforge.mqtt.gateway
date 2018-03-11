package ch.quantasy.gateway.message.hallEffect;

import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class EdgeCountInterruptStatus extends AStatus {

    @Range(from = 0)
    public Long value;

    private EdgeCountInterruptStatus() {
    }

    public EdgeCountInterruptStatus(Long value) {
        this.value = value;
    }
}
