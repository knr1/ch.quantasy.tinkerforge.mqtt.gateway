package ch.quantasy.gateway.message.stepper;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
public class TargetPositionStatus extends AStatus{
@Range(from = 0, to = Integer.MAX_VALUE)
public Integer value;
private TargetPositionStatus(){}
public TargetPositionStatus(Integer value){
  this.value=value;
}
}
