package ch.quantasy.gateway.binding.tinkerforge.LCD20x4;

import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ClearDisplayStatus extends AStatus {

    public boolean value;

    private ClearDisplayStatus() {
    }

    public ClearDisplayStatus(boolean value) {
        this.value = value;
    }
}
