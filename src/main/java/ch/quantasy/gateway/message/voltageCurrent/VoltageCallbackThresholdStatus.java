package ch.quantasy.gateway.message.voltageCurrent;
import ch.quantasy.gateway.message.voltageCurrent.DeviceVoltagCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class VoltageCallbackThresholdStatus extends AStatus{
public DeviceVoltagCallbackThreshold value;
private VoltageCallbackThresholdStatus(){}
public VoltageCallbackThresholdStatus(DeviceVoltagCallbackThreshold value){
  this.value=value;
}
}
