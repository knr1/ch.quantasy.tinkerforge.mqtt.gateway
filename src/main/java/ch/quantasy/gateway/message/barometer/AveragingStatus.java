package ch.quantasy.gateway.message.barometer;
import ch.quantasy.gateway.message.barometer.DeviceAveraging;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AveragingStatus extends AStatus{
public DeviceAveraging value;
private AveragingStatus(){}
public AveragingStatus(DeviceAveraging value){
  this.value=value;
}
}
