package ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class RemoteSwitchConfigurationStatus extends AStatus{
public RemoteSwitchConfiguration value;
private RemoteSwitchConfigurationStatus(){}
public RemoteSwitchConfigurationStatus(RemoteSwitchConfiguration value){
  this.value=value;
}
}
