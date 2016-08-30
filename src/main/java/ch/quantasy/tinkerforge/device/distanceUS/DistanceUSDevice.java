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
package ch.quantasy.tinkerforge.device.distanceUS;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DistanceUSDevice extends GenericDevice<BrickletDistanceUS, DistanceUSDeviceCallback> {

    private Long callbackPeriod;
    private Long debouncePeriod;
    private Short movingAverage;
    private DeviceDistanceCallbackThreshold distanceThreshold;

    public DistanceUSDevice(TinkerforgeStackAddress address, BrickletDistanceUS device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addDistanceListener(super.getCallback());
        getDevice().addDistanceReachedListener(super.getCallback());

        if (this.movingAverage != null) {
            setMovingAverage(movingAverage);
        }
        if (this.debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (this.callbackPeriod != null) {
            setDistanceCallbackPeriod(callbackPeriod);
        }
        if (this.distanceThreshold != null) {
            setDistanceCallbackThreshold(distanceThreshold);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeDistanceListener(super.getCallback());
        getDevice().removeDistanceReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceUSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDistanceCallbackPeriod(Long period) {
        try {
            getDevice().setDistanceCallbackPeriod(period);
            this.callbackPeriod = getDevice().getDistanceCallbackPeriod();
            super.getCallback().distanceCallbackPeriodChanged(this.callbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceUSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDistanceCallbackThreshold(DeviceDistanceCallbackThreshold threshold) {
        try {
            getDevice().setDistanceCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.distanceThreshold = new DeviceDistanceCallbackThreshold(getDevice().getDistanceCallbackThreshold());
            super.getCallback().distanceCallbackThresholdChanged(this.distanceThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceUSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMovingAverage(Short movingAverage) {
        try {
            getDevice().setMovingAverage(movingAverage);
            this.movingAverage = getDevice().getMovingAverage();
            super.getCallback().movingAverageChanged(movingAverage);

        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DistanceUSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
