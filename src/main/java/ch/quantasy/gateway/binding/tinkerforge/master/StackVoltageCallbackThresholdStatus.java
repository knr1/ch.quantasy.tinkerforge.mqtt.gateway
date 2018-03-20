package ch.quantasy.gateway.binding.tinkerforge.master;
import ch.quantasy.gateway.binding.tinkerforge.master.StackVoltageCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class StackVoltageCallbackThresholdStatus extends AStatus{
public StackVoltageCallbackThreshold value;
private StackVoltageCallbackThresholdStatus(){}
public StackVoltageCallbackThresholdStatus(StackVoltageCallbackThreshold value){
  this.value=value;
}
}
