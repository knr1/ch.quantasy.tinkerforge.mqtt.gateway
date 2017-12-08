package ch.quantasy.gateway.message.soundIntensity;
import ch.quantasy.gateway.message.soundIntensity.DeviceSoundIntensityCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class IntensityCallbackThresholdStatus extends AStatus{
public DeviceSoundIntensityCallbackThreshold value;
private IntensityCallbackThresholdStatus(){}
public IntensityCallbackThresholdStatus(DeviceSoundIntensityCallbackThreshold value){
  this.value=value;
}
}
