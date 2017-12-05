package ch.quantasy.gateway.message.status.thermoCouple;
import ch.quantasy.gateway.message.intent.thermoCouple.DeviceTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TemperatureCallbackThresholdStatus extends AStatus{
public DeviceTemperatureCallbackThreshold value;
private TemperatureCallbackThresholdStatus(){}
public TemperatureCallbackThresholdStatus(DeviceTemperatureCallbackThreshold value){
  this.value=value;
}
}
