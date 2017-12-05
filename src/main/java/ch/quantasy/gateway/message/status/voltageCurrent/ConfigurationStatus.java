package ch.quantasy.gateway.message.status.voltageCurrent;
import ch.quantasy.gateway.message.intent.voltageCurrent.DeviceConfiguration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ConfigurationStatus extends AStatus{
public DeviceConfiguration value;
private ConfigurationStatus(){}
public ConfigurationStatus(DeviceConfiguration value){
  this.value=value;
}
}
