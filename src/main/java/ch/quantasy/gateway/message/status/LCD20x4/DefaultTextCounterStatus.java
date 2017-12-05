package ch.quantasy.gateway.message.status.LCD20x4;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class DefaultTextCounterStatus extends AStatus{
@Period(from=-1, to=2147483647)
public Integer value;
private DefaultTextCounterStatus(){}
public DefaultTextCounterStatus(Integer value){
  this.value=value;
}
}
