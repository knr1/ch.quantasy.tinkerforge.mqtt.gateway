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
package ch.quantasy.tinkerforge.device.rotaryEncoder;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletRotaryEncoder;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RotaryEncoderDevice extends GenericDevice<BrickletRotaryEncoder, RotaryEncoderDeviceCallback> {

    private Long countCallbackPeriod;
    private Long debouncePeriod;
    private DeviceCountCallbackThreshold countThreshold;

    public RotaryEncoderDevice(TinkerforgeStackAddress address, BrickletRotaryEncoder device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addPressedListener(super.getCallback());
        getDevice().addReleasedListener(super.getCallback());
        getDevice().addCountListener(super.getCallback());
        getDevice().addCountReachedListener(super.getCallback());

        if (countCallbackPeriod != null) {
            setCountCallbackPeriod(this.countCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }

        if (countThreshold != null) {
            setPositionCallbackThreshold(countThreshold);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removePressedListener(super.getCallback());
        getDevice().removeReleasedListener(super.getCallback());
        getDevice().removeCountListener(super.getCallback());
        getDevice().removeCountReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RotaryEncoderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCountCallbackPeriod(Long period) {
        try {
            getDevice().setCountCallbackPeriod(period);
            this.countCallbackPeriod = getDevice().getCountCallbackPeriod();
            super.getCallback().countCallbackPeriodChanged(this.countCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RotaryEncoderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPositionCallbackThreshold(DeviceCountCallbackThreshold threshold) {
        try {
            getDevice().setCountCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.countThreshold = new DeviceCountCallbackThreshold(getDevice().getCountCallbackThreshold());
            super.getCallback().countCallbackThresholdChanged(this.countThreshold);

        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RotaryEncoderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCountReset(boolean reset) {
        try {
            long edgeCount = getDevice().getCount(reset);
            super.getCallback().countReset(edgeCount);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RotaryEncoderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
