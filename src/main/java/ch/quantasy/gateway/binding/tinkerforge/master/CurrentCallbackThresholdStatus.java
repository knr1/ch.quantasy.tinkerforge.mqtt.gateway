package ch.quantasy.gateway.binding.tinkerforge.master;
import ch.quantasy.gateway.binding.tinkerforge.master.StackCurrentCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CurrentCallbackThresholdStatus extends AStatus{
public StackCurrentCallbackThreshold value;
private CurrentCallbackThresholdStatus(){}
public CurrentCallbackThresholdStatus(StackCurrentCallbackThreshold value){
  this.value=value;
}
}
