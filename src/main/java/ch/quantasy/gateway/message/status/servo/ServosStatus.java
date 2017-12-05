package ch.quantasy.gateway.message.status.servo;

import java.util.Set;
import java.util.HashSet;

import ch.quantasy.gateway.message.intent.servo.Servo;
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
