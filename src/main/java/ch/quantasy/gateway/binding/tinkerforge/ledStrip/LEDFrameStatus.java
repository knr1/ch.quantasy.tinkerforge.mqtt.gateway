package ch.quantasy.gateway.binding.tinkerforge.ledStrip;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class LEDFrameStatus extends AStatus {

    public LEDFrame value;

    private LEDFrameStatus() {
    }

    public LEDFrameStatus(LEDFrame value) {
        this.value = value;
    }
}
