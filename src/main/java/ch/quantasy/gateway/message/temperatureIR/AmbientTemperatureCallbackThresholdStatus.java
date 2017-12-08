package ch.quantasy.gateway.message.temperatureIR;
import ch.quantasy.gateway.message.temperatureIR.DeviceAmbientTemperatureCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AmbientTemperatureCallbackThresholdStatus extends AStatus{
public DeviceAmbientTemperatureCallbackThreshold value;
private AmbientTemperatureCallbackThresholdStatus(){}
public AmbientTemperatureCallbackThresholdStatus(DeviceAmbientTemperatureCallbackThreshold value){
  this.value=value;
}
}
