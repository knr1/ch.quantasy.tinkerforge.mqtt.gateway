package ch.quantasy.gateway.message.status.solidState;
import ch.quantasy.gateway.message.intent.solidState.DeviceMonoflopParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MonoflopParametersStatus extends AStatus{
public DeviceMonoflopParameters value;
private MonoflopParametersStatus(){}
public MonoflopParametersStatus(DeviceMonoflopParameters value){
  this.value=value;
}
}
