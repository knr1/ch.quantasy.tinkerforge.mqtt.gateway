package ch.quantasy.gateway.message.status.laserRangeFinder;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceAveraging;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MovingAverageStatus extends AStatus{
public DeviceAveraging value;
private MovingAverageStatus(){}
public MovingAverageStatus(DeviceAveraging value){
  this.value=value;
}
}
