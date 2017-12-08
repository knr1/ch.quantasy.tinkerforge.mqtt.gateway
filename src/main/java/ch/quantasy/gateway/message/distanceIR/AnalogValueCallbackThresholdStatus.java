package ch.quantasy.gateway.message.distanceIR;
import ch.quantasy.gateway.message.distanceIR.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AnalogValueCallbackThresholdStatus extends AStatus{
public DeviceAnalogValueCallbackThreshold value;
private AnalogValueCallbackThresholdStatus(){}
public AnalogValueCallbackThresholdStatus(DeviceAnalogValueCallbackThreshold value){
  this.value=value;
}
}
