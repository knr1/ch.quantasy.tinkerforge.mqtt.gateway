package ch.quantasy.gateway.message.thermoCouple;
import ch.quantasy.gateway.message.thermoCouple.DeviceTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TemperatureCallbackThresholdStatus extends AStatus{
public DeviceTemperatureCallbackThreshold value;
private TemperatureCallbackThresholdStatus(){}
public TemperatureCallbackThresholdStatus(DeviceTemperatureCallbackThreshold value){
  this.value=value;
}
}
