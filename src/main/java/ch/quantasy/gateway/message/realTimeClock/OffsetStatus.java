package ch.quantasy.gateway.message.realTimeClock;
import java.lang.Byte;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class OffsetStatus extends AStatus{
@Range(from=-128, to=127)
public Byte value;
private OffsetStatus(){}
public OffsetStatus(Byte value){
  this.value=value;
}
}
