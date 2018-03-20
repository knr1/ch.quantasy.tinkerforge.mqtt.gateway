package ch.quantasy.gateway.binding.tinkerforge.thermoCouple;
import ch.quantasy.gateway.binding.tinkerforge.thermoCouple.DeviceTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TemperatureCallbackThresholdStatus extends AStatus{
public DeviceTemperatureCallbackThreshold value;
private TemperatureCallbackThresholdStatus(){}
public TemperatureCallbackThresholdStatus(DeviceTemperatureCallbackThreshold value){
  this.value=value;
}
}
