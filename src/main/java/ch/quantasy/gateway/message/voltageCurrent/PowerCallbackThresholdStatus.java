package ch.quantasy.gateway.message.voltageCurrent;
import ch.quantasy.gateway.message.voltageCurrent.DevicePowerCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class PowerCallbackThresholdStatus extends AStatus{
public DevicePowerCallbackThreshold value;
private PowerCallbackThresholdStatus(){}
public PowerCallbackThresholdStatus(DevicePowerCallbackThreshold value){
  this.value=value;
}
}
