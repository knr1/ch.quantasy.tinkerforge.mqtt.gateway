package ch.quantasy.gateway.message.status.accelerometer;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CallbackPeriodStatus extends AStatus{
@Period
public Long value;
private CallbackPeriodStatus(){}
public CallbackPeriodStatus(Long value){
  this.value=value;
}
}
