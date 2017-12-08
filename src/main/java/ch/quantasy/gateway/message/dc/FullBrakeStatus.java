package ch.quantasy.gateway.message.dc;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class FullBrakeStatus extends AStatus{
public Boolean value;
private FullBrakeStatus(){}
public FullBrakeStatus(Boolean value){
  this.value=value;
}
}
