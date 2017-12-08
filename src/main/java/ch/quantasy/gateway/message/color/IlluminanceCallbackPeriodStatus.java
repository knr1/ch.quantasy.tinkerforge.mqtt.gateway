package ch.quantasy.gateway.message.color;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class IlluminanceCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private IlluminanceCallbackPeriodStatus(){}
public IlluminanceCallbackPeriodStatus(Long value){
  this.value=value;
}
}
