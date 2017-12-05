package ch.quantasy.gateway.message.status.dualRelay;

import java.util.Set;
import java.util.HashSet;
import ch.quantasy.gateway.message.intent.dualRelay.DeviceMonoflopParameters;
import ch.quantasy.mqtt.gateway.client.message.annotations.SetSize;

import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class MonoflopParametersStatus extends AStatus{

    @SetSize(max = 2, min = 0)
    public Set<DeviceMonoflopParameters
    > value ;

    private MonoflopParametersStatus() {
        value=new HashSet();
    }

    public MonoflopParametersStatus(Set<DeviceMonoflopParameters 
        > value){
  this.value = value;
    }
}
