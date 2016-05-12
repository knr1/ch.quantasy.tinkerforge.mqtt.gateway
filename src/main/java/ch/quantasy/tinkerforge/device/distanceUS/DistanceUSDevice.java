/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.distanceUS;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DistanceUSDevice extends GenericDevice<BrickletDistanceUS, DistanceUSDeviceCallback> {

    private Long callbackPeriod;
    private Long debouncePeriod;
    private Short movingAverage;
    private DeviceDistanceCallbackThreshold distanceThreshold;

    public DistanceUSDevice(TinkerforgeStackAddress address, BrickletDistanceUS device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addDistanceListener(super.getCallback());
        getDevice().addDistanceReachedListener(super.getCallback());

        if (this.movingAverage != null) {
            setMovingAverage(movingAverage);
        }
        if (this.debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (this.callbackPeriod != null) {
            setDistanceCallbackPeriod(callbackPeriod);
        }
        if (this.distanceThreshold != null) {
            setDistanceCallbackThreshold(distanceThreshold);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeDistanceListener(super.getCallback());
        getDevice().removeDistanceReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceUSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDistanceCallbackPeriod(Long period) {
        try {
            getDevice().setDistanceCallbackPeriod(period);
            this.callbackPeriod = getDevice().getDistanceCallbackPeriod();
            super.getCallback().distanceCallbackPeriodChanged(this.callbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceUSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDistanceCallbackThreshold(DeviceDistanceCallbackThreshold threshold) {
        try {
            getDevice().setDistanceCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.distanceThreshold = new DeviceDistanceCallbackThreshold(getDevice().getDistanceCallbackThreshold());
            super.getCallback().distanceCallbackThresholdChanged(this.distanceThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceUSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMovingAverage(Short movingAverage) {
        try {
            getDevice().setMovingAverage(movingAverage);
            this.movingAverage = getDevice().getMovingAverage();
            super.getCallback().movingAverageChanged(movingAverage);

        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceUSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
