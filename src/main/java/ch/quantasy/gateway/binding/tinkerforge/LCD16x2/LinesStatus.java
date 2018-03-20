package ch.quantasy.gateway.binding.tinkerforge.LCD16x2;

import java.util.Set;
import java.util.HashSet;
import ch.quantasy.mqtt.gateway.client.message.annotations.SetSize;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class LinesStatus extends AStatus {

    @SetSize(max = 32, min = 0)
    public Set<DeviceWriteLine> value;

    private LinesStatus() {
        value = new HashSet();
    }

    public LinesStatus(Set<DeviceWriteLine> value) {
        this.value = value;
    }
}
