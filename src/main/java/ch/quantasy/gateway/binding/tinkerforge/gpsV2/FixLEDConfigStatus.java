package ch.quantasy.gateway.binding.tinkerforge.gpsV2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class FixLEDConfigStatus extends AStatus {

    public FixLEDConfig value;

    private FixLEDConfigStatus() {
    }

    public FixLEDConfigStatus(FixLEDConfig value) {
        this.value = value;
    }
}
