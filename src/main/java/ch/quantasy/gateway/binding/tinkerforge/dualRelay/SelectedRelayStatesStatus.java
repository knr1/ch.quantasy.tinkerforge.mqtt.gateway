package ch.quantasy.gateway.binding.tinkerforge.dualRelay;

import java.util.Set;
import java.util.HashSet;
import ch.quantasy.mqtt.gateway.client.message.annotations.SetSize;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class SelectedRelayStatesStatus extends AStatus {

    @SetSize(max = 2, min = 0)
    public Set<DeviceSelectedRelayState> value;

    private SelectedRelayStatesStatus() {
        value = new HashSet();
    }

    public SelectedRelayStatesStatus(Set<DeviceSelectedRelayState> value) {
        this.value = value;
    }
}
