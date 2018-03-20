package ch.quantasy.gateway.binding.tinkerforge.uvLight;
import ch.quantasy.gateway.binding.tinkerforge.uvLight.DeviceUVLightCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class UvLightCallbackThresholdStatus extends AStatus{
public DeviceUVLightCallbackThreshold value;
private UvLightCallbackThresholdStatus(){}
public UvLightCallbackThresholdStatus(DeviceUVLightCallbackThreshold value){
  this.value=value;
}
}
