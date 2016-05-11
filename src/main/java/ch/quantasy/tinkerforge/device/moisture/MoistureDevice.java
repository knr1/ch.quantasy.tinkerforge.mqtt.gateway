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
    private DeviceMoistureThreshold moistureThreshold;

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
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMoistureCallbackPeriod(Long period) {
        try {
            getDevice().setMoistureCallbackPeriod(period);
            super.getCallback().moistureCallbackPeriodChanged(getDevice().getMoistureCallbackPeriod());
            this.callbackPeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void setMoistureCallbackThreshold(DeviceMoistureThreshold threshold) {
        try {
            getDevice().setMoistureCallbackThreshold(threshold.option, threshold.min, threshold.max);
            super.getCallback().moistureCallbackThresholdChanged(getDevice().getMoistureCallbackThreshold());
            this.moistureThreshold = threshold;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMovingAverage(Short average) {
        try {
            getDevice().setMovingAverage(average);
            super.getCallback().movingAverageChanged(getDevice().getMovingAverage());
            this.average = average;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
