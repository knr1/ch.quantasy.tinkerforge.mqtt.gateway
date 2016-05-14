/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.co2;


import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletCO2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CO2Device extends GenericDevice<BrickletCO2, CO2DeviceCallback> {

    private Long debouncePeriod;
    private Long co2ConcentrationCallbackPeriod;
    private DeviceCO2ConcentrationCallbackThreshold threshold;
    
    public CO2Device(TinkerforgeStackAddress address, BrickletCO2 device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addCO2ConcentrationListener(getCallback());
        getDevice().addCO2ConcentrationReachedListener(getCallback());
        if(debouncePeriod!=null){
            setDebouncePeriod(debouncePeriod);
        }
        if(co2ConcentrationCallbackPeriod!=null){
            setCO2ConcentrationCallbackPeriod(co2ConcentrationCallbackPeriod);
        }
        if(threshold!=null){
            setCO2ConcentrationCallbackThreshold(threshold);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeCO2ConcentrationListener(getCallback());
        getDevice().removeCO2ConcentrationReachedListener(getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(CO2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCO2ConcentrationCallbackPeriod(Long period) {
        try {
            getDevice().setCO2ConcentrationCallbackPeriod(period);
            this.co2ConcentrationCallbackPeriod = getDevice().getCO2ConcentrationCallbackPeriod();
            super.getCallback().co2ConcentrationCallbackPeriodChanged(this.co2ConcentrationCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(CO2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCO2ConcentrationCallbackThreshold(DeviceCO2ConcentrationCallbackThreshold threshold) {
        try {
            getDevice().setCO2ConcentrationCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.threshold = new DeviceCO2ConcentrationCallbackThreshold(getDevice().getCO2ConcentrationCallbackThreshold());
            super.getCallback().co2ConcentrationCallbackThresholdChanged(this.threshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(CO2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
