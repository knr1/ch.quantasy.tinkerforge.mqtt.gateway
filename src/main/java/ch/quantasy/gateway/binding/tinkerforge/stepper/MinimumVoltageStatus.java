package ch.quantasy.gateway.binding.tinkerforge.stepper;

import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class MinimumVoltageStatus extends AStatus {

    @Range(from = 6000, to = Integer.MAX_VALUE)
    public Integer value;

    private MinimumVoltageStatus() {
    }

    public MinimumVoltageStatus(Integer value) {
        this.value = value;
    }
}
