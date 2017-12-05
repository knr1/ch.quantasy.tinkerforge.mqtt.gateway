package ch.quantasy.gateway.message.status.co2;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class Co2ConcentrationCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private Co2ConcentrationCallbackPeriodStatus(){}
public Co2ConcentrationCallbackPeriodStatus(Long value){
  this.value=value;
}
}
