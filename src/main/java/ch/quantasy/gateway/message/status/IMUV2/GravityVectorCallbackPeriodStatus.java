package ch.quantasy.gateway.message.status.IMUV2;

import ch.quantasy.gateway.message.status.IMU.*;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class GravityVectorCallbackPeriodStatus  extends AStatus{

    @Period
    public Long value;

    private GravityVectorCallbackPeriodStatus() {
    }

    public GravityVectorCallbackPeriodStatus(Long value) {
        this.value = value;
    }
}
