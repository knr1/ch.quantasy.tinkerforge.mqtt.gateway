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
package ch.quantasy.tinkerforge.device.rotaryEncoder;

import ch.quantasy.gateway.message.rotaryEncoder.DeviceCountCallbackThreshold;
import ch.quantasy.gateway.message.rotaryEncoder.RotaryEncoderIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletRotaryEncoder;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RotaryEncoderDevice extends GenericDevice<BrickletRotaryEncoder, RotaryEncoderDeviceCallback, RotaryEncoderIntent> {

    public RotaryEncoderDevice(TinkerforgeStack stack, BrickletRotaryEncoder device) throws NotConnectedException, TimeoutException {
        super(stack, device, new RotaryEncoderIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletRotaryEncoder device) {
        device.addPressedListener(super.getCallback());
        device.addReleasedListener(super.getCallback());
        device.addCountListener(super.getCallback());
        device.addCountReachedListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletRotaryEncoder device) {
        device.removePressedListener(super.getCallback());
        device.removeReleasedListener(super.getCallback());
        device.removeCountListener(super.getCallback());
        device.removeCountReachedListener(super.getCallback());
    }

    @Override
    public void update(RotaryEncoderIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }

        if (intent.debouncePeriod != null) {
            try {
                getDevice().setDebouncePeriod(intent.debouncePeriod);
                getIntent().debouncePeriod = getDevice().getDebouncePeriod();
                super.getCallback().debouncePeriodChanged(getIntent().debouncePeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(RotaryEncoderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.countCallbackPeriod != null) {
            try {
                getDevice().setCountCallbackPeriod(intent.countCallbackPeriod);
                getIntent().countCallbackPeriod = getDevice().getCountCallbackPeriod();
                super.getCallback().countCallbackPeriodChanged(getIntent().countCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(RotaryEncoderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.countThreshold != null) {
            try {
                getDevice().setCountCallbackThreshold(intent.countThreshold.getOption(), intent.countThreshold.getMin(), intent.countThreshold.getMax());
                getIntent().countThreshold = new DeviceCountCallbackThreshold(getDevice().getCountCallbackThreshold());
                super.getCallback().countCallbackThresholdChanged(getIntent().countThreshold);

            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(RotaryEncoderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.countReset != null) {
            if (intent.countReset == true) {

                try {
                    int edgeCount = getDevice().getCount(intent.countReset);
                    super.getCallback().countReset(edgeCount);
                } catch (TimeoutException | NotConnectedException ex) {
                    Logger.getLogger(RotaryEncoderDevice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
