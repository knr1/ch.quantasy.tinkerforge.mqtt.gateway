package ch.quantasy.gateway.binding.tinkerforge.distanceUS;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class DistanceCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private DistanceCallbackPeriodStatus() {
    }

    public DistanceCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
