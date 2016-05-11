/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualRelay;
import ch.quantasy.tinkerforge.device.remoteSwitch.*;
import ch.quantasy.tinkerforge.device.moisture.*;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.BrickletMoisture;

/**
 *
 * @author reto
 */
public class DeviceState {
    
    private boolean relay1;
    private boolean relay2;

    private DeviceState() {
    }

    public DeviceState(boolean relay1, boolean relay2) {
       this.relay1=relay1;
       this.relay2=relay2;
    }
    
    public DeviceState(BrickletDualRelay.State state){
        this(state.relay1,state.relay2);
    }

    public boolean getRelay1() {
        return relay1;
    }

    public boolean getRelay2() {
        return relay2;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.relay1 ? 1 : 0);
        hash = 79 * hash + (this.relay2 ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeviceState other = (DeviceState) obj;
        if (this.relay1 != other.relay1) {
            return false;
        }
        if (this.relay2 != other.relay2) {
            return false;
        }
        return true;
    }

    

    
    
}
