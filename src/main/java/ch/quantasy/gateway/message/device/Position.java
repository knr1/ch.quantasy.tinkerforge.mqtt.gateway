package ch.quantasy.gateway.message.device;

import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.ArraySize;
import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;

public class Position extends AStatus {

    @Choice(values = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d"})
    public char value;

    private Position() {
    }

    public Position(char value) {
        this.value = value;
    }
}
