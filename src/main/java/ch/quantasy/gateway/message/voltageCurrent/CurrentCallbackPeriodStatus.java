package ch.quantasy.gateway.message.voltageCurrent;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CurrentCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private CurrentCallbackPeriodStatus(){}
public CurrentCallbackPeriodStatus(Long value){
  this.value=value;
}
}