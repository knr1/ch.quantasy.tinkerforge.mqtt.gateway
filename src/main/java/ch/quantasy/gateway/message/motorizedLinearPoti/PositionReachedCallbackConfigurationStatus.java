package ch.quantasy.gateway.message.motorizedLinearPoti;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class PositionReachedCallbackConfigurationStatus extends AStatus {

    public boolean value;

    private PositionReachedCallbackConfigurationStatus() {
    }

    public PositionReachedCallbackConfigurationStatus(boolean value) {
        this.value = value;
    }
}
