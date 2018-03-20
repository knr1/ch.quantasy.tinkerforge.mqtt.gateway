package ch.quantasy.gateway.binding.tinkerforge.IMUV2;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class LinearAccelerationCallbackPeriodStatus extends AStatus {

    @Period
    public Long value;

    private LinearAccelerationCallbackPeriodStatus() {
    }

    public LinearAccelerationCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
