package ch.quantasy.gateway.message.line;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ReflectivityCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private ReflectivityCallbackPeriodStatus(){}
public ReflectivityCallbackPeriodStatus(Long value){
  this.value=value;
}
}
