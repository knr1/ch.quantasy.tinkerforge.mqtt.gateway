package ch.quantasy.gateway.message.status.distanceIR;
import ch.quantasy.gateway.message.intent.distanceIR.DeviceDistanceCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class DistanceCallbackThresholdStatus extends AStatus{
public DeviceDistanceCallbackThreshold value;
private DistanceCallbackThresholdStatus(){}
public DistanceCallbackThresholdStatus(DeviceDistanceCallbackThreshold value){
  this.value=value;
}
}
