package ch.quantasy.gateway.message.status.ambientLightV2;
import ch.quantasy.gateway.message.intent.ambientLightV2.DeviceIlluminanceCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class IlluminanceCallbackThresholdStatus extends AStatus{
public DeviceIlluminanceCallbackThreshold value;
private IlluminanceCallbackThresholdStatus(){}
public IlluminanceCallbackThresholdStatus(DeviceIlluminanceCallbackThreshold value){
  this.value=value;
}
}
