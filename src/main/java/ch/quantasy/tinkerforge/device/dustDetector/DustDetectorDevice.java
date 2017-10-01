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
package ch.quantasy.tinkerforge.device.dustDetector;

import ch.quantasy.gateway.intent.dustDetector.DeviceDustDensityCallbackThreshold;
import ch.quantasy.gateway.intent.dustDetector.DustDetectorIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletDustDetector;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DustDetectorDevice extends GenericDevice<BrickletDustDetector, DustDetectorDeviceCallback, DustDetectorIntent> {

    public DustDetectorDevice(TinkerforgeStack stack, BrickletDustDetector device) throws NotConnectedException, TimeoutException {
        super(stack, device, new DustDetectorIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletDustDetector device) {
        device.addDustDensityListener(getCallback());
        device.addDustDensityReachedListener(getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletDustDetector device) {
        device.removeDustDensityListener(getCallback());
        device.removeDustDensityReachedListener(getCallback());
    }

    @Override
    public void update(DustDetectorIntent intent) {
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
                Logger.getLogger(DustDetectorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.dustDensityCallbackPeriod != null) {
            try {
                getDevice().setDustDensityCallbackPeriod(intent.dustDensityCallbackPeriod);
                getIntent().dustDensityCallbackPeriod = getDevice().getDustDensityCallbackPeriod();
                super.getCallback().dustDensityCallbackPeriodChanged(getIntent().dustDensityCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DustDetectorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.dustDensityCallbackThreshold != null) {
            try {
                getDevice().setDustDensityCallbackThreshold(intent.dustDensityCallbackThreshold.getOption(), intent.dustDensityCallbackThreshold.getMin(), intent.dustDensityCallbackThreshold.getMax());
                getIntent().dustDensityCallbackThreshold = new DeviceDustDensityCallbackThreshold(getDevice().getDustDensityCallbackThreshold());
                super.getCallback().dustDensityCallbackThresholdChanged(getIntent().dustDensityCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DustDetectorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.movingAverage != null) {
            try {
                getDevice().setMovingAverage(intent.movingAverage);
                getIntent().movingAverage = getDevice().getMovingAverage();
                super.getCallback().movingAverageChanged(getIntent().movingAverage);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DustDetectorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
