package ch.quantasy.gateway.binding.tinkerforge.dustDetector;

import java.lang.Short;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class MovingAverageStatus extends AStatus {

    @Range(from = 0, to = 100)
    public Short value;

    private MovingAverageStatus() {
    }

    public MovingAverageStatus(Short value) {
        this.value = value;
    }
}
