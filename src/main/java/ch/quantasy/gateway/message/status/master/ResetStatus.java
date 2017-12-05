package ch.quantasy.gateway.message.status.master;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ResetStatus extends AStatus{
public Boolean value;
private ResetStatus(){}
public ResetStatus(Boolean value){
  this.value=value;
}
}
