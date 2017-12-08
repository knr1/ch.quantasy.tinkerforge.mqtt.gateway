package ch.quantasy.gateway.message.uvLight;
import ch.quantasy.gateway.message.uvLight.DeviceUVLightCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class UvLightCallbackThresholdStatus extends AStatus{
public DeviceUVLightCallbackThreshold value;
private UvLightCallbackThresholdStatus(){}
public UvLightCallbackThresholdStatus(DeviceUVLightCallbackThreshold value){
  this.value=value;
}
}
