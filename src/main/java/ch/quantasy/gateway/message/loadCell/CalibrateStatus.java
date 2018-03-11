package ch.quantasy.gateway.message.loadCell;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class CalibrateStatus extends AStatus {

    public Long value;

    private CalibrateStatus() {
    }

    public CalibrateStatus(Long value) {
        this.value = value;
    }
}
