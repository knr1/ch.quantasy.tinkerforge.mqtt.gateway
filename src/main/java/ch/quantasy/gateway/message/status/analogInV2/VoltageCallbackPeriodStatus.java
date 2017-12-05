package ch.quantasy.gateway.message.status.analogInV2;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class VoltageCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private VoltageCallbackPeriodStatus(){}
public VoltageCallbackPeriodStatus(Long value){
  this.value=value;
}
}
