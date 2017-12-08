package ch.quantasy.gateway.message.nfc;
import java.lang.String;
import ch.quantasy.mqtt.gateway.client.message.annotations.StringForm;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class TagIDStatus extends AStatus{
@StringForm(regEx="[0-9A-F]{8-14}")
public String value;
private TagIDStatus(){}
public TagIDStatus(String value){
  this.value=value;
}
}
