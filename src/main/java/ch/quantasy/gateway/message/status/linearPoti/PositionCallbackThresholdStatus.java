package ch.quantasy.gateway.message.status.linearPoti;
import ch.quantasy.gateway.message.intent.linearPoti.DevicePositionCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class PositionCallbackThresholdStatus extends AStatus{
public DevicePositionCallbackThreshold value;
private PositionCallbackThresholdStatus(){}
public PositionCallbackThresholdStatus(DevicePositionCallbackThreshold value){
  this.value=value;
}
}
