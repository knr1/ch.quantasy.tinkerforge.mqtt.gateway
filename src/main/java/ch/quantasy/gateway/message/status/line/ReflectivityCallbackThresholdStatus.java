package ch.quantasy.gateway.message.status.line;
import ch.quantasy.gateway.message.intent.line.DeviceReflectivityCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ReflectivityCallbackThresholdStatus extends AStatus{
public DeviceReflectivityCallbackThreshold value;
private ReflectivityCallbackThresholdStatus(){}
public ReflectivityCallbackThresholdStatus(DeviceReflectivityCallbackThreshold value){
  this.value=value;
}
}
