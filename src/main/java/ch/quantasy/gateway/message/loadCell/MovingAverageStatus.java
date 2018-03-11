package ch.quantasy.gateway.message.loadCell;

import java.lang.Short;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class MovingAverageStatus extends AStatus {

    @Range(from = 1, to = 40)
    public Short value;

    private MovingAverageStatus() {
    }

    public MovingAverageStatus(Short value) {
        this.value = value;
    }
}
