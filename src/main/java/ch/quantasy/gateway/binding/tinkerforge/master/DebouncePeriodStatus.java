package ch.quantasy.gateway.binding.tinkerforge.master;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class DebouncePeriodStatus extends AStatus{
@Period
public Long value;
private DebouncePeriodStatus(){}
public DebouncePeriodStatus(Long value){
  this.value=value;
}
}
