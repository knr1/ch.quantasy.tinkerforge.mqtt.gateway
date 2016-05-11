/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.remoteSwitch;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RemoteSwitchDevice extends GenericDevice<BrickletRemoteSwitch, RemoteSwitchDeviceCallback> {

    private Short repeats;

    public RemoteSwitchDevice(TinkerforgeStackAddress address, BrickletRemoteSwitch device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addSwitchingDoneListener(super.getCallback());
        if (repeats != null) {
            setRepeats(repeats);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeSwitchingDoneListener(super.getCallback());
    }

    public void setRepeats(short repeats) {
        try {
            getDevice().setRepeats(repeats);
            super.getCallback().repeatsChanged(getDevice().getRepeats());
            this.repeats = repeats;
        } catch (TimeoutException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dimSocketB(DimSocketBParameters parameters) {
        try {
            getDevice().dimSocketB(parameters.address, parameters.unit, parameters.dimValue);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchSocketB(SwitchSocketBParameters parameters) {
        try {
            getDevice().switchSocketB(parameters.getAddress(), parameters.getUnit(), parameters.getSwitchingValue().getValue());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchSocketA(SwitchSocketAParameters parameters) {
        try {
            getDevice().switchSocketA(parameters.getHouseCode(), parameters.getReceiverCode(), parameters.getSwitchingValue().getValue());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchSocketC(SwitchSocketCParameters parameters) {
        try {
            getDevice().switchSocketC(parameters.getSystemCode(), parameters.getDeviceCode(), parameters.getSwitchingValue().getValue());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
