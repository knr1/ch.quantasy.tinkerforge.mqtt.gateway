package ch.quantasy.gateway.message.status.dc;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class VelocityPeriodStatus extends AStatus{
@Period
public Integer value;
private VelocityPeriodStatus(){}
public VelocityPeriodStatus(Integer value){
  this.value=value;
}
}
