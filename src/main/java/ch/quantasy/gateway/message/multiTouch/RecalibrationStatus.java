package ch.quantasy.gateway.message.multiTouch;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class RecalibrationStatus extends AStatus{
public Boolean value;
private RecalibrationStatus(){}
public RecalibrationStatus(Boolean value){
  this.value=value;
}
}
