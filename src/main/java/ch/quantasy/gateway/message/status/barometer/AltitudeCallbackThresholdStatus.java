package ch.quantasy.gateway.message.status.barometer;
import ch.quantasy.gateway.message.intent.barometer.DeviceAltitudeCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AltitudeCallbackThresholdStatus extends AStatus{
public DeviceAltitudeCallbackThreshold value;
private AltitudeCallbackThresholdStatus(){}
public AltitudeCallbackThresholdStatus(DeviceAltitudeCallbackThreshold value){
  this.value=value;
}
}
