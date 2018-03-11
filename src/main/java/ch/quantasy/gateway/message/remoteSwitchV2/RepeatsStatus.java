package ch.quantasy.gateway.message.remoteSwitchV2;

import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class RepeatsStatus extends AStatus {

    @Range(from = 0, to = Integer.MAX_VALUE)
    public int value;

    private RepeatsStatus() {
    }

    public RepeatsStatus(int value) {
        this.value = value;
    }
}
