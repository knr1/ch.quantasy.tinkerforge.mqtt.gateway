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
package ch.quantasy.tinkerforge.device.distanceIR;

import ch.quantasy.gateway.binding.tinkerforge.barometer.BarometerIntent;
import ch.quantasy.gateway.binding.tinkerforge.distanceIR.DeviceDistanceCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.distanceIR.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.distanceIR.DistanceIRIntent;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DistanceIRDevice extends GenericDevice<BrickletDistanceIR, DistanceIRDeviceCallback, DistanceIRIntent> {

    public DistanceIRDevice(TinkerforgeStack stack, BrickletDistanceIR device) throws NotConnectedException, TimeoutException {
        super(stack, device, new DistanceIRIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletDistanceIR device) {
        device.addAnalogValueListener(super.getCallback());
        device.addAnalogValueReachedListener(super.getCallback());
        device.addDistanceListener(super.getCallback());
        device.addDistanceReachedListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletDistanceIR device) {
        device.removeAnalogValueListener(super.getCallback());
        device.removeAnalogValueReachedListener(super.getCallback());
        device.removeDistanceListener(super.getCallback());
        device.removeDistanceReachedListener(super.getCallback());
    }

    @Override
    public void update(DistanceIRIntent intent) {
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
                Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.analogCallbackPeriod != null) {
            try {
                getDevice().setAnalogValueCallbackPeriod(intent.analogCallbackPeriod);
                getIntent().analogCallbackPeriod = getDevice().getAnalogValueCallbackPeriod();
                super.getCallback().analogValueCallbackPeriodChanged(getIntent().analogCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.distanceCallbackPeriod != null) {
            try {
                getDevice().setDistanceCallbackPeriod(intent.distanceCallbackPeriod);
                getIntent().distanceCallbackPeriod = getDevice().getDistanceCallbackPeriod();
                super.getCallback().distanceCallbackPeriodChanged(getIntent().distanceCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.analogValueCallbackThreshold != null) {
            try {
                getDevice().setAnalogValueCallbackThreshold(intent.analogValueCallbackThreshold.option, intent.analogValueCallbackThreshold.min, intent.analogValueCallbackThreshold.max);
                getIntent().analogValueCallbackThreshold = new DeviceAnalogValueCallbackThreshold(getDevice().getAnalogValueCallbackThreshold());
                super.getCallback().analogValueCallbackThresholdChanged(getIntent().analogValueCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.distanceCallbackThreshold != null) {
            try {
                getDevice().setDistanceCallbackThreshold(intent.distanceCallbackThreshold.option, intent.distanceCallbackThreshold.min, intent.distanceCallbackThreshold.max);
                getIntent().distanceCallbackThreshold = new DeviceDistanceCallbackThreshold(getDevice().getDistanceCallbackThreshold());
                super.getCallback().distanceCallbackThresholdChanged(getIntent().distanceCallbackThreshold);

            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DistanceIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
