package ch.quantasy.gateway.message.status.piezoSpeaker;
import ch.quantasy.gateway.message.intent.piezoSpeaker.BeepParameter;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class BeepParameterStatus extends AStatus{
public BeepParameter value;
private BeepParameterStatus(){}
public BeepParameterStatus(BeepParameter value){
  this.value=value;
}
}
