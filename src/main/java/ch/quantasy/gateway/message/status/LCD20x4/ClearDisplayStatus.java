package ch.quantasy.gateway.message.status.LCD20x4;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ClearDisplayStatus extends AStatus{
public Boolean value;
private ClearDisplayStatus(){}
public ClearDisplayStatus(Boolean value){
  this.value=value;
}
}
