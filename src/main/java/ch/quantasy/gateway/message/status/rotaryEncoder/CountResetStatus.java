package ch.quantasy.gateway.message.status.rotaryEncoder;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CountResetStatus extends AStatus{
public Boolean value;
private CountResetStatus(){}
public CountResetStatus(Boolean value){
  this.value=value;
}
}
