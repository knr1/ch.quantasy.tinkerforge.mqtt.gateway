package ch.quantasy.gateway.binding.tinkerforge.line;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ReflectivityCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private ReflectivityCallbackPeriodStatus() {
    }

    public ReflectivityCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
