package ch.quantasy.gateway.message.status.distanceIR;
import ch.quantasy.gateway.message.intent.distanceIR.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class AnalogValueCallbackThresholdStatus extends AStatus{
public DeviceAnalogValueCallbackThreshold value;
private AnalogValueCallbackThresholdStatus(){}
public AnalogValueCallbackThresholdStatus(DeviceAnalogValueCallbackThreshold value){
  this.value=value;
}
}
