package ch.quantasy.gateway.binding.tinkerforge.voltageCurrent;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.DeviceVoltagCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class VoltageCallbackThresholdStatus extends AStatus{
public DeviceVoltagCallbackThreshold value;
private VoltageCallbackThresholdStatus(){}
public VoltageCallbackThresholdStatus(DeviceVoltagCallbackThreshold value){
  this.value=value;
}
}
