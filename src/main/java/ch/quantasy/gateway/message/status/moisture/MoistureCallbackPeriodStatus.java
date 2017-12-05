package ch.quantasy.gateway.message.status.moisture;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MoistureCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private MoistureCallbackPeriodStatus(){}
public MoistureCallbackPeriodStatus(Long value){
  this.value=value;
}
}
