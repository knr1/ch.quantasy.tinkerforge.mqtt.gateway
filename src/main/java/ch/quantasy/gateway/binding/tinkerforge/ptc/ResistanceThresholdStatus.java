package ch.quantasy.gateway.binding.tinkerforge.ptc;
import ch.quantasy.gateway.binding.tinkerforge.ptc.DeviceResistanceCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ResistanceThresholdStatus extends AStatus{
public DeviceResistanceCallbackThreshold value;
private ResistanceThresholdStatus(){}
public ResistanceThresholdStatus(DeviceResistanceCallbackThreshold value){
  this.value=value;
}
}
