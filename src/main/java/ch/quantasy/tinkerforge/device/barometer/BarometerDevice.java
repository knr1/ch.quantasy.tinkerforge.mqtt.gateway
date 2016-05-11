/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.barometer;

import ch.quantasy.tinkerforge.device.humidity.*;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BarometerDevice extends GenericDevice<BrickletBarometer, BarometerDeviceCallback> {

    private Long airPressureCallbackPeriod;
    private Long altitudeCallbackPeriod;
    private Long debouncePeriod;
    private DeviceAirPressureCallbackThreshold airPressureThreshold;
    private DeviceAltitudeCallbackThreshold altitudeThreshold;
    private DeviceAveraging averaging;

    public BarometerDevice(TinkerforgeStackAddress address, BrickletBarometer device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAirPressureListener(super.getCallback());
        getDevice().addAirPressureReachedListener(super.getCallback());
        getDevice().addAltitudeListener(super.getCallback());
        getDevice().addAltitudeReachedListener(super.getCallback());
        if (airPressureCallbackPeriod != null) {
            setAirPressureCallbackPeriod(airPressureCallbackPeriod);
        }
        if (altitudeCallbackPeriod != null) {
            setAltitudeCallbackPeriod(this.altitudeCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (airPressureThreshold != null) {
            setAirPressureThreshold(airPressureThreshold);
        }
        if (altitudeThreshold != null) {
            setAltitudeCallbackThreshold(altitudeThreshold);
        }
        if(averaging!=null){
            setAveraging(averaging);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAltitudeListener(super.getCallback());
        getDevice().removeAltitudeReachedListener(super.getCallback());
        getDevice().removeAirPressureListener(super.getCallback());
        getDevice().removeAirPressureReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAirPressureCallbackPeriod(Long period) {
        try {
            getDevice().setAirPressureCallbackPeriod(period);
            super.getCallback().airPressureCallbackPeriodChanged(getDevice().getAirPressureCallbackPeriod());
            this.airPressureCallbackPeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAltitudeCallbackPeriod(Long period) {
        try {
            getDevice().setAltitudeCallbackPeriod(period);
            super.getCallback().altitudeCallbackPeriodChanged(getDevice().getAltitudeCallbackPeriod());
            this.altitudeCallbackPeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAirPressureThreshold(DeviceAirPressureCallbackThreshold threshold) {
        try {
            getDevice().setAirPressureCallbackThreshold(threshold.option, threshold.min, threshold.max);
            super.getCallback().airPressureCallbackThresholdChanged(getDevice().getAirPressureCallbackThreshold());
            this.airPressureThreshold = threshold;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAltitudeCallbackThreshold(DeviceAltitudeCallbackThreshold threshold) {
        try {
            getDevice().setAltitudeCallbackThreshold(threshold.option, threshold.min, threshold.max);
            super.getCallback().altitudeCallbackThresholdChanged(getDevice().getAltitudeCallbackThreshold());
            this.altitudeThreshold = threshold;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setAveraging(DeviceAveraging averaging){
        try {
            getDevice().setAveraging(averaging.getMovingAveragePressure(),averaging.getAveragingPressure(),averaging.getAveragingTemperature());
            super.getCallback().averagingChanged(getDevice().getAveraging());
            this.averaging = averaging;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
