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
package ch.quantasy.tinkerforge.device.moisture;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MoistureDevice extends GenericDevice<BrickletMoisture, MoistureDeviceCallback> {

    private Short average;
    private Long callbackPeriod;
    private Long debouncePeriod;
    private DeviceMoistureCallbackThreshold moistureThreshold;

    public MoistureDevice(TinkerforgeStackAddress address, BrickletMoisture device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addMoistureListener(super.getCallback());
        getDevice().addMoistureReachedListener(super.getCallback());

        if (average != null) {
            setMovingAverage(average);
        }
        if (callbackPeriod != null) {
            setMoistureCallbackPeriod(this.callbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (moistureThreshold != null) {
            setMoistureCallbackThreshold(moistureThreshold);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeMoistureListener(super.getCallback());
        getDevice().removeMoistureReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMoistureCallbackPeriod(Long period) {
        try {
            getDevice().setMoistureCallbackPeriod(period);
            this.callbackPeriod = getDevice().getMoistureCallbackPeriod();
            super.getCallback().moistureCallbackPeriodChanged(this.callbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMoistureCallbackThreshold(DeviceMoistureCallbackThreshold threshold) {
        try {
            getDevice().setMoistureCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.moistureThreshold = new DeviceMoistureCallbackThreshold(getDevice().getMoistureCallbackThreshold());
            super.getCallback().moistureCallbackThresholdChanged(this.moistureThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMovingAverage(Short average) {
        try {
            getDevice().setMovingAverage(average);
            this.average = getDevice().getMovingAverage();
            super.getCallback().movingAverageChanged(this.average);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MoistureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
