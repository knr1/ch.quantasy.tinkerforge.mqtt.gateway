package ch.quantasy.gateway.message.laserRangeFinder;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class SensorHardwareStatus extends AStatus {

    public SensorHardware value;

    private SensorHardwareStatus() {
    }

    public SensorHardwareStatus(SensorHardware value) {
        this.value = value;
    }
}
