/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.ambientLight;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AmbientLightDevice extends GenericDevice<BrickletAmbientLight, AmbientLightDeviceCallback> {

    private Long analogCallbackPeriod;
    private Long illuminanceCallbackPeriod;
    private Long debouncePeriod;
    private DeviceAnalogValueCallbackThreshold analogValueThreshold;
    private DeviceIlluminanceCallbackThreshold illuminanceThreshold;

    public AmbientLightDevice(TinkerforgeStackAddress address, BrickletAmbientLight device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAnalogValueListener(super.getCallback());
        getDevice().addAnalogValueReachedListener(super.getCallback());
        getDevice().addIlluminanceListener(super.getCallback());
        getDevice().addIlluminanceReachedListener(super.getCallback());
        if (analogCallbackPeriod != null) {
            setAnalogValueCallbackPeriod(analogCallbackPeriod);
        }
        if (illuminanceCallbackPeriod != null) {
            setIlluminanceCallbackPeriod(this.illuminanceCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (analogValueThreshold != null) {
            setAnalogValueThreshold(analogValueThreshold);
        }
        if (illuminanceThreshold != null) {
            setIlluminanceCallbackThreshold(illuminanceThreshold);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAnalogValueListener(super.getCallback());
        getDevice().removeAnalogValueReachedListener(super.getCallback());
        getDevice().removeIlluminanceListener(super.getCallback());
        getDevice().removeIlluminanceReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAnalogValueCallbackPeriod(Long period) {
        try {
            getDevice().setAnalogValueCallbackPeriod(period);
            this.analogCallbackPeriod = getDevice().getAnalogValueCallbackPeriod();
            super.getCallback().analogValueCallbackPeriodChanged(this.analogCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIlluminanceCallbackPeriod(Long period) {
        try {
            getDevice().setIlluminanceCallbackPeriod(period);
            this.illuminanceCallbackPeriod=getDevice().getIlluminanceCallbackPeriod();
            super.getCallback().illuminanceCallbackPeriodChanged(this.illuminanceCallbackPeriod );
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAnalogValueThreshold(DeviceAnalogValueCallbackThreshold threshold) {
        try {
            getDevice().setAnalogValueCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.analogValueThreshold = new DeviceAnalogValueCallbackThreshold(getDevice().getAnalogValueCallbackThreshold());
            super.getCallback().analogValueCallbackThresholdChanged(this.analogValueThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIlluminanceCallbackThreshold(DeviceIlluminanceCallbackThreshold threshold) {
        try {
            getDevice().setIlluminanceCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.illuminanceThreshold = new DeviceIlluminanceCallbackThreshold(getDevice().getIlluminanceCallbackThreshold());
            super.getCallback().illuminanceCallbackThresholdChanged(this.illuminanceThreshold);
            
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
