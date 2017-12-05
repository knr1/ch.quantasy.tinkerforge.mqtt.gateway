package ch.quantasy.gateway.message.status.laserRangeFinder;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceMode;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ModeStatus extends AStatus{
public DeviceMode value;
private ModeStatus(){}
public ModeStatus(DeviceMode value){
  this.value=value;
}
}
