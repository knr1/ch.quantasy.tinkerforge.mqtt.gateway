package ch.quantasy.gateway.message.status.color;
import ch.quantasy.gateway.message.intent.color.DeviceColorCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ColorCallbackThresholdStatus extends AStatus{
public DeviceColorCallbackThreshold value;
private ColorCallbackThresholdStatus(){}
public ColorCallbackThresholdStatus(DeviceColorCallbackThreshold value){
  this.value=value;
}
}
