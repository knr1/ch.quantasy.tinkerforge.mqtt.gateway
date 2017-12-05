package ch.quantasy.gateway.message.status.IMUV2;

import ch.quantasy.gateway.message.status.IMU.*;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;
import java.lang.Short;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SensorFusionModeStatus extends AStatus{

    @Choice(values = {"0", "1", "2"})
    public Short value;

    private SensorFusionModeStatus() {
    }

    public SensorFusionModeStatus(Short value) {
        this.value = value;
    }
}
