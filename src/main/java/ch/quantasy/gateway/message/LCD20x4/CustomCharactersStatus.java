package ch.quantasy.gateway.message.LCD20x4;

import java.util.Set;
import java.util.HashSet;
import ch.quantasy.mqtt.gateway.client.message.annotations.SetSize;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class CustomCharactersStatus extends AStatus {

    @SetSize(max = 8, min = 0)
    public Set<DeviceCustomCharacter> value;

    private CustomCharactersStatus() {
        value = new HashSet();
    }

    public CustomCharactersStatus(Set<DeviceCustomCharacter> value) {
        this.value = value;
    }
}
