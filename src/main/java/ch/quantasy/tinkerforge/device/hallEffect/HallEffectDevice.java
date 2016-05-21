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
package ch.quantasy.tinkerforge.device.hallEffect;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletHallEffect;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class HallEffectDevice extends GenericDevice<BrickletHallEffect, HallEffectDeviceCallback> {

    private Long edgeCountCallbackPeriod;
    private DeviceConfiguration configuration;
    private Long edges;

    public HallEffectDevice(TinkerforgeStackAddress address, BrickletHallEffect device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addEdgeCountListener(super.getCallback());
        
        if (edgeCountCallbackPeriod != null) {
            setEdgeCountCallbackPeriod(edgeCountCallbackPeriod);
        }
        if (configuration != null) {
            setEdgeCountConfig(this.configuration);
        }
        if (edges != null) {
            setEdgeInterrupt(edges);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeEdgeCountListener(super.getCallback());
    }

    public void setEdgeInterrupt(Long edges) {
        try {
            getDevice().setEdgeInterrupt(edges);
            this.edges = getDevice().getEdgeInterrupt();
            super.getCallback().edgeInterruptChanged(this.edges);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HallEffectDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEdgeCountCallbackPeriod(Long period) {
        try {
            getDevice().setEdgeCountCallbackPeriod(period);
            this.edgeCountCallbackPeriod = getDevice().getEdgeCountCallbackPeriod();
            super.getCallback().edgeCountCallbackPeriodChanged(this.edgeCountCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HallEffectDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEdgeCountConfig(DeviceConfiguration configuration) {
        try {
            getDevice().setEdgeCountConfig(configuration.getEdgeType().getValue(),configuration.getDebounce());
            this.configuration=new DeviceConfiguration(getDevice().getEdgeCountConfig());
            super.getCallback().edgeCountConfigChanged(this.configuration);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HallEffectDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setEdgeCountReset(boolean reset){
        try{
            long edgeCount=getDevice().getEdgeCount(reset);
            super.getCallback().edgeCountReset(edgeCount);
        }catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(HallEffectDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
