package ch.quantasy.gateway.binding.tinkerforge.voltageCurrent;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.DeviceCurrentCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CurrentCalbackThresholdStatus extends AStatus{
public DeviceCurrentCallbackThreshold value;
private CurrentCalbackThresholdStatus(){}
public CurrentCalbackThresholdStatus(DeviceCurrentCallbackThreshold value){
  this.value=value;
}
}
