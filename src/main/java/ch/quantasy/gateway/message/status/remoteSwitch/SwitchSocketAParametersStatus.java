package ch.quantasy.gateway.message.status.remoteSwitch;
import ch.quantasy.gateway.message.intent.remoteSwitch.SwitchSocketAParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SwitchSocketAParametersStatus extends AStatus{
public SwitchSocketAParameters value;
private SwitchSocketAParametersStatus(){}
public SwitchSocketAParametersStatus(SwitchSocketAParameters value){
  this.value=value;
}
}
