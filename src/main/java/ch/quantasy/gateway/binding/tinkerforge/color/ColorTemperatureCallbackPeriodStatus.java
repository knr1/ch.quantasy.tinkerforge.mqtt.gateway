package ch.quantasy.gateway.binding.tinkerforge.color;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ColorTemperatureCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private ColorTemperatureCallbackPeriodStatus() {
    }

    public ColorTemperatureCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
