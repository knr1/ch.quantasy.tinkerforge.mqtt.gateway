package ch.quantasy.gateway.message.status.color;
import ch.quantasy.gateway.message.intent.color.DeviceConfiguration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ConfigStatus extends AStatus{
public DeviceConfiguration value;
private ConfigStatus(){}
public ConfigStatus(DeviceConfiguration value){
  this.value=value;
}
}
