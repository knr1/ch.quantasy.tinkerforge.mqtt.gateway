/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dustDetector;


import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDustDetector;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DustDetectorDevice extends GenericDevice<BrickletDustDetector, DustDetectorDeviceCallback> {

    private Long debouncePeriod;
    private Long dustDensityCallbackPeriod;
    private DeviceDustDensityCallbackThreshold threshold;
    
    public DustDetectorDevice(TinkerforgeStackAddress address, BrickletDustDetector device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addDustDensityListener(getCallback());
        getDevice().addDustDensityReachedListener(getCallback());
        if(debouncePeriod!=null){
            setDebouncePeriod(debouncePeriod);
        }
        if(dustDensityCallbackPeriod!=null){
            setDustDensityCallbackPeriod(dustDensityCallbackPeriod);
        }
        if(threshold!=null){
            setDustDensityCallbackThreshold(threshold);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeDustDensityListener(getCallback());
        getDevice().removeDustDensityReachedListener(getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DustDetectorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDustDensityCallbackPeriod(Long period) {
        try {
            getDevice().setDustDensityCallbackPeriod(period);
            this.dustDensityCallbackPeriod = getDevice().getDustDensityCallbackPeriod();
            super.getCallback().dustDensityCallbackPeriodChanged(this.dustDensityCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DustDetectorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDustDensityCallbackThreshold(DeviceDustDensityCallbackThreshold threshold) {
        try {
            getDevice().setDustDensityCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.threshold = new DeviceDustDensityCallbackThreshold(getDevice().getDustDensityCallbackThreshold());
            super.getCallback().dustDensityCallbackThresholdChanged(this.threshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DustDetectorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
