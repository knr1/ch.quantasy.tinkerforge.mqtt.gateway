package ch.quantasy.gateway.message.hallEffect;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class EdgeCountCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private EdgeCountCallbackPeriodStatus(){}
public EdgeCountCallbackPeriodStatus(Long value){
  this.value=value;
}
}
