/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.moisture;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MoistureDevice extends GenericDevice<BrickletMoisture, MoistureDeviceCallback> {

    private Short average;
    private Long callbackPeriod;
    private Long debouncePeriod;
    private DeviceMoistureCallbackThreshold moistureThreshold;

    public MoistureDevice(TinkerforgeStackAddress address, BrickletMoisture device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addMoistureListener(super.getCallback());
        getDevice().addMoistureReachedListener(super.getCallback());

        if (average != null) {
            setMovingAverage(average);
        }
        if (callbackPeriod != null) {
            setMoistureCallbackPeriod(this.callbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (moistureThreshold != null) {
            setMoistureCallbackThreshold(moistureThreshold);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeMoistureListener(super.getCallback());
        getDevice().removeMoistureReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMoistureCallbackPeriod(Long period) {
        try {
            getDevice().setMoistureCallbackPeriod(period);
            this.callbackPeriod = getDevice().getMoistureCallbackPeriod();
            super.getCallback().moistureCallbackPeriodChanged(this.callbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMoistureCallbackThreshold(DeviceMoistureCallbackThreshold threshold) {
        try {
            getDevice().setMoistureCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.moistureThreshold = new DeviceMoistureCallbackThreshold(getDevice().getMoistureCallbackThreshold());
            super.getCallback().moistureCallbackThresholdChanged(this.moistureThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMovingAverage(Short average) {
        try {
            getDevice().setMovingAverage(average);
            this.average = getDevice().getMovingAverage();
            super.getCallback().movingAverageChanged(this.average);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
