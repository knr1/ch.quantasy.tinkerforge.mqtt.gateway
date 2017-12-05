package ch.quantasy.gateway.message.status.laserRangeFinder;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceConfiguration;
import ch.quantasy.gateway.message.intent.laserRangeFinder.SensorHardware;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SensorHardwareStatus extends AStatus{
public SensorHardware value;
private SensorHardwareStatus(){}
public SensorHardwareStatus(SensorHardware value){
  this.value=value;
}
}
