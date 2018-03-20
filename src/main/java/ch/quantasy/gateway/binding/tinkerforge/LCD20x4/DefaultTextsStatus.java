package ch.quantasy.gateway.binding.tinkerforge.LCD20x4;

import java.util.Set;
import java.util.HashSet;
import ch.quantasy.mqtt.gateway.client.message.annotations.SetSize;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class DefaultTextsStatus extends AStatus {

    @SetSize(max = 4, min = 0)
    public Set<DeviceDefaultText> value;

    private DefaultTextsStatus() {
        value = new HashSet();
    }

    public DefaultTextsStatus(Set<DeviceDefaultText> value) {
        this.value = value;
    }
}
