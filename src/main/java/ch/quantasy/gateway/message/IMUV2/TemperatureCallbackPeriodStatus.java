package ch.quantasy.gateway.message.IMUV2;

import ch.quantasy.mqtt.gateway.client.message.annotations.Period;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class TemperatureCallbackPeriodStatus extends AStatus {

    @Period
    public long value;

    private TemperatureCallbackPeriodStatus() {
    }

    public TemperatureCallbackPeriodStatus(long value) {
        this.value = value;
    }
}
