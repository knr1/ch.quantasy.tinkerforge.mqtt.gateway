package ch.quantasy.gateway.message.joystick;
import ch.quantasy.gateway.message.joystick.DevicePositionCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class PositionCallbackThresholdStatus extends AStatus{
public DevicePositionCallbackThreshold value;
private PositionCallbackThresholdStatus(){}
public PositionCallbackThresholdStatus(DevicePositionCallbackThreshold value){
  this.value=value;
}
}