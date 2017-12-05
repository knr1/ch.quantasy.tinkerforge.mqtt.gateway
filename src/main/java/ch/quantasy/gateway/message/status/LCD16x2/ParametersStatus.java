package ch.quantasy.gateway.message.status.LCD16x2;
import ch.quantasy.gateway.message.intent.LCD16x2.DeviceConfigParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ParametersStatus extends AStatus{
public DeviceConfigParameters value;
private ParametersStatus(){}
public ParametersStatus(DeviceConfigParameters value){
  this.value=value;
}
}
