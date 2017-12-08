package ch.quantasy.gateway.message.distanceUS;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class DistanceCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private DistanceCallbackPeriodStatus(){}
public DistanceCallbackPeriodStatus(Long value){
  this.value=value;
}
}
