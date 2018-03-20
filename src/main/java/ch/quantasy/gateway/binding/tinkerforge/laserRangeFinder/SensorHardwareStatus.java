package ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class SensorHardwareStatus extends AStatus {

    public SensorHardware value;

    private SensorHardwareStatus() {
    }

    public SensorHardwareStatus(SensorHardware value) {
        this.value = value;
    }
}
