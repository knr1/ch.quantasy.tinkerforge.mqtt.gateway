package ch.quantasy.gateway.message.color;
import ch.quantasy.gateway.message.color.DeviceConfiguration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ConfigStatus extends AStatus{
public DeviceConfiguration value;
private ConfigStatus(){}
public ConfigStatus(DeviceConfiguration value){
  this.value=value;
}
}
