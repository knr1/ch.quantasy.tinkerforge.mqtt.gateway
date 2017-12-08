package ch.quantasy.gateway.message.loadCell;
import ch.quantasy.gateway.message.loadCell.DeviceWeightCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class WeightCallbackThresholdStatus extends AStatus{
public DeviceWeightCallbackThreshold value;
private WeightCallbackThresholdStatus(){}
public WeightCallbackThresholdStatus(DeviceWeightCallbackThreshold value){
  this.value=value;
}
}
