/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.ambientLightV2;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AmbientLightV2Device extends GenericDevice<BrickletAmbientLightV2, AmbientLightV2DeviceCallback> {

    private DeviceConfiguration configuration;
    private Long callbackPeriod;
    private Long debouncePeriod;
    private DeviceIlluminanceCallbackThreshold illuminanceThreshold;

    public AmbientLightV2Device(TinkerforgeStackAddress address, BrickletAmbientLightV2 device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addIlluminanceListener(super.getCallback());
        getDevice().addIlluminanceReachedListener(super.getCallback());

        if (configuration != null) {
            setConfiguration(configuration);
        }
        if (callbackPeriod != null) {
            setIlluminanceCallbackPeriod(this.callbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (illuminanceThreshold != null) {
            setIlluminanceCallbackThreshold(illuminanceThreshold);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeIlluminanceListener(super.getCallback());
        getDevice().removeIlluminanceReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIlluminanceCallbackPeriod(Long period) {
        try {
            getDevice().setIlluminanceCallbackPeriod(period);
            this.callbackPeriod = getDevice().getIlluminanceCallbackPeriod();
            super.getCallback().illuminanceCallbackPeriodChanged(this.callbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIlluminanceCallbackThreshold(DeviceIlluminanceCallbackThreshold threshold) {
        try {
            getDevice().setIlluminanceCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.illuminanceThreshold = new DeviceIlluminanceCallbackThreshold(getDevice().getIlluminanceCallbackThreshold());
            super.getCallback().illuminanceCallbackThresholdChanged(this.illuminanceThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setConfiguration(DeviceConfiguration configuration) {
        try {
            getDevice().setConfiguration(configuration.getIlluminanceRange(), configuration.getIntegrationTime());
            this.configuration = new DeviceConfiguration(getDevice().getConfiguration());
            super.getCallback().configurationChanged(this.configuration);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
