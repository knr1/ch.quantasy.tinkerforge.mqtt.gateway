package ch.quantasy.gateway.message.status.temperature;
import ch.quantasy.gateway.message.intent.temperature.DeviceTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TemperatureThresholdStatus extends AStatus{
public DeviceTemperatureCallbackThreshold value;
private TemperatureThresholdStatus(){}
public TemperatureThresholdStatus(DeviceTemperatureCallbackThreshold value){
  this.value=value;
}
}
