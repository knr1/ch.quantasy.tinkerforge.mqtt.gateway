package ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ConfigurationStatus extends AStatus {

    public DeviceConfiguration value;

    private ConfigurationStatus() {
    }

    public ConfigurationStatus(DeviceConfiguration value) {
        this.value = value;
    }
}
