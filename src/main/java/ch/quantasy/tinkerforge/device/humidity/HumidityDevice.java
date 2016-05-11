/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.humidity;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletHumidity.AnalogValueCallbackThreshold;
import com.tinkerforge.BrickletHumidity.HumidityCallbackThreshold;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class HumidityDevice extends GenericDevice<BrickletHumidity, HumidityDeviceCallback> {

    private Long analogCallbackPeriod;
    private Long humidityCallbackPeriod;
    private Long debouncePeriod;
    private DeviceAnalogValueThreshold analogValueThreshold;
    private DeviceHumidityThreshold humidityThreshold;

    public HumidityDevice(TinkerforgeStackAddress address, BrickletHumidity device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAnalogValueListener(super.getCallback());
        getDevice().addAnalogValueReachedListener(super.getCallback());
        getDevice().addHumidityListener(super.getCallback());
        getDevice().addHumidityReachedListener(super.getCallback());
        if (analogCallbackPeriod != null) {
            setAnalogValueCallbackPeriod(analogCallbackPeriod);
        }
        if (humidityCallbackPeriod != null) {
            setHumidityCallbackPeriod(this.humidityCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (analogValueThreshold != null) {
            setAnalogValueThreshold(analogValueThreshold);
        }
        if (humidityThreshold != null) {
            setHumidityCallbackThreshold(humidityThreshold);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAnalogValueListener(super.getCallback());
        getDevice().removeAnalogValueReachedListener(super.getCallback());
        getDevice().removeHumidityListener(super.getCallback());
        getDevice().removeHumidityReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HumidityDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAnalogValueCallbackPeriod(Long period) {
        try {
            getDevice().setAnalogValueCallbackPeriod(period);
            super.getCallback().analogValueCallbackPeriodChanged(getDevice().getAnalogValueCallbackPeriod());
            this.analogCallbackPeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HumidityDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setHumidityCallbackPeriod(Long period) {
        try {
            getDevice().setHumidityCallbackPeriod(period);
            super.getCallback().humidityCallbackPeriodChanged(getDevice().getHumidityCallbackPeriod());
            this.humidityCallbackPeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HumidityDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAnalogValueThreshold(DeviceAnalogValueThreshold threshold) {
        try {
            getDevice().setAnalogValueCallbackThreshold(threshold.option, threshold.min, threshold.max);
            super.getCallback().analogValueCallbackThresholdChanged(getDevice().getAnalogValueCallbackThreshold());
            this.analogValueThreshold = threshold;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HumidityDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setHumidityCallbackThreshold(DeviceHumidityThreshold threshold) {
        try {
            getDevice().setHumidityCallbackThreshold(threshold.option, threshold.min, threshold.max);
            super.getCallback().humidityCallbackThresholdChanged(getDevice().getHumidityCallbackThreshold());
            this.humidityThreshold = threshold;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HumidityDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
