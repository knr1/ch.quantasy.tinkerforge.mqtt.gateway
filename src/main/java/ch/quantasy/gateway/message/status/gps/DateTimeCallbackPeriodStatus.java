package ch.quantasy.gateway.message.status.gps;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class DateTimeCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private DateTimeCallbackPeriodStatus(){}
public DateTimeCallbackPeriodStatus(Long value){
  this.value=value;
}
}
