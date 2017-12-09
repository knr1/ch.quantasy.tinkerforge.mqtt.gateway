package ch.quantasy.gateway.message.master;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class UsbVoltageCallbackPeriodStatus extends AStatus{
@Period
public Long value;
private UsbVoltageCallbackPeriodStatus(){}
public UsbVoltageCallbackPeriodStatus(Long value){
  this.value=value;
}
}