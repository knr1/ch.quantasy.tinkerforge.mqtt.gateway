package ch.quantasy.gateway.message.status.remoteSwitch;
import java.lang.Short;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class RepeatsStatus extends AStatus{
@Range(from=0, to=32767)
public Short value;
private RepeatsStatus(){}
public RepeatsStatus(Short value){
  this.value=value;
}
}
