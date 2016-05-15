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

    private Integer referenceAirPressure;
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
        if (averaging != null) {
            setAveraging(averaging);
        }
        if(referenceAirPressure!=null){
            setReferenceAirPressure(referenceAirPressure);
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
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAirPressureCallbackPeriod(Long period) {
        try {
            getDevice().setAirPressureCallbackPeriod(period);
            this.airPressureCallbackPeriod = getDevice().getAirPressureCallbackPeriod();
            super.getCallback().airPressureCallbackPeriodChanged(this.airPressureCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setReferenceAirPressure(Integer reference) {
        try {
            getDevice().setReferenceAirPressure(reference);
            this.referenceAirPressure = getDevice().getReferenceAirPressure();
            super.getCallback().referenceAirPressureChanged(this.referenceAirPressure);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAltitudeCallbackPeriod(Long period) {
        try {
            getDevice().setAltitudeCallbackPeriod(period);
            this.altitudeCallbackPeriod = getDevice().getAltitudeCallbackPeriod();
            super.getCallback().altitudeCallbackPeriodChanged(this.altitudeCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAirPressureThreshold(DeviceAirPressureCallbackThreshold threshold) {
        try {
            getDevice().setAirPressureCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.airPressureThreshold = new DeviceAirPressureCallbackThreshold(getDevice().getAirPressureCallbackThreshold());
            super.getCallback().airPressureCallbackThresholdChanged(this.airPressureThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAltitudeCallbackThreshold(DeviceAltitudeCallbackThreshold threshold) {
        try {
            getDevice().setAltitudeCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.altitudeThreshold = new DeviceAltitudeCallbackThreshold(getDevice().getAltitudeCallbackThreshold());
            super.getCallback().altitudeCallbackThresholdChanged(this.altitudeThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAveraging(DeviceAveraging averaging) {
        try {
            getDevice().setAveraging(averaging.getMovingAveragePressure(), averaging.getAveragingPressure(), averaging.getAveragingTemperature());
            this.averaging = new DeviceAveraging(getDevice().getAveraging());
            super.getCallback().averagingChanged(this.averaging);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
