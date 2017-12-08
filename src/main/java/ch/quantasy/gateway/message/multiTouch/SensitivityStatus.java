package ch.quantasy.gateway.message.multiTouch;
import java.lang.Short;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SensitivityStatus extends AStatus{
@Range(from=5, to=201)
public Short value;
private SensitivityStatus(){}
public SensitivityStatus(Short value){
  this.value=value;
}
}
