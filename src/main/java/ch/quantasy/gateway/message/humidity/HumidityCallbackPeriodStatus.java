package ch.quantasy.gateway.message.humidity;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class HumidityCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private HumidityCallbackPeriodStatus(){}
public HumidityCallbackPeriodStatus(Long value){
  this.value=value;
}
}
