package ch.quantasy.gateway.binding.tinkerforge.ptc;
import ch.quantasy.gateway.binding.tinkerforge.ptc.DeviceTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TemperatureThresholdStatus extends AStatus{
public DeviceTemperatureCallbackThreshold value;
private TemperatureThresholdStatus(){}
public TemperatureThresholdStatus(DeviceTemperatureCallbackThreshold value){
  this.value=value;
}
}
