package ch.quantasy.gateway.message.gpsv2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class StatusLEDConfigStatus extends AStatus {

    public StatusLEDConfig value;

    private StatusLEDConfigStatus() {
    }

    public StatusLEDConfigStatus(StatusLEDConfig value) {
        this.value = value;
    }
}
