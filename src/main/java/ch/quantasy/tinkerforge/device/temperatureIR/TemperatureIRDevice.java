/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.temperatureIR;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TemperatureIRDevice extends GenericDevice<BrickletTemperatureIR, TemperatureIRDeviceCallback> {

    private Long analogCallbackPeriod;
    private Long objectTemperatureCallbackPeriod;
    private Long debouncePeriod;
    private DeviceObjectTemperatureCallbackThreshold objectTemperatureThreshold;
    private DeviceAmbientTemperatureCallbackThreshold ambientTemperatureThreshold;

    public TemperatureIRDevice(TinkerforgeStackAddress address, BrickletTemperatureIR device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAmbientTemperatureListener(super.getCallback());
        getDevice().addAmbientTemperatureReachedListener(super.getCallback());
        getDevice().addObjectTemperatureListener(super.getCallback());
        getDevice().addObjectTemperatureReachedListener(super.getCallback());
        if (analogCallbackPeriod != null) {
            setAmbientTemperatureCallbackPeriod(analogCallbackPeriod);
        }
        if (objectTemperatureCallbackPeriod != null) {
            setObjectTemperatureCallbackPeriod(this.objectTemperatureCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (objectTemperatureThreshold != null) {
            setObjectTemperatureThreshold(objectTemperatureThreshold);
        }
        if (ambientTemperatureThreshold != null) {
            setAmbientTemperatureThreshold(ambientTemperatureThreshold);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAmbientTemperatureListener(super.getCallback());
        getDevice().removeAmbientTemperatureReachedListener(super.getCallback());
        getDevice().removeObjectTemperatureListener(super.getCallback());
        getDevice().removeObjectTemperatureReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAmbientTemperatureCallbackPeriod(Long period) {
        try {
            getDevice().setAmbientTemperatureCallbackPeriod(period);
            this.analogCallbackPeriod = getDevice().getAmbientTemperatureCallbackPeriod();
            super.getCallback().ambientTemperatureCallbackPeriodChanged(this.analogCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setObjectTemperatureCallbackPeriod(Long period) {
        try {
            getDevice().setObjectTemperatureCallbackPeriod(period);
            this.objectTemperatureCallbackPeriod = getDevice().getObjectTemperatureCallbackPeriod();
            super.getCallback().objectTemperatureCallbackPeriodChanged(this.objectTemperatureCallbackPeriod);

        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setObjectTemperatureThreshold(DeviceObjectTemperatureCallbackThreshold threshold) {
        try {
            getDevice().setObjectTemperatureCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.objectTemperatureThreshold = new DeviceObjectTemperatureCallbackThreshold(getDevice().getObjectTemperatureCallbackThreshold());
            super.getCallback().objectTemperatureCallbackThresholdChanged(this.objectTemperatureThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAmbientTemperatureThreshold(DeviceAmbientTemperatureCallbackThreshold threshold) {
        try {
            getDevice().setAmbientTemperatureCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.ambientTemperatureThreshold = new DeviceAmbientTemperatureCallbackThreshold(getDevice().getAmbientTemperatureCallbackThreshold());
            super.getCallback().ambientTemperatureCallbackThresholdChanged(this.ambientTemperatureThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
