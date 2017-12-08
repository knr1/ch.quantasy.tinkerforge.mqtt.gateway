package ch.quantasy.gateway.message.ptc;

import java.lang.Short;
import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class WireModeStatus extends AStatus{

    @Choice(values = {"2", "3", "4"})
    public Short value;

    private WireModeStatus() {
    }

    public WireModeStatus(Short value) {
        this.value = value;
    }
}
