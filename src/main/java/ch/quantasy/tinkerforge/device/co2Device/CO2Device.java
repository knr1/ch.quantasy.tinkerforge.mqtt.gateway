/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.co2Device;


import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletAmbientLight;
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
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(CO2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCO2ConcentrationCallbackPeriod(Long period) {
        try {
            getDevice().setCO2ConcentrationCallbackPeriod(period);
            super.getCallback().co2ConcentrationCallbackPeriodChanged(getDevice().getCO2ConcentrationCallbackPeriod());
            this.co2ConcentrationCallbackPeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(CO2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCO2ConcentrationCallbackThreshold(DeviceCO2ConcentrationCallbackThreshold threshold) {
        try {
            getDevice().setCO2ConcentrationCallbackThreshold(threshold.option, threshold.min, threshold.max);
            super.getCallback().co2ConcentrationCallbackThresholdChanged(getDevice().getCO2ConcentrationCallbackThreshold());
            this.threshold = threshold;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(CO2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
