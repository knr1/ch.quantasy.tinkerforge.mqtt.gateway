package ch.quantasy.gateway.message.status.hallEffect;
import ch.quantasy.gateway.message.intent.hallEffect.DeviceConfiguration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class EdgeCountConfigurationStatus extends AStatus{
public DeviceConfiguration value;
private EdgeCountConfigurationStatus(){}
public EdgeCountConfigurationStatus(DeviceConfiguration value){
  this.value=value;
}
}
