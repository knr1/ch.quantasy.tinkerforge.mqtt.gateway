package ch.quantasy.gateway.message.status.soundIntensity;
import ch.quantasy.gateway.message.intent.soundIntensity.DeviceSoundIntensityCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class IntensityCallbackThresholdStatus extends AStatus{
public DeviceSoundIntensityCallbackThreshold value;
private IntensityCallbackThresholdStatus(){}
public IntensityCallbackThresholdStatus(DeviceSoundIntensityCallbackThreshold value){
  this.value=value;
}
}
