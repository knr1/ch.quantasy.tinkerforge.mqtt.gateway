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
package ch.quantasy.tinkerforge.device.accelerometer;

import ch.quantasy.gateway.binding.tinkerforge.accelerometer.AccelerometerIntent;
import ch.quantasy.gateway.binding.tinkerforge.accelerometer.DeviceConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.accelerometer.DeviceAccelerationCallbackThreshold;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletAccelerometer;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AccelerometerDevice extends GenericDevice<BrickletAccelerometer, AccelerometerDeviceCallback, AccelerometerIntent> {

    public AccelerometerDevice(TinkerforgeStack stack, BrickletAccelerometer device) throws NotConnectedException, TimeoutException {
        super(stack, device, new AccelerometerIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletAccelerometer device) {
        device.addAccelerationListener(super.getCallback());
        device.addAccelerationReachedListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletAccelerometer device) {
        device.removeAccelerationListener(super.getCallback());
        device.removeAccelerationReachedListener(super.getCallback());
    }

    @Override
    public void update(AccelerometerIntent intent) {
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
                Logger.getLogger(AccelerometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.callbackPeriod != null) {
            try {
                getDevice().setAccelerationCallbackPeriod(intent.callbackPeriod);
                getIntent().callbackPeriod = getDevice().getAccelerationCallbackPeriod();
                super.getCallback().accelerationCallbackPeriodChanged(getIntent().callbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(AccelerometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.accelerationThreshold != null) {
            try {
                getDevice().setAccelerationCallbackThreshold(intent.accelerationThreshold.option, intent.accelerationThreshold.minX, intent.accelerationThreshold.maxX,
                        intent.accelerationThreshold.minY, intent.accelerationThreshold.maxY, intent.accelerationThreshold.minZ, intent.accelerationThreshold.maxZ);
                getIntent().accelerationThreshold = new DeviceAccelerationCallbackThreshold(getDevice().getAccelerationCallbackThreshold());
                super.getCallback().accelerationCallbackThresholdChanged(getIntent().accelerationThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(AccelerometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.configuration != null) {
            try {
                getDevice().setConfiguration(intent.configuration.getDataRate().getValue(), intent.configuration.getFullScale().getValue(), intent.configuration.getFilterBandwidth().getValue());
                getIntent().configuration = new DeviceConfiguration(getDevice().getConfiguration());
                super.getCallback().configurationChanged(getIntent().configuration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(AccelerometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
