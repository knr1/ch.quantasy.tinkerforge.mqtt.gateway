package ch.quantasy.gateway.message.status.servo;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class OutputVoltageStatus extends AStatus{
@Range(from=2000, to=9000)
public Integer value;
private OutputVoltageStatus(){}
public OutputVoltageStatus(Integer value){
  this.value=value;
}
}
