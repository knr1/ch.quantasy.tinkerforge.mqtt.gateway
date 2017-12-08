package ch.quantasy.gateway.message.gps;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CoordinatesCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private CoordinatesCallbackPeriodStatus(){}
public CoordinatesCallbackPeriodStatus(Long value){
  this.value=value;
}
}
