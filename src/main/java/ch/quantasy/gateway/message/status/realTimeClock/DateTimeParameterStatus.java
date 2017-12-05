package ch.quantasy.gateway.message.status.realTimeClock;
import ch.quantasy.gateway.message.intent.realTimeClock.DateTimeParameter;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class DateTimeParameterStatus extends AStatus{
public DateTimeParameter value;
private DateTimeParameterStatus(){}
public DateTimeParameterStatus(DateTimeParameter value){
  this.value=value;
}
}
