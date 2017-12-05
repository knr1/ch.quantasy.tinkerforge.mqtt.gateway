package ch.quantasy.gateway.message.status.dualButton;
import ch.quantasy.gateway.message.intent.dualButton.DeviceLEDState;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class LedStateStatus extends AStatus{
public DeviceLEDState value;
private LedStateStatus(){}
public LedStateStatus(DeviceLEDState value){
  this.value=value;
}
}
