package ch.quantasy.gateway.binding.tinkerforge.LCD16x2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class BacklightStatus extends AStatus {

    public boolean value;

    private BacklightStatus() {
    }

    public BacklightStatus(boolean value) {
        this.value = value;
    }
}
