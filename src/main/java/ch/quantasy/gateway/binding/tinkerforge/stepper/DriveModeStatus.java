package ch.quantasy.gateway.binding.tinkerforge.stepper;
import java.lang.Integer;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
public class DriveModeStatus extends AStatus{
public DriveMode value;
private DriveModeStatus(){}
public DriveModeStatus(DriveMode value){
  this.value=value;
}
}
