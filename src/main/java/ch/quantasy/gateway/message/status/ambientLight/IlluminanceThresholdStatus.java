package ch.quantasy.gateway.message.status.ambientLight;
import ch.quantasy.gateway.message.intent.ambientLight.DeviceIlluminanceCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class IlluminanceThresholdStatus extends AStatus{
public DeviceIlluminanceCallbackThreshold value;
private IlluminanceThresholdStatus(){}
public IlluminanceThresholdStatus(DeviceIlluminanceCallbackThreshold value){
  this.value=value;
}
}
