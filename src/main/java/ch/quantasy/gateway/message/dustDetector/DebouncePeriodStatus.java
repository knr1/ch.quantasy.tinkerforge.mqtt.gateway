package ch.quantasy.gateway.message.dustDetector;
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
