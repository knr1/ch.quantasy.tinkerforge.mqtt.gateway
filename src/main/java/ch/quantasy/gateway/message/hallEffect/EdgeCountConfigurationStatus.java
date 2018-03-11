package ch.quantasy.gateway.message.hallEffect;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class EdgeCountConfigurationStatus extends AStatus {

    public DeviceConfiguration value;

    private EdgeCountConfigurationStatus() {
    }

    public EdgeCountConfigurationStatus(DeviceConfiguration value) {
        this.value = value;
    }
}
