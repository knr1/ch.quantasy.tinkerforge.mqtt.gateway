package ch.quantasy.gateway.message.status.LCD20x4;
import ch.quantasy.gateway.message.intent.LCD20x4.DeviceConfigParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ParametersStatus extends AStatus{
public DeviceConfigParameters value;
private ParametersStatus(){}
public ParametersStatus(DeviceConfigParameters value){
  this.value=value;
}
}
