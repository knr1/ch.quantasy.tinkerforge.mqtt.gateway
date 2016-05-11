/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualRelay;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualRelayDevice extends GenericDevice<BrickletDualRelay, DualRelayDeviceCallback> {

    private Map<Short, DeviceMonoflopParameters> monoflopParametersMap;
    private DeviceState state;

    public DualRelayDevice(TinkerforgeStackAddress address, BrickletDualRelay device) throws NotConnectedException, TimeoutException {
        super(address, device);
        this.monoflopParametersMap = new HashMap<>();
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addMonoflopDoneListener(super.getCallback());
        for (DeviceMonoflopParameters parameters : monoflopParametersMap.values()) {
            setMonoflop(parameters);
        }
        if (state != null) {
            setState(state);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeMonoflopDoneListener(super.getCallback());
    }

    public void setMonoflop(DeviceMonoflopParameters parameters) {
        try {
            getDevice().setMonoflop(parameters.getRelay(), parameters.getState(), parameters.getPeriod());
            this.monoflopParametersMap.put(parameters.getRelay(), parameters);
            super.getCallback().stateChanged(new DeviceState(getDevice().getState()));
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DualRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setSelectedState(DeviceSelectedState parameters) {
        try {
            getDevice().setSelectedState(parameters.getRelay(), parameters.getState());
            this.state=new DeviceState(getDevice().getState());
            super.getCallback().stateChanged(state);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DualRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setState(DeviceState state) {
        try {
            getDevice().setState(state.getRelay1(),state.getRelay2());
            this.state=new DeviceState(getDevice().getState());
            super.getCallback().stateChanged(state);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DualRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
