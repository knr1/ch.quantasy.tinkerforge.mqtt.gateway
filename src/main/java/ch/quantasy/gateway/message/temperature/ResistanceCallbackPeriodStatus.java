package ch.quantasy.gateway.message.temperature;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ResistanceCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private ResistanceCallbackPeriodStatus() {
    }

    public ResistanceCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
