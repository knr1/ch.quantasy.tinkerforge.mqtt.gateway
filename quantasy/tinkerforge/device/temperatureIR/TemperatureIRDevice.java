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
package ch.quantasy.tinkerforge.device.temperatureIR;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TemperatureIRDevice extends GenericDevice<BrickletTemperatureIR, TemperatureIRDeviceCallback> {

    private Long analogCallbackPeriod;
    private Long objectTemperatureCallbackPeriod;
    private Long debouncePeriod;
    private DeviceObjectTemperatureCallbackThreshold objectTemperatureThreshold;
    private DeviceAmbientTemperatureCallbackThreshold ambientTemperatureThreshold;

    public TemperatureIRDevice(TinkerforgeStackAddress address, BrickletTemperatureIR device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAmbientTemperatureListener(super.getCallback());
        getDevice().addAmbientTemperatureReachedListener(super.getCallback());
        getDevice().addObjectTemperatureListener(super.getCallback());
        getDevice().addObjectTemperatureReachedListener(super.getCallback());
        if (analogCallbackPeriod != null) {
            setAmbientTemperatureCallbackPeriod(analogCallbackPeriod);
        }
        if (objectTemperatureCallbackPeriod != null) {
            setObjectTemperatureCallbackPeriod(this.objectTemperatureCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (objectTemperatureThreshold != null) {
            setObjectTemperatureThreshold(objectTemperatureThreshold);
        }
        if (ambientTemperatureThreshold != null) {
            setAmbientTemperatureThreshold(ambientTemperatureThreshold);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAmbientTemperatureListener(super.getCallback());
        getDevice().removeAmbientTemperatureReachedListener(super.getCallback());
        getDevice().removeObjectTemperatureListener(super.getCallback());
        getDevice().removeObjectTemperatureReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAmbientTemperatureCallbackPeriod(Long period) {
        try {
            getDevice().setAmbientTemperatureCallbackPeriod(period);
            this.analogCallbackPeriod = getDevice().getAmbientTemperatureCallbackPeriod();
            super.getCallback().ambientTemperatureCallbackPeriodChanged(this.analogCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setObjectTemperatureCallbackPeriod(Long period) {
        try {
            getDevice().setObjectTemperatureCallbackPeriod(period);
            this.objectTemperatureCallbackPeriod = getDevice().getObjectTemperatureCallbackPeriod();
            super.getCallback().objectTemperatureCallbackPeriodChanged(this.objectTemperatureCallbackPeriod);

        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setObjectTemperatureThreshold(DeviceObjectTemperatureCallbackThreshold threshold) {
        try {
            getDevice().setObjectTemperatureCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.objectTemperatureThreshold = new DeviceObjectTemperatureCallbackThreshold(getDevice().getObjectTemperatureCallbackThreshold());
            super.getCallback().objectTemperatureCallbackThresholdChanged(this.objectTemperatureThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAmbientTemperatureThreshold(DeviceAmbientTemperatureCallbackThreshold threshold) {
        try {
            getDevice().setAmbientTemperatureCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.ambientTemperatureThreshold = new DeviceAmbientTemperatureCallbackThreshold(getDevice().getAmbientTemperatureCallbackThreshold());
            super.getCallback().ambientTemperatureCallbackThresholdChanged(this.ambientTemperatureThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
