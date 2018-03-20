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
package ch.quantasy.tinkerforge.device.temperatureIR;

import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.DeviceAmbientTemperatureCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.DeviceObjectTemperatureCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.TemperatureIRIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TemperatureIRDevice extends GenericDevice<BrickletTemperatureIR, TemperatureIRDeviceCallback, TemperatureIRIntent> {

    public TemperatureIRDevice(TinkerforgeStack stack, BrickletTemperatureIR device) throws NotConnectedException, TimeoutException {
        super(stack, device, new TemperatureIRIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletTemperatureIR device) {
        device.addAmbientTemperatureListener(super.getCallback());
        device.addAmbientTemperatureReachedListener(super.getCallback());
        device.addObjectTemperatureListener(super.getCallback());
        device.addObjectTemperatureReachedListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletTemperatureIR device) {
        device.removeAmbientTemperatureListener(super.getCallback());
        device.removeAmbientTemperatureReachedListener(super.getCallback());
        device.removeObjectTemperatureListener(super.getCallback());
        device.removeObjectTemperatureReachedListener(super.getCallback());
    }

    @Override
    public void update(TemperatureIRIntent intent) {
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
                Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.ambientTemperatureCallbackPeriod != null) {
            try {
                getDevice().setAmbientTemperatureCallbackPeriod(intent.ambientTemperatureCallbackPeriod);
                getIntent().ambientTemperatureCallbackPeriod = getDevice().getAmbientTemperatureCallbackPeriod();
                super.getCallback().ambientTemperatureCallbackPeriodChanged(getIntent().ambientTemperatureCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.ambientTemperatureCallbackThreshold != null) {
            try {
                getDevice().setAmbientTemperatureCallbackThreshold(intent.ambientTemperatureCallbackThreshold.getOption(), intent.ambientTemperatureCallbackThreshold.getMin(), intent.ambientTemperatureCallbackThreshold.getMax());
                getIntent().ambientTemperatureCallbackThreshold = new DeviceAmbientTemperatureCallbackThreshold(getDevice().getAmbientTemperatureCallbackThreshold());
                super.getCallback().ambientTemperatureCallbackThresholdChanged(getIntent().ambientTemperatureCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.objectTemperatureCallbackPeriod != null) {
            try {
                getDevice().setObjectTemperatureCallbackPeriod(intent.objectTemperatureCallbackPeriod);
                getIntent().objectTemperatureCallbackPeriod = getDevice().getObjectTemperatureCallbackPeriod();
                super.getCallback().objectTemperatureCallbackPeriodChanged(getIntent().objectTemperatureCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.objectTemperatureCallbackThreshold != null) {
            try {
                getDevice().setObjectTemperatureCallbackThreshold(intent.objectTemperatureCallbackThreshold.getOption(), intent.objectTemperatureCallbackThreshold.getMin(), intent.objectTemperatureCallbackThreshold.getMax());
                getIntent().objectTemperatureCallbackThreshold = new DeviceObjectTemperatureCallbackThreshold(getDevice().getObjectTemperatureCallbackThreshold());
                super.getCallback().objectTemperatureCallbackThresholdChanged(getIntent().objectTemperatureCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(TemperatureIRDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
