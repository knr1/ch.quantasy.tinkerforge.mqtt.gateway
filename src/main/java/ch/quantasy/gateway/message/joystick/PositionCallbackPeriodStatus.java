package ch.quantasy.gateway.message.joystick;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class PositionCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private PositionCallbackPeriodStatus() {
    }

    public PositionCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
