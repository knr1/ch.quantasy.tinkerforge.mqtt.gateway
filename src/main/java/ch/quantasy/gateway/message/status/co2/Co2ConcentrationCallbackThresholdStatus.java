package ch.quantasy.gateway.message.status.co2;
import ch.quantasy.gateway.message.intent.co2.DeviceCO2ConcentrationCallbackThreshold;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class Co2ConcentrationCallbackThresholdStatus extends AStatus{
public DeviceCO2ConcentrationCallbackThreshold value;
private Co2ConcentrationCallbackThresholdStatus(){}
public Co2ConcentrationCallbackThresholdStatus(DeviceCO2ConcentrationCallbackThreshold value){
  this.value=value;
}
}
