package ch.quantasy.gateway.message.dc;

import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class DriveModeStatus extends AStatus {

    @Choice(values = {"1", "2"})
    public Short value;

    private DriveModeStatus() {
    }

    public DriveModeStatus(Short value) {
        this.value = value;
    }
}
