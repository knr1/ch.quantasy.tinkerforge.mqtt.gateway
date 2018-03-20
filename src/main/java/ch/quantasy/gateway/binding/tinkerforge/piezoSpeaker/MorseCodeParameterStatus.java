package ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker;
import ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker.MorseCodeParameter;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MorseCodeParameterStatus extends AStatus{
public MorseCodeParameter value;
private MorseCodeParameterStatus(){}
public MorseCodeParameterStatus(MorseCodeParameter value){
  this.value=value;
}
}
