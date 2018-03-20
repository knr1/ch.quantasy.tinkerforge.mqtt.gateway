package ch.quantasy.gateway.binding.tinkerforge.voltageCurrent;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.DeviceCalibration;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class CalibrationStatus extends AStatus{
public DeviceCalibration value;
private CalibrationStatus(){}
public CalibrationStatus(DeviceCalibration value){
  this.value=value;
}
}
