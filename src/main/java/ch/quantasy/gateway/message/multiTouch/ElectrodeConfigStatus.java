package ch.quantasy.gateway.message.multiTouch;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ElectrodeConfigStatus extends AStatus{
@Range(from=0, to=8191)
public Integer value;
private ElectrodeConfigStatus(){}
public ElectrodeConfigStatus(Integer value){
  this.value=value;
}
}
