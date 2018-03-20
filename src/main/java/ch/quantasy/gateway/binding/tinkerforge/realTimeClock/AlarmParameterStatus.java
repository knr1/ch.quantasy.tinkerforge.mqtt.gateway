package ch.quantasy.gateway.binding.tinkerforge.realTimeClock;
import ch.quantasy.gateway.binding.tinkerforge.realTimeClock.AlarmParamter;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AlarmParameterStatus extends AStatus{
public AlarmParamter value;
private AlarmParameterStatus(){}
public AlarmParameterStatus(AlarmParamter value){
  this.value=value;
}
}
