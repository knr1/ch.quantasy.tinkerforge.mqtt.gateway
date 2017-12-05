package ch.quantasy.gateway.message.status.remoteSwitch;
import ch.quantasy.gateway.message.intent.remoteSwitch.DimSocketBParameters;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class DimSocketBParametersStatus extends AStatus{
public DimSocketBParameters value;
private DimSocketBParametersStatus(){}
public DimSocketBParametersStatus(DimSocketBParameters value){
  this.value=value;
}
}
