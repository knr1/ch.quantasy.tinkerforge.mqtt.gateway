package ch.quantasy.gateway.binding.tinkerforge.temperatureIR;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.DeviceObjectTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ObjectTemperatureCallbackThresholdStatus extends AStatus{
public DeviceObjectTemperatureCallbackThreshold value;
private ObjectTemperatureCallbackThresholdStatus(){}
public ObjectTemperatureCallbackThresholdStatus(DeviceObjectTemperatureCallbackThreshold value){
  this.value=value;
}
}
