package ch.quantasy.gateway.message.status.ptc;
import ch.quantasy.gateway.message.intent.ptc.DeviceNoiseReductionFilter;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class NoiseReductionFilterStatus extends AStatus{
public DeviceNoiseReductionFilter value;
private NoiseReductionFilterStatus(){}
public NoiseReductionFilterStatus(DeviceNoiseReductionFilter value){
  this.value=value;
}
}
