package ch.quantasy.gateway.message.motorizedLinearPoti;

import ch.quantasy.gateway.message.motorizedLinearPoti.DevicePositionCallbackConfiguration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class PositionCallbackConfigurationStatus extends AStatus {

    public DevicePositionCallbackConfiguration value;

    private PositionCallbackConfigurationStatus() {
    }

    public PositionCallbackConfigurationStatus(DevicePositionCallbackConfiguration value) {
        this.value = value;
    }
}
