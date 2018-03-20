package ch.quantasy.gateway.binding.tinkerforge.servo;

import java.util.Set;
import java.util.HashSet;

import ch.quantasy.gateway.binding.tinkerforge.servo.Servo;
import ch.quantasy.mqtt.gateway.client.message.annotations.SetSize;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ServosStatus extends AStatus{

    @SetSize(max = 7, min = 0)
    public Set<Servo> value;

    private ServosStatus() {
        value = new HashSet();
    }

    public ServosStatus(Set<Servo> value) {
        this.value = value;
    }
}
