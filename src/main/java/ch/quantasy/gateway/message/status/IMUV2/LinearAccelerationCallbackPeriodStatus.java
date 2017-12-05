package ch.quantasy.gateway.message.status.IMUV2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class LinearAccelerationCallbackPeriodStatus  extends AStatus{

    @Period
    public Long value;

    private LinearAccelerationCallbackPeriodStatus() {
    }

    public LinearAccelerationCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
