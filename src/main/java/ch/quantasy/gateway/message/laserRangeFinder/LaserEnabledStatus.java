package ch.quantasy.gateway.message.laserRangeFinder;
import java.lang.Boolean;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class LaserEnabledStatus extends AStatus{
public Boolean value;
private LaserEnabledStatus(){}
public LaserEnabledStatus(Boolean value){
  this.value=value;
}
}
