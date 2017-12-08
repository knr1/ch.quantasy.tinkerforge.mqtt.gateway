package ch.quantasy.gateway.message.analogInV2;
import java.lang.Short;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MovingAverageStatus extends AStatus{
@Period
public Short value;
private MovingAverageStatus(){}
public MovingAverageStatus(Short value){
  this.value=value;
}
}
