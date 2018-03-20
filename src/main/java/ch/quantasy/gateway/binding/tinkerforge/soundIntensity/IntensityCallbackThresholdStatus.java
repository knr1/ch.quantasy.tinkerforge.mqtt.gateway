package ch.quantasy.gateway.binding.tinkerforge.soundIntensity;
import ch.quantasy.gateway.binding.tinkerforge.soundIntensity.DeviceSoundIntensityCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class IntensityCallbackThresholdStatus extends AStatus{
public DeviceSoundIntensityCallbackThreshold value;
private IntensityCallbackThresholdStatus(){}
public IntensityCallbackThresholdStatus(DeviceSoundIntensityCallbackThreshold value){
  this.value=value;
}
}
