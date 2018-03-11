package ch.quantasy.gateway.message.color;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class LightStatus extends AStatus {

    public Boolean value;

    private LightStatus() {
    }

    public LightStatus(Boolean value) {
        this.value = value;
    }
}
