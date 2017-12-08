package ch.quantasy.gateway.message.IMU;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class OrientationCalculationStatus extends AStatus{
public Boolean value;
private OrientationCalculationStatus(){}
public OrientationCalculationStatus(Boolean value){
  this.value=value;
}
}
