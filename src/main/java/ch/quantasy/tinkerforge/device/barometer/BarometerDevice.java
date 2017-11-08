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
package ch.quantasy.tinkerforge.device.barometer;

import ch.quantasy.gateway.message.intent.barometer.BarometerIntent;
import ch.quantasy.gateway.message.intent.barometer.DeviceAirPressureCallbackThreshold;
import ch.quantasy.gateway.message.intent.barometer.DeviceAveraging;
import ch.quantasy.gateway.message.intent.barometer.DeviceAltitudeCallbackThreshold;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BarometerDevice extends GenericDevice<BrickletBarometer, BarometerDeviceCallback, BarometerIntent> {

    public BarometerDevice(TinkerforgeStack stack, BrickletBarometer device) throws NotConnectedException, TimeoutException {
        super(stack, device, new BarometerIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletBarometer device) {
        device.addAirPressureListener(super.getCallback());
        device.addAirPressureReachedListener(super.getCallback());
        device.addAltitudeListener(super.getCallback());
        device.addAltitudeReachedListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletBarometer device) {
        device.removeAltitudeListener(super.getCallback());
        device.removeAltitudeReachedListener(super.getCallback());
        device.removeAirPressureListener(super.getCallback());
        device.removeAirPressureReachedListener(super.getCallback());
    }

    @Override
    public void update(BarometerIntent intent) {
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
        if (intent.airPressureCallbackPeriod != null) {
            try {
                getDevice().setAirPressureCallbackPeriod(intent.airPressureCallbackPeriod);
                getIntent().airPressureCallbackPeriod = getDevice().getAirPressureCallbackPeriod();
                super.getCallback().airPressureCallbackPeriodChanged(getIntent().airPressureCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.referenceAirPressure != null) {
            try {
                getDevice().setReferenceAirPressure(intent.referenceAirPressure);
                getIntent().referenceAirPressure = getDevice().getReferenceAirPressure();
                super.getCallback().referenceAirPressureChanged(getIntent().referenceAirPressure);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.altitudeCallbackPeriod != null) {
            try {
                getDevice().setAltitudeCallbackPeriod(intent.altitudeCallbackPeriod);
                getIntent().altitudeCallbackPeriod = getDevice().getAltitudeCallbackPeriod();
                super.getCallback().altitudeCallbackPeriodChanged(getIntent().altitudeCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.airPressureCallbackThreshold != null) {
            try {
                getDevice().setAirPressureCallbackThreshold(intent.airPressureCallbackThreshold.getOption(), intent.airPressureCallbackThreshold.getMin(), intent.airPressureCallbackThreshold.getMax());
                getIntent().airPressureCallbackThreshold = new DeviceAirPressureCallbackThreshold(getDevice().getAirPressureCallbackThreshold());
                super.getCallback().airPressureCallbackThresholdChanged(getIntent().airPressureCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.altitudeCallbackThreshold != null) {
            try {
                getDevice().setAltitudeCallbackThreshold(intent.altitudeCallbackThreshold.getOption(), intent.altitudeCallbackThreshold.getMin(), intent.altitudeCallbackThreshold.getMax());
                getIntent().altitudeCallbackThreshold = new DeviceAltitudeCallbackThreshold(getDevice().getAltitudeCallbackThreshold());
                super.getCallback().altitudeCallbackThresholdChanged(getIntent().altitudeCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.averaging != null) {
            try {
                getDevice().setAveraging(intent.averaging.getMovingAveragePressure(), intent.averaging.getAveragingPressure(), intent.averaging.getAveragingTemperature());
                getIntent().averaging = new DeviceAveraging(getDevice().getAveraging());
                super.getCallback().averagingChanged(getIntent().averaging);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(BarometerDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
