package ch.quantasy.gateway.message.analogOutV2;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class OutputVoltageStatus extends AStatus{
@Range(from=0, to=12000)
public Integer value;
private OutputVoltageStatus(){}
public OutputVoltageStatus(Integer value){
  this.value=value;
}
}
