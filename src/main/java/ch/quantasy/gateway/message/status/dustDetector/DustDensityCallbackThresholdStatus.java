package ch.quantasy.gateway.message.status.dustDetector;
import ch.quantasy.gateway.message.intent.dustDetector.DeviceDustDensityCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class DustDensityCallbackThresholdStatus extends AStatus{
public DeviceDustDensityCallbackThreshold value;
private DustDensityCallbackThresholdStatus(){}
public DustDensityCallbackThresholdStatus(DeviceDustDensityCallbackThreshold value){
  this.value=value;
}
}
