package ch.quantasy.gateway.binding.tinkerforge.dc;

import java.lang.Short;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class VelocityStatus extends AStatus {

    @Range(from = -32767, to = 32767)
    public Short value;

    private VelocityStatus() {
    }

    public VelocityStatus(Short value) {
        this.value = value;
    }
}
