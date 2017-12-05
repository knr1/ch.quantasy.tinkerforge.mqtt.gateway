package ch.quantasy.gateway.message.status.segment4x7;
import ch.quantasy.gateway.message.intent.segment4x7.DeviceSegments;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class SegmentsStatus extends AStatus{
public DeviceSegments value;
private SegmentsStatus(){}
public SegmentsStatus(DeviceSegments value){
  this.value=value;
}
}
