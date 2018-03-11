package ch.quantasy.gateway.message.remoteSwitch;

import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class RepeatsStatus extends AStatus {

    @Range(from = 0, to = 32767)
    public short value;

    private RepeatsStatus() {
    }

    public RepeatsStatus(short value) {
        this.value = value;
    }
}
