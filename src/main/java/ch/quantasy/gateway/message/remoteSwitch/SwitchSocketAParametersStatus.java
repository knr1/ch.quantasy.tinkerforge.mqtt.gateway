package ch.quantasy.gateway.message.remoteSwitch;
import ch.quantasy.gateway.message.remoteSwitch.SwitchSocketAParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SwitchSocketAParametersStatus extends AStatus{
public SwitchSocketAParameters value;
private SwitchSocketAParametersStatus(){}
public SwitchSocketAParametersStatus(SwitchSocketAParameters value){
  this.value=value;
}
}