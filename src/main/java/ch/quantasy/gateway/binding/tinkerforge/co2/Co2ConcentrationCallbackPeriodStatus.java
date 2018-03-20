package ch.quantasy.gateway.binding.tinkerforge.co2;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class Co2ConcentrationCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private Co2ConcentrationCallbackPeriodStatus() {
    }

    public Co2ConcentrationCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
