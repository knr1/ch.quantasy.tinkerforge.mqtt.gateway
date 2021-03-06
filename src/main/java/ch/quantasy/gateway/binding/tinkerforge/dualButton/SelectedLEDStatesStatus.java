package ch.quantasy.gateway.binding.tinkerforge.dualButton;

import java.util.Set;
import java.util.HashSet;
import ch.quantasy.mqtt.gateway.client.message.annotations.SetSize;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class SelectedLEDStatesStatus extends AStatus {

    @SetSize(max = 2, min = 0)
    public Set<DeviceSelectedLEDStateParameters> value;

    private SelectedLEDStatesStatus() {
        value = new HashSet();
    }

    public SelectedLEDStatesStatus(Set<DeviceSelectedLEDStateParameters> value) {
        this.value = value;
    }
}
