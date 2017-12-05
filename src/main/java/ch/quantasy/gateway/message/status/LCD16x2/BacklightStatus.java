package ch.quantasy.gateway.message.status.LCD16x2;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class BacklightStatus extends AStatus{
public Boolean value;
private BacklightStatus(){}
public BacklightStatus(Boolean value){
  this.value=value;
}
}
