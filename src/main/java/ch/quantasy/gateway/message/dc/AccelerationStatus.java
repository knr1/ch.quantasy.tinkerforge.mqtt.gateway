package ch.quantasy.gateway.message.dc;

import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class AccelerationStatus extends AStatus {

    @Range(from = 0, to = 2147483647)
    public Integer value;

    private AccelerationStatus() {
    }

    public AccelerationStatus(Integer value) {
        this.value = value;
    }
}
