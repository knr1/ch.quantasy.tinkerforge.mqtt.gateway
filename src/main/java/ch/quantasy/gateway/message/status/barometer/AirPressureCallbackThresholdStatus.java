package ch.quantasy.gateway.message.status.barometer;
import ch.quantasy.gateway.message.intent.barometer.DeviceAirPressureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AirPressureCallbackThresholdStatus extends AStatus{
public DeviceAirPressureCallbackThreshold value;
private AirPressureCallbackThresholdStatus(){}
public AirPressureCallbackThresholdStatus(DeviceAirPressureCallbackThreshold value){
  this.value=value;
}
}
