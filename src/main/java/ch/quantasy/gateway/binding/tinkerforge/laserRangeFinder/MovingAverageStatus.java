package ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class MovingAverageStatus extends AStatus {

    public DeviceAveraging value;

    private MovingAverageStatus() {
    }

    public MovingAverageStatus(DeviceAveraging value) {
        this.value = value;
    }
}
