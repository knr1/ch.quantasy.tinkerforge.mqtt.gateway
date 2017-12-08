package ch.quantasy.gateway.message.master;
import ch.quantasy.gateway.message.master.USBVoltageCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class UsbVoltageCallbackThresholdStatus extends AStatus{
public USBVoltageCallbackThreshold value;
private UsbVoltageCallbackThresholdStatus(){}
public UsbVoltageCallbackThresholdStatus(USBVoltageCallbackThreshold value){
  this.value=value;
}
}
