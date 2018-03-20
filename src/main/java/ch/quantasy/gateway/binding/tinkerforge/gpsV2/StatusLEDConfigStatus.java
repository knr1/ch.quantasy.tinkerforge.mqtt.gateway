package ch.quantasy.gateway.binding.tinkerforge.gpsV2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class StatusLEDConfigStatus extends AStatus {

    public StatusLEDConfig value;

    private StatusLEDConfigStatus() {
    }

    public StatusLEDConfigStatus(StatusLEDConfig value) {
        this.value = value;
    }
}
