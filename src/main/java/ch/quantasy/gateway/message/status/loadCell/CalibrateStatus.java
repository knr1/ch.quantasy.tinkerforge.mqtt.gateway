package ch.quantasy.gateway.message.status.loadCell;
import java.lang.Long;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CalibrateStatus extends AStatus{
public Long value;
private CalibrateStatus(){}
public CalibrateStatus(Long value){
  this.value=value;
}
}
