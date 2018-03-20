package ch.quantasy.gateway.binding.tinkerforge.barometer;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class AveragingStatus extends AStatus {

    public DeviceAveraging value;

    private AveragingStatus() {
    }

    public AveragingStatus(DeviceAveraging value) {
        this.value = value;
    }
}
