package ch.quantasy.gateway.binding.tinkerforge.solidStateRelay;
import ch.quantasy.gateway.binding.tinkerforge.solidStateRelay.DeviceMonoflopParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MonoflopParametersStatus extends AStatus{
public DeviceMonoflopParameters value;
private MonoflopParametersStatus(){}
public MonoflopParametersStatus(DeviceMonoflopParameters value){
  this.value=value;
}
}
