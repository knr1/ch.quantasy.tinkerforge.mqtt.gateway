package ch.quantasy.gateway.binding.tinkerforge.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;

public class DecayStatus extends AStatus {

    @Range(from = 0, to = 65535)
    public Integer value;

    private DecayStatus() {
    }

    public DecayStatus(Integer value) {
        this.value = value;
    }
}
