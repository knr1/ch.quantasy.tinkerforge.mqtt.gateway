package ch.quantasy.gateway.binding.tinkerforge.dustDetector;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class DustDensityCallbackThresholdStatus extends AStatus {

    public DeviceDustDensityCallbackThreshold value;

    private DustDensityCallbackThresholdStatus() {
    }

    public DustDensityCallbackThresholdStatus(DeviceDustDensityCallbackThreshold value) {
        this.value = value;
    }
}
