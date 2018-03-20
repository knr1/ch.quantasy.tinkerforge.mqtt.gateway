package ch.quantasy.gateway.binding.tinkerforge.voltageCurrent;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.DevicePowerCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class PowerCallbackThresholdStatus extends AStatus{
public DevicePowerCallbackThreshold value;
private PowerCallbackThresholdStatus(){}
public PowerCallbackThresholdStatus(DevicePowerCallbackThreshold value){
  this.value=value;
}
}
