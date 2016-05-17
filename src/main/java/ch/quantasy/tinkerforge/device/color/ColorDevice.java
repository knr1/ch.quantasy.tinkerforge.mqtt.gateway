/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.tinkerforge.device.color;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletColor;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorDevice extends GenericDevice<BrickletColor, ColorDeviceCallback> {

    private Long colorCallbackPeriod;
    private Long illuminanceCallbackPeriod;
    private Long debouncePeriod;
    private DeviceColorCallbackThreshold colorCallbackThreshold;
    private Long colorTemperaturePeriod;
    private DeviceConfiguration config;
    private Boolean isLight;

    public ColorDevice(TinkerforgeStackAddress address, BrickletColor device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addColorListener(super.getCallback());
        getDevice().addColorReachedListener(super.getCallback());
        getDevice().addColorTemperatureListener(super.getCallback());
        getDevice().addIlluminanceListener(super.getCallback());
        if (colorCallbackPeriod != null) {
            setColorCallbackPeriod(colorCallbackPeriod);
        }
        if (illuminanceCallbackPeriod != null) {
            setIlluminanceCallbackPeriod(this.illuminanceCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (colorCallbackThreshold != null) {
            setColorCallbackThreshold(colorCallbackThreshold);
        }
        if (colorTemperaturePeriod != null) {
            setColorTemperatureCallbackPeriod(colorTemperaturePeriod);
        }
        if(config!=null){
            setConfig(config);
        }
        if(isLight!=null){
            setLight(isLight);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeColorListener(super.getCallback());
        getDevice().removeColorReachedListener(super.getCallback());
        getDevice().addColorTemperatureListener(super.getCallback());
        getDevice().addIlluminanceListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setColorCallbackPeriod(Long period) {
        try {
            getDevice().setColorCallbackPeriod(period);
            this.colorCallbackPeriod = getDevice().getColorCallbackPeriod();
            super.getCallback().colorCallbackPeriodChanged(this.colorCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIlluminanceCallbackPeriod(Long period) {
        try {
            getDevice().setIlluminanceCallbackPeriod(period);
            this.illuminanceCallbackPeriod=getDevice().getIlluminanceCallbackPeriod();
            super.getCallback().illuminanceCallbackPeriodChanged(this.illuminanceCallbackPeriod );
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setColorCallbackThreshold(DeviceColorCallbackThreshold threshold) {
        try {
            getDevice().setColorCallbackThreshold(threshold.getOption(), threshold.getMinR(),threshold.getMaxR(),threshold.getMinG(),threshold.getMaxG(),threshold.getMinB(),threshold.getMaxB(),threshold.getMinC(),threshold.getMaxC());
            this.colorCallbackThreshold = new DeviceColorCallbackThreshold(getDevice().getColorCallbackThreshold());
            super.getCallback().colorCallbackThresholdChanged(this.colorCallbackThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setColorTemperatureCallbackPeriod(Long period) {
        try {
            getDevice().setColorTemperatureCallbackPeriod(period);
            this.colorTemperaturePeriod = getDevice().getColorTemperatureCallbackPeriod();
            super.getCallback().colorTemperatureCallbackPeriodChanged(this.colorTemperaturePeriod);
            
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setConfig(DeviceConfiguration config){
        try{
            getDevice().setConfig(config.getGain().getValue(),config.getIntegrationTime().getValue());
            this.config=new DeviceConfiguration(getDevice().getConfig());
            super.getCallback().configurationChanged(this.config);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void setLight(Boolean isLightOn){
        try{
            if(isLightOn){
                getDevice().lightOn();
            }else{
                getDevice().lightOff();
            }
            this.isLight=getDevice().isLightOn() != 0;
            super.getCallback().lightStatusChanged(this.isLight);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    

}
