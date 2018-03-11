package ch.quantasy.gateway.message.IMUV2;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class GravityVectorCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private GravityVectorCallbackPeriodStatus() {
    }

    public GravityVectorCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
