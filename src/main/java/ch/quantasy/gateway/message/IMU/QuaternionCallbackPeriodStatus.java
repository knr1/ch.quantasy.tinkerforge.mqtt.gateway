package ch.quantasy.gateway.message.IMU;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class QuaternionCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private QuaternionCallbackPeriodStatus() {
    }

    public QuaternionCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
