package ch.quantasy.gateway.message.status.dualRelay;
import ch.quantasy.gateway.message.intent.dualRelay.DeviceRelayState;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class RelayStateStatus extends AStatus{
public DeviceRelayState value;
private RelayStateStatus(){}
public RelayStateStatus(DeviceRelayState value){
  this.value=value;
}
}
