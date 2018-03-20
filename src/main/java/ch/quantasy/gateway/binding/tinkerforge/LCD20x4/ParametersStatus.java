package ch.quantasy.gateway.binding.tinkerforge.LCD20x4;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ParametersStatus extends AStatus {

    public DeviceConfigParameters value;

    private ParametersStatus() {
    }

    public ParametersStatus(DeviceConfigParameters value) {
        this.value = value;
    }
}
