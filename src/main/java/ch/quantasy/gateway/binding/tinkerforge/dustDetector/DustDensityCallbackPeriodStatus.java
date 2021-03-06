package ch.quantasy.gateway.binding.tinkerforge.dustDetector;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class DustDensityCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private DustDensityCallbackPeriodStatus() {
    }

    public DustDensityCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
