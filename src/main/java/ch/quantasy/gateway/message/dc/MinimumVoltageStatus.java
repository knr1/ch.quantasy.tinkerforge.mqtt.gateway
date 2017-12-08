package ch.quantasy.gateway.message.dc;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MinimumVoltageStatus extends AStatus{
@Range(from=6000, to=2147483647)
public Integer value;
private MinimumVoltageStatus(){}
public MinimumVoltageStatus(Integer value){
  this.value=value;
}
}
