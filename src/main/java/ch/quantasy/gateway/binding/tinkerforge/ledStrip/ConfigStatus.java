package ch.quantasy.gateway.binding.tinkerforge.ledStrip;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ConfigStatus extends AStatus {

    public LEDStripDeviceConfig value;

    private ConfigStatus() {
    }

    public ConfigStatus(LEDStripDeviceConfig value) {
        this.value = value;
    }
}
