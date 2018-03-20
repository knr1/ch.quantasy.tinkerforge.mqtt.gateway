package ch.quantasy.gateway.binding.tinkerforge.temperature;
import ch.quantasy.gateway.binding.tinkerforge.temperature.DeviceI2CMode;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ModeStatus extends AStatus{
public DeviceI2CMode value;
private ModeStatus(){}
public ModeStatus(DeviceI2CMode value){
  this.value=value;
}
}
