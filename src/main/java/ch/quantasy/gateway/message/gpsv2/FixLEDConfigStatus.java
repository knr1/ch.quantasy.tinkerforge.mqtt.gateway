package ch.quantasy.gateway.message.gpsv2;
import ch.quantasy.gateway.message.gps.RestartType;
import ch.quantasy.gateway.message.gpsv2.FixLEDConfig;
import ch.quantasy.gateway.message.gpsv2.StatusLEDConfig;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class FixLEDConfigStatus extends AStatus{
    public FixLEDConfig value;
private FixLEDConfigStatus(){}
public FixLEDConfigStatus(FixLEDConfig value){
  this.value=value;
}
}
