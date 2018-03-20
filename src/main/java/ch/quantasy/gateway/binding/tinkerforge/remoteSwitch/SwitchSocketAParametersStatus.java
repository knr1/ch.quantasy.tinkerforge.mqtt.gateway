package ch.quantasy.gateway.binding.tinkerforge.remoteSwitch;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitch.SwitchSocketAParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SwitchSocketAParametersStatus extends AStatus{
public SwitchSocketAParameters value;
private SwitchSocketAParametersStatus(){}
public SwitchSocketAParametersStatus(SwitchSocketAParameters value){
  this.value=value;
}
}
