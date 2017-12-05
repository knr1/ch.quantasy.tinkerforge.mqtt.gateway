package ch.quantasy.gateway.message.status.gps;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class StatusCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private StatusCallbackPeriodStatus(){}
public StatusCallbackPeriodStatus(Long value){
  this.value=value;
}
}
