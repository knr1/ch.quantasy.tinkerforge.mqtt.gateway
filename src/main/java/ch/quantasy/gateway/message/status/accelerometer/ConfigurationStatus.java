package ch.quantasy.gateway.message.status.accelerometer;
import ch.quantasy.gateway.message.intent.accelerometer.DeviceConfiguration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ConfigurationStatus extends AStatus{
public DeviceConfiguration value;
private ConfigurationStatus(){}
public ConfigurationStatus(DeviceConfiguration value){
  this.value=value;
}
}
