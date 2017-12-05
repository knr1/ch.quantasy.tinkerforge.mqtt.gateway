package ch.quantasy.gateway.message.status.laserRangeFinder;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceVelocityCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class VelocityCallbackThresholdStatus extends AStatus{
public DeviceVelocityCallbackThreshold value;
private VelocityCallbackThresholdStatus(){}
public VelocityCallbackThresholdStatus(DeviceVelocityCallbackThreshold value){
  this.value=value;
}
}
