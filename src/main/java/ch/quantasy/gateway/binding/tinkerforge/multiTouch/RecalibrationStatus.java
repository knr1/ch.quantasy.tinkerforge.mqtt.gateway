package ch.quantasy.gateway.binding.tinkerforge.multiTouch;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class RecalibrationStatus extends AStatus{
public Boolean value;
private RecalibrationStatus(){}
public RecalibrationStatus(Boolean value){
  this.value=value;
}
}
