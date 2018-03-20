package ch.quantasy.gateway.binding.tinkerforge.IMU;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class LedsStatus extends AStatus {

    public Boolean value;

    private LedsStatus() {
    }

    public LedsStatus(Boolean value) {
        this.value = value;
    }
}
