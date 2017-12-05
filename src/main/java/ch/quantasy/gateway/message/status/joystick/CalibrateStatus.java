package ch.quantasy.gateway.message.status.joystick;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CalibrateStatus extends AStatus{
public Boolean value;
private CalibrateStatus(){}
public CalibrateStatus(Boolean value){
  this.value=value;
}
}
