package ch.quantasy.gateway.message.dc;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class PwmFrequencyStatus extends AStatus{
@Range(from=1, to=20000)
public Integer value;
private PwmFrequencyStatus(){}
public PwmFrequencyStatus(Integer value){
  this.value=value;
}
}
