package ch.quantasy.gateway.message.laserRangeFinder;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ModeStatus extends AStatus {

    public DeviceMode value;

    private ModeStatus() {
    }

    public ModeStatus(DeviceMode value) {
        this.value = value;
    }
}
