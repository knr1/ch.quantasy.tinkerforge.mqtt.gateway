/*
 * /*
 *  *   "TiMqWay"
 *  *
 *  *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *  *
 *  *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
 *  *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *    Quellgasse 21, CH-2501 Biel, Switzerland
 *  *
 *  *    Licensed under Dual License consisting of:
 *  *    1. GNU Affero General Public License (AGPL) v3
 *  *    and
 *  *    2. Commercial license
 *  *
 *  *
 *  *    1. This program is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU Affero General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     This program is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU Affero General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU Affero General Public License
 *  *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 *  *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *  *     accordance with the commercial license agreement provided with the
 *  *     Software or, alternatively, in accordance with the terms contained in
 *  *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *  *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *  *
 *  *
 *  *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *  *
 *  *
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
