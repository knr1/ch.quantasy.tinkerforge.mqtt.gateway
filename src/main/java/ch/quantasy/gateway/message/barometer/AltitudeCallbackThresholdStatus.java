package ch.quantasy.gateway.message.barometer;
import ch.quantasy.gateway.message.barometer.DeviceAltitudeCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AltitudeCallbackThresholdStatus extends AStatus{
public DeviceAltitudeCallbackThreshold value;
private AltitudeCallbackThresholdStatus(){}
public AltitudeCallbackThresholdStatus(DeviceAltitudeCallbackThreshold value){
  this.value=value;
}
}
