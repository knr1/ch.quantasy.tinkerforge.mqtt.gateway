package ch.quantasy.gateway.message.status.nfc;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class ScanningIntervalStatus extends AStatus{
@Period
public Long value;
private ScanningIntervalStatus(){}
public ScanningIntervalStatus(Long value){
  this.value=value;
}
}
