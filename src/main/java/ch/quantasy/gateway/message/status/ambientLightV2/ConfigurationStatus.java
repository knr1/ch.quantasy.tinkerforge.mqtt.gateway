package ch.quantasy.gateway.message.status.ambientLightV2;
import ch.quantasy.gateway.message.intent.ambientLightV2.DeviceConfiguration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ConfigurationStatus extends AStatus{
public DeviceConfiguration value;
private ConfigurationStatus(){}
public ConfigurationStatus(DeviceConfiguration value){
  this.value=value;
}
}
