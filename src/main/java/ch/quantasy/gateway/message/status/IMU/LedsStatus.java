package ch.quantasy.gateway.message.status.IMU;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import java.lang.Boolean;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class LedsStatus   extends AStatus{

    public Boolean value;

    private LedsStatus() {
    }

    public LedsStatus(Boolean value) {
        this.value = value;
    }
}
