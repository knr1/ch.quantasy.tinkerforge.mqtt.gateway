package ch.quantasy.gateway.binding.tinkerforge.device;

import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.ArraySize;

public class Hardware extends AStatus {

    @ArraySize()
    @Range(from = Short.MIN_VALUE, to = Short.MAX_VALUE)
    public short[] value;

    private Hardware() {
    }

    public Hardware(short[] value) {
        this.value = value;
    }
}
