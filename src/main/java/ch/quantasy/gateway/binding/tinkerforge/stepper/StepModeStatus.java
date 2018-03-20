package ch.quantasy.gateway.binding.tinkerforge.stepper;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class StepModeStatus extends AStatus {

    public StepMode value;

    private StepModeStatus() {
    }

    public StepModeStatus(StepMode value) {
        this.value = value;
    }
}
