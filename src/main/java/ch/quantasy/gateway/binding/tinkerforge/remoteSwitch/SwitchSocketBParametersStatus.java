package ch.quantasy.gateway.binding.tinkerforge.remoteSwitch;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitch.SwitchSocketBParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SwitchSocketBParametersStatus extends AStatus{
public SwitchSocketBParameters value;
private SwitchSocketBParametersStatus(){}
public SwitchSocketBParametersStatus(SwitchSocketBParameters value){
  this.value=value;
}
}
