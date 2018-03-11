package ch.quantasy.gateway.message.gps;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class RestartStatus extends AStatus {

    public RestartType value;

    private RestartStatus() {
    }

    public RestartStatus(RestartType value) {
        this.value = value;
    }
}
