package ch.quantasy.gateway.message.moisture;
import ch.quantasy.gateway.message.moisture.DeviceMoistureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MoistureCallbackThresholdStatus extends AStatus{
public DeviceMoistureCallbackThreshold value;
private MoistureCallbackThresholdStatus(){}
public MoistureCallbackThresholdStatus(DeviceMoistureCallbackThreshold value){
  this.value=value;
}
}
