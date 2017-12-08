package ch.quantasy.gateway.message.uvLight;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class UvLightCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private UvLightCallbackPeriodStatus(){}
public UvLightCallbackPeriodStatus(Long value){
  this.value=value;
}
}
