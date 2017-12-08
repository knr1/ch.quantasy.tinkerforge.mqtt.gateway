package ch.quantasy.gateway.message.remoteSwitch;
import ch.quantasy.gateway.message.remoteSwitch.SwitchSocketCParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SwitchSocketCParametersStatus extends AStatus{
public SwitchSocketCParameters value;
private SwitchSocketCParametersStatus(){}
public SwitchSocketCParametersStatus(SwitchSocketCParameters value){
  this.value=value;
}
}
