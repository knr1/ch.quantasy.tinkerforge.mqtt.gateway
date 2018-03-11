package ch.quantasy.gateway.message.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class StepModeStatus extends AStatus {

    public StepMode value;

    private StepModeStatus() {
    }

    public StepModeStatus(StepMode value) {
        this.value = value;
    }
}
