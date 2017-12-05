package ch.quantasy.gateway.message.status.piezoSpeaker;
import ch.quantasy.gateway.message.intent.piezoSpeaker.MorseCodeParameter;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MorseCodeParameterStatus extends AStatus{
public MorseCodeParameter value;
private MorseCodeParameterStatus(){}
public MorseCodeParameterStatus(MorseCodeParameter value){
  this.value=value;
}
}
