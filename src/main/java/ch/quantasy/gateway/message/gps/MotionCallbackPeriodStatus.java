package ch.quantasy.gateway.message.gps;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MotionCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private MotionCallbackPeriodStatus(){}
public MotionCallbackPeriodStatus(Long value){
  this.value=value;
}
}
