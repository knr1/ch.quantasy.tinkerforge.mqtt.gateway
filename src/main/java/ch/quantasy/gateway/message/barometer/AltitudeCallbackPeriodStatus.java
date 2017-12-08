package ch.quantasy.gateway.message.barometer;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AltitudeCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private AltitudeCallbackPeriodStatus(){}
public AltitudeCallbackPeriodStatus(Long value){
  this.value=value;
}
}
