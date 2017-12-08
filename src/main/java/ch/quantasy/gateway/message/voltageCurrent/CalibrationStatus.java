package ch.quantasy.gateway.message.voltageCurrent;
import ch.quantasy.gateway.message.voltageCurrent.DeviceCalibration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CalibrationStatus extends AStatus{
public DeviceCalibration value;
private CalibrationStatus(){}
public CalibrationStatus(DeviceCalibration value){
  this.value=value;
}
}
