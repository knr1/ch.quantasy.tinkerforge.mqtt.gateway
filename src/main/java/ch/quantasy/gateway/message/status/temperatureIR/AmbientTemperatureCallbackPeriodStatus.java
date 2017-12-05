package ch.quantasy.gateway.message.status.temperatureIR;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AmbientTemperatureCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private AmbientTemperatureCallbackPeriodStatus(){}
public AmbientTemperatureCallbackPeriodStatus(Long value){
  this.value=value;
}
}
