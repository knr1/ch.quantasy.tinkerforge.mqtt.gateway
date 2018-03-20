package ch.quantasy.gateway.binding.tinkerforge.ledStrip;

import ch.quantasy.mqtt.gateway.client.message.annotations.ArraySize;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class LEDFramesStatus extends AStatus {

    @ArraySize(max = 2147483647, min = 1)
    public LEDFrame[] value;

    private LEDFramesStatus() {
    }

    public LEDFramesStatus(LEDFrame[] value) {
        this.value = value;
    }
}
