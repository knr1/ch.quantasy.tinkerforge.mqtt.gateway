package ch.quantasy.gateway.message.status.hallEffect;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class EdgeCountResetStatus extends AStatus{
public Boolean value;
private EdgeCountResetStatus(){}
public EdgeCountResetStatus(Boolean value){
  this.value=value;
}
}
