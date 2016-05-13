/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.distanceIR;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DistanceIRDevice extends GenericDevice<BrickletDistanceIR, DistanceIRDeviceCallback> {

    private Long analogCallbackPeriod;
    private Long distanceCallbackPeriod;
    private Long debouncePeriod;
    private DeviceAnalogValueCallbackThreshold analogValueThreshold;
    private DeviceDistanceCallbackThreshold distanceThreshold;

    public DistanceIRDevice(TinkerforgeStackAddress address, BrickletDistanceIR device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAnalogValueListener(super.getCallback());
        getDevice().addAnalogValueReachedListener(super.getCallback());
        getDevice().addDistanceListener(super.getCallback());
        getDevice().addDistanceReachedListener(super.getCallback());
        if (analogCallbackPeriod != null) {
            setAnalogValueCallbackPeriod(analogCallbackPeriod);
        }
        if (distanceCallbackPeriod != null) {
            setDistanceCallbackPeriod(this.distanceCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (analogValueThreshold != null) {
            setAnalogValueThreshold(analogValueThreshold);
        }
        if (distanceThreshold != null) {
            setDistanceCallbackThreshold(distanceThreshold);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAnalogValueListener(super.getCallback());
        getDevice().removeAnalogValueReachedListener(super.getCallback());
        getDevice().removeDistanceListener(super.getCallback());
        getDevice().removeDistanceReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAnalogValueCallbackPeriod(Long period) {
        try {
            getDevice().setAnalogValueCallbackPeriod(period);
            this.analogCallbackPeriod = getDevice().getAnalogValueCallbackPeriod();
            super.getCallback().analogValueCallbackPeriodChanged(this.analogCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDistanceCallbackPeriod(Long period) {
        try {
            getDevice().setDistanceCallbackPeriod(period);
            this.distanceCallbackPeriod=getDevice().getDistanceCallbackPeriod();
            super.getCallback().distanceCallbackPeriodChanged(this.distanceCallbackPeriod );
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAnalogValueThreshold(DeviceAnalogValueCallbackThreshold threshold) {
        try {
            getDevice().setAnalogValueCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.analogValueThreshold = new DeviceAnalogValueCallbackThreshold(getDevice().getAnalogValueCallbackThreshold());
            super.getCallback().analogValueCallbackThresholdChanged(this.analogValueThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDistanceCallbackThreshold(DeviceDistanceCallbackThreshold threshold) {
        try {
            getDevice().setDistanceCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.distanceThreshold = new DeviceDistanceCallbackThreshold(getDevice().getDistanceCallbackThreshold());
            super.getCallback().distanceCallbackThresholdChanged(this.distanceThreshold);
            
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
