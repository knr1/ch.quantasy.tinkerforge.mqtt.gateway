package ch.quantasy.gateway.message.status.ledStrip;
import ch.quantasy.gateway.message.intent.ledStrip.LEDFrame;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class LEDFrameStatus extends AStatus{
public LEDFrame value;
private LEDFrameStatus(){}
public LEDFrameStatus(LEDFrame value){
  this.value=value;
}
}
