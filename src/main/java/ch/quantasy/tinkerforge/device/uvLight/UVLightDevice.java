/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.uvLight;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletUVLight;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class UVLightDevice extends GenericDevice<BrickletUVLight, UVLightDeviceCallback> {

    private Long callbackPeriod;
    private Long debouncePeriod;
    private DeviceUVLightCallbackThreshold uvLightThreshold;

    public UVLightDevice(TinkerforgeStackAddress address, BrickletUVLight device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addUVLightListener(super.getCallback());
        getDevice().addUVLightReachedListener(super.getCallback());

        if (callbackPeriod != null) {
            setUVLightCallbackPeriod(this.callbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (uvLightThreshold != null) {
            setUVLightCallbackThreshold(uvLightThreshold);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeUVLightListener(super.getCallback());
        getDevice().removeUVLightReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(UVLightDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUVLightCallbackPeriod(Long period) {
        try {
            getDevice().setUVLightCallbackPeriod(period);
            this.callbackPeriod = getDevice().getUVLightCallbackPeriod();
            super.getCallback().uvLightCallbackPeriodChanged(this.callbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(UVLightDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUVLightCallbackThreshold(DeviceUVLightCallbackThreshold threshold) {
        try {
            getDevice().setUVLightCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.uvLightThreshold = new DeviceUVLightCallbackThreshold(getDevice().getUVLightCallbackThreshold());
            super.getCallback().uvLightCallbackThresholdChanged(this.uvLightThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(UVLightDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
