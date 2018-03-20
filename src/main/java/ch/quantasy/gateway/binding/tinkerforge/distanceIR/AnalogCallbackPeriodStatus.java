package ch.quantasy.gateway.binding.tinkerforge.distanceIR;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class AnalogCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private AnalogCallbackPeriodStatus() {
    }

    public AnalogCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
