package ch.quantasy.gateway.binding.tinkerforge.voltageCurrent;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class PowerCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private PowerCallbackPeriodStatus(){}
public PowerCallbackPeriodStatus(Long value){
  this.value=value;
}
}
