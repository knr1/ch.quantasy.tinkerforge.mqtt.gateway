package ch.quantasy.gateway.binding.tinkerforge.dualRelay;

import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class RelayStateStatus extends AStatus {

    public DeviceRelayState value;

    private RelayStateStatus() {
    }

    public RelayStateStatus(DeviceRelayState value) {
        this.value = value;
    }
}
