package ch.quantasy.gateway.binding.tinkerforge.hallEffect;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class EdgeCountResetStatus extends AStatus {

    public Boolean value;

    private EdgeCountResetStatus() {
    }

    public EdgeCountResetStatus(Boolean value) {
        this.value = value;
    }
}
