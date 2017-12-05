package ch.quantasy.gateway.message.status.segment4x7;
import ch.quantasy.gateway.message.intent.segment4x7.DeviceCounterParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CounterParameterStatus extends AStatus{
public DeviceCounterParameters value;
private CounterParameterStatus(){}
public CounterParameterStatus(DeviceCounterParameters value){
  this.value=value;
}
}
