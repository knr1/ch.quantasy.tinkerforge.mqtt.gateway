package ch.quantasy.gateway.message.temperatureIR;
import ch.quantasy.gateway.message.temperatureIR.DeviceObjectTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ObjectTemperatureCallbackThresholdStatus extends AStatus{
public DeviceObjectTemperatureCallbackThreshold value;
private ObjectTemperatureCallbackThresholdStatus(){}
public ObjectTemperatureCallbackThresholdStatus(DeviceObjectTemperatureCallbackThreshold value){
  this.value=value;
}
}
