package ch.quantasy.gateway.message.status.loadCell;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TareStatus extends AStatus{
public Boolean value;
private TareStatus(){}
public TareStatus(Boolean value){
  this.value=value;
}
}
