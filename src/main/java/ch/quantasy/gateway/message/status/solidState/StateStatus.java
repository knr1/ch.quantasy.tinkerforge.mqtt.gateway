package ch.quantasy.gateway.message.status.solidState;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class StateStatus extends AStatus{
public Boolean value;
private StateStatus(){}
public StateStatus(Boolean value){
  this.value=value;
}
}
