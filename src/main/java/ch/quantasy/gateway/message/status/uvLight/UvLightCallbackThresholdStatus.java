package ch.quantasy.gateway.message.status.uvLight;
import ch.quantasy.gateway.message.intent.uvLight.DeviceUVLightCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class UvLightCallbackThresholdStatus extends AStatus{
public DeviceUVLightCallbackThreshold value;
private UvLightCallbackThresholdStatus(){}
public UvLightCallbackThresholdStatus(DeviceUVLightCallbackThreshold value){
  this.value=value;
}
}
