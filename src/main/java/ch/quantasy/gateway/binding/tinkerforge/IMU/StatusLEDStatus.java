package ch.quantasy.gateway.binding.tinkerforge.IMU;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class StatusLEDStatus extends AStatus {

    public Boolean value;

    private StatusLEDStatus() {
    }

    public StatusLEDStatus(Boolean value) {
        this.value = value;
    }
}
