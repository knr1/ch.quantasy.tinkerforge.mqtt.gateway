package ch.quantasy.gateway.binding.tinkerforge.temperature;
import ch.quantasy.gateway.binding.tinkerforge.temperature.DeviceTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TemperatureThresholdStatus extends AStatus{
public DeviceTemperatureCallbackThreshold value;
private TemperatureThresholdStatus(){}
public TemperatureThresholdStatus(DeviceTemperatureCallbackThreshold value){
  this.value=value;
}
}
