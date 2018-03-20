package ch.quantasy.gateway.binding.tinkerforge.motorizedLinearPoti;

import ch.quantasy.gateway.binding.tinkerforge.motorizedLinearPoti.DevicePositionCallbackConfiguration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class PositionCallbackConfigurationStatus extends AStatus {

    public DevicePositionCallbackConfiguration value;

    private PositionCallbackConfigurationStatus() {
    }

    public PositionCallbackConfigurationStatus(DevicePositionCallbackConfiguration value) {
        this.value = value;
    }
}
