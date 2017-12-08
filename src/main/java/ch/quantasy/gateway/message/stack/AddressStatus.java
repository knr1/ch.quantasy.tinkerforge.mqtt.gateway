package ch.quantasy.gateway.message.stack;
import ch.quantasy.gateway.message.stack.TinkerforgeStackAddress;
import ch.quantasy.mqtt.gateway.client.message.annotations.NonNull;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AddressStatus extends AStatus{
@NonNull()
public TinkerforgeStackAddress value;
private AddressStatus(){}
public AddressStatus(TinkerforgeStackAddress value){
  this.value=value;
}
}
