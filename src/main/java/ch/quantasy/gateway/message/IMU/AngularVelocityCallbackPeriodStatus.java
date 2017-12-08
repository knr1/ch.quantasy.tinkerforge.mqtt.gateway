package ch.quantasy.gateway.message.IMU;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AngularVelocityCallbackPeriodStatus extends AStatus{

    @Period
    public Long value;

    private AngularVelocityCallbackPeriodStatus() {
    }

    public AngularVelocityCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
