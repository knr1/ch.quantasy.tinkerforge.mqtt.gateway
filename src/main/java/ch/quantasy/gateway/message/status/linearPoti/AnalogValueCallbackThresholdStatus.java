package ch.quantasy.gateway.message.status.linearPoti;
import ch.quantasy.gateway.message.intent.linearPoti.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AnalogValueCallbackThresholdStatus extends AStatus{
public DeviceAnalogValueCallbackThreshold value;
private AnalogValueCallbackThresholdStatus(){}
public AnalogValueCallbackThresholdStatus(DeviceAnalogValueCallbackThreshold value){
  this.value=value;
}
}
