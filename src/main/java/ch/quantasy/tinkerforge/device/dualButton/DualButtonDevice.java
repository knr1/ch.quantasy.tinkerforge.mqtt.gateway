/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualButton;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDualButton;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualButtonDevice extends GenericDevice<BrickletDualButton, DualButtonDeviceCallback> {

    private DeviceLEDState LEDState;

    public DualButtonDevice(TinkerforgeStackAddress address, BrickletDualButton device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addStateChangedListener(super.getCallback());
        if (LEDState != null) {
            setLEDState(LEDState);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeStateChangedListener(super.getCallback());
    }

    public void setLEDState(DeviceLEDState ledState) {
        try {
            getDevice().setLEDState(ledState.leftLED, ledState.rightLED);
            this.LEDState = new DeviceLEDState(getDevice().getLEDState());
            super.getCallback().ledStateChanged(this.LEDState);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DualButtonDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setSelectedLEDState(DeviceSelectedLEDStateParameters parameters) {
        try {
            getDevice().setSelectedLEDState(parameters.getLed(), parameters.getState());
            this.LEDState = new DeviceLEDState(getDevice().getLEDState());
            super.getCallback().ledStateChanged(this.LEDState);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DualButtonDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
