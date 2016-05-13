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
public class RemoteSwitchDevice extends GenericDevice<BrickletRemoteSwitch, RemoteSwitchDeviceCallback> implements BrickletRemoteSwitch.SwitchingDoneListener {

    private Short repeats;
    private boolean isSwitching;

    public RemoteSwitchDevice(TinkerforgeStackAddress address, BrickletRemoteSwitch device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addSwitchingDoneListener(super.getCallback());
        getDevice().addSwitchingDoneListener(this);
        if (repeats != null) {
            setRepeats(repeats);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeSwitchingDoneListener(super.getCallback());
        getDevice().removeSwitchingDoneListener(this);

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

    public synchronized void dimSocketB(DimSocketBParameters parameters) {
        while(isSwitching){
            try {
                wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isSwitching=true;
        try {
            getDevice().dimSocketB(parameters.address, parameters.unit, parameters.dimValue);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void switchSocketB(SwitchSocketBParameters parameters) {
        while(isSwitching){
            try {
                wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isSwitching=true;
        try {
            getDevice().switchSocketB(parameters.getAddress(), parameters.getUnit(), parameters.getSwitchingValue().getValue());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void switchSocketA(SwitchSocketAParameters parameters) {
        while(isSwitching){
            try {
                wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isSwitching=true;
        try {
            getDevice().switchSocketA(parameters.getHouseCode(), parameters.getReceiverCode(), parameters.getSwitchingValue().getValue());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void switchSocketC(SwitchSocketCParameters parameters) {
        while(isSwitching){
            try {
                wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isSwitching=true;
        try {
            getDevice().switchSocketC(parameters.getSystemCode(), parameters.getDeviceCode(), parameters.getSwitchingValue().getValue());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void switchingDone() {
        isSwitching = false;
        notifyAll();
    }

}
