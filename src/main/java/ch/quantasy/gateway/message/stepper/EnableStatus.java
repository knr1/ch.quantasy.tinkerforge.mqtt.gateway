package ch.quantasy.gateway.message.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class EnableStatus extends AStatus {

    public Boolean value;

    private EnableStatus() {
    }

    public EnableStatus(Boolean value) {
        this.value = value;
    }
}
