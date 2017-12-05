package ch.quantasy.gateway.message.status.barometer;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ReferenceAirPressureStatus extends AStatus{
public Integer value;
private ReferenceAirPressureStatus(){}
public ReferenceAirPressureStatus(Integer value){
  this.value=value;
}
}
