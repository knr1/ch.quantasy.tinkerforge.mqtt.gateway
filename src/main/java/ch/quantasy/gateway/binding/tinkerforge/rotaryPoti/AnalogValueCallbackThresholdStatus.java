package ch.quantasy.gateway.binding.tinkerforge.rotaryPoti;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AnalogValueCallbackThresholdStatus extends AStatus{
public DeviceAnalogValueCallbackThreshold value;
private AnalogValueCallbackThresholdStatus(){}
public AnalogValueCallbackThresholdStatus(DeviceAnalogValueCallbackThreshold value){
  this.value=value;
}
}
