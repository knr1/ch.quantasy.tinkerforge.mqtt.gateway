package ch.quantasy.gateway.message.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class RectificationStatus extends AStatus {

    public Boolean value;

    private RectificationStatus() {
    }

    public RectificationStatus(Boolean value) {
        this.value = value;
    }
}
