package ch.quantasy.gateway.binding.tinkerforge.barometer;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class AltitudeCallbackThresholdStatus extends AStatus {

    public DeviceAltitudeCallbackThreshold value;

    private AltitudeCallbackThresholdStatus() {
    }

    public AltitudeCallbackThresholdStatus(DeviceAltitudeCallbackThreshold value) {
        this.value = value;
    }
}
