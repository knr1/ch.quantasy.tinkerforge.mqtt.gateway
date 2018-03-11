package ch.quantasy.gateway.message.IMUV2;

import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class SensorFusionModeStatus extends AStatus {

    @Choice(values = {"0", "1", "2"})
    public short value;

    private SensorFusionModeStatus() {
    }

    public SensorFusionModeStatus(short value) {
        this.value = value;
    }
}
