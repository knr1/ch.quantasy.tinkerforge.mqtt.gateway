package ch.quantasy.gateway.message.nfc;
import ch.quantasy.gateway.message.nfc.NFCWrite;
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class NfcWriteStatus extends AStatus{
public NFCWrite value;
private NfcWriteStatus(){}
public NfcWriteStatus(NFCWrite value){
  this.value=value;
}
}