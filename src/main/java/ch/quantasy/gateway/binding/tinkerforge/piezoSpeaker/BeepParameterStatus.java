package ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker;
import ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker.BeepParameter;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class BeepParameterStatus extends AStatus{
public BeepParameter value;
private BeepParameterStatus(){}
public BeepParameterStatus(BeepParameter value){
  this.value=value;
}
}
