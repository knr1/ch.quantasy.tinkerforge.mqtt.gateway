package ch.quantasy.gateway.binding.tinkerforge.loadCell;

import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class StatusLEDStatus extends AStatus {

    public Boolean value;

    private StatusLEDStatus() {
    }

    public StatusLEDStatus(Boolean value) {
        this.value = value;
    }
}
