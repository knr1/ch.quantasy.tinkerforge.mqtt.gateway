package ch.quantasy.gateway.binding.tinkerforge.loadCell;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class WeightCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private WeightCallbackPeriodStatus() {
    }

    public WeightCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
