package ch.quantasy.gateway.binding.tinkerforge.dc;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class FullBrakeStatus extends AStatus {

    public Boolean value;

    private FullBrakeStatus() {
    }

    public FullBrakeStatus(Boolean value) {
        this.value = value;
    }
}
