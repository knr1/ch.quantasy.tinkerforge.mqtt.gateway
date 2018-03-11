package ch.quantasy.gateway.message.LCD16x2;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ClearDisplayStatus extends AStatus {

    public boolean value;

    private ClearDisplayStatus() {
    }

    public ClearDisplayStatus(boolean value) {
        this.value = value;
    }
}
