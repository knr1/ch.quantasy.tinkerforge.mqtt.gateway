package ch.quantasy.gateway.binding.stackManager;

import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.annotations.NonNull;
import ch.quantasy.mqtt.gateway.client.message.AStatus;

public class ConnectStatus extends AStatus {

    @NonNull()
    public Boolean value;

    private ConnectStatus() {
    }

    public ConnectStatus(Boolean value) {
        this.value = value;
    }
}
