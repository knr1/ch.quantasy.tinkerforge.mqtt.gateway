package ch.quantasy.gateway.binding.tinkerforge.temperature;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TemperatureCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private TemperatureCallbackPeriodStatus(){}
public TemperatureCallbackPeriodStatus(Long value){
  this.value=value;
}
}
