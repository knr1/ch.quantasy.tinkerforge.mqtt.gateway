package ch.quantasy.gateway.message.status.IMU;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AllDataCallbackPeriodStatus extends AStatus{

    @Period
    public Long value;

    private AllDataCallbackPeriodStatus() {
    }

    public AllDataCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
