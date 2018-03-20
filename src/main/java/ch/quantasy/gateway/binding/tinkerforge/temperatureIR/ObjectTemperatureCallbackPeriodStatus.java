package ch.quantasy.gateway.binding.tinkerforge.temperatureIR;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ObjectTemperatureCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private ObjectTemperatureCallbackPeriodStatus(){}
public ObjectTemperatureCallbackPeriodStatus(Long value){
  this.value=value;
}
}
