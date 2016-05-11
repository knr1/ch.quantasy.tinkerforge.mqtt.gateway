/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.generic;

import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class GenericDevice<D extends Device, C extends DeviceCallback> extends TinkerforgeDevice<D> {

    private C callback;
    private boolean areListenerAdded;

    public GenericDevice(ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress address, D device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    protected abstract void addDeviceListeners();

    protected abstract void removeDeviceListeners();

    @Override
    public void setDevice(D device) throws TimeoutException, NotConnectedException {
        if (device == null || device.getIdentity().uid != super.getUid()) {
            return;
        }
        removeDeviceListeners();
        areListenerAdded = false;
        super.setDevice(device);
        if (areListenerAdded == false) {
            addDeviceListeners();
            areListenerAdded = true;
        }
    }

    public void connected() {
        super.connected();
        if (super.getDevice() != null && !areListenerAdded) {
            addDeviceListeners();
            areListenerAdded=true;
        }
    }

    public void disconnected() {
        super.disconnected();
        if (super.getDevice() != null && areListenerAdded) {
            removeDeviceListeners();
            areListenerAdded=false;
        }
    }

    public void reconnected() {
        super.reconnected();
        if (super.getDevice() != null && !areListenerAdded) {
            addDeviceListeners();
            areListenerAdded=true;
        }
    }

    public void setCallback(C callback) {
        if (super.getDevice() != null && this.callback != null && areListenerAdded) {
            removeDeviceListeners();
            areListenerAdded=false;
        }
        this.callback = callback;
        if (super.getDevice() != null && this.callback != null && !areListenerAdded) {
            addDeviceListeners();
            areListenerAdded=true;
        }
    }

    public C getCallback() {
        return callback;
    }
}
