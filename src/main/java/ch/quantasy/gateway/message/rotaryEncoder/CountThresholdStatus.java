package ch.quantasy.gateway.message.rotaryEncoder;
import ch.quantasy.gateway.message.rotaryEncoder.DeviceCountCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CountThresholdStatus extends AStatus{
public DeviceCountCallbackThreshold value;
private CountThresholdStatus(){}
public CountThresholdStatus(DeviceCountCallbackThreshold value){
  this.value=value;
}
}
