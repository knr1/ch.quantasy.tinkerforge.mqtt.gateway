package ch.quantasy.gateway.binding.tinkerforge.loadCell;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class TareStatus extends AStatus {

    public Boolean value;

    private TareStatus() {
    }

    public TareStatus(Boolean value) {
        this.value = value;
    }
}
