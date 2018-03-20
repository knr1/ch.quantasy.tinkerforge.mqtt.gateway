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
package ch.quantasy.tinkerforge.device.ptc;

import ch.quantasy.gateway.binding.tinkerforge.ptc.DeviceNoiseReductionFilter;
import ch.quantasy.gateway.binding.tinkerforge.ptc.DeviceResistanceCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.ptc.DeviceTemperatureCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.ptc.PTCIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletPTC;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class PTCDevice extends GenericDevice<BrickletPTC, PTCDeviceCallback, PTCIntent> {

    public PTCDevice(TinkerforgeStack stack, BrickletPTC device) throws NotConnectedException, TimeoutException {
        super(stack, device, new PTCIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletPTC device) {
        device.addTemperatureListener(super.getCallback());
        device.addTemperatureReachedListener(super.getCallback());
        device.addResistanceListener(super.getCallback());
        device.addResistanceReachedListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletPTC device) {
        device.removeTemperatureListener(super.getCallback());
        device.removeTemperatureReachedListener(super.getCallback());
        device.removeResistanceListener(super.getCallback());
        device.removeResistanceReachedListener(super.getCallback());

    }

    @Override
    public void update(PTCIntent intent) {
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
                Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.temperatureCallbackPeriod != null) {
            try {
                getDevice().setTemperatureCallbackPeriod(intent.temperatureCallbackPeriod);
                getIntent().temperatureCallbackPeriod = getDevice().getTemperatureCallbackPeriod();
                super.getCallback().temperatureCallbackPeriodChanged(getIntent().temperatureCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.temperatureThreshold != null) {
            try {
                getDevice().setTemperatureCallbackThreshold(intent.temperatureThreshold.getOption(), intent.temperatureThreshold.getMin(), intent.temperatureThreshold.getMax());
                getIntent().temperatureThreshold = new DeviceTemperatureCallbackThreshold(getDevice().getTemperatureCallbackThreshold());
                super.getCallback().temperatureCallbackThresholdChanged(getIntent().temperatureThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.resistanceCallbackPeriod != null) {
            try {
                getDevice().setResistanceCallbackPeriod(intent.resistanceCallbackPeriod);
                getIntent().resistanceCallbackPeriod = getDevice().getResistanceCallbackPeriod();
                super.getCallback().resistanceCallbackPeriodChanged(getIntent().resistanceCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.resistanceThreshold != null) {
            try {
                getDevice().setResistanceCallbackThreshold(intent.resistanceThreshold.getOption(), intent.resistanceThreshold.getMin(), intent.resistanceThreshold.getMax());
                getIntent().resistanceThreshold = new DeviceResistanceCallbackThreshold(getDevice().getResistanceCallbackThreshold());
                super.getCallback().resistanceCallbackThresholdChanged(getIntent().resistanceThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.noiseReductionFilter != null) {
            try {
                getDevice().setNoiseRejectionFilter(intent.noiseReductionFilter.getFilter().getValue());
                getIntent().noiseReductionFilter = new DeviceNoiseReductionFilter(getDevice().getNoiseRejectionFilter());
                super.getCallback().noiseReductionFilterChanged(getIntent().noiseReductionFilter);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.wireMode != null) {
            try {
                getDevice().setWireMode(intent.wireMode);
                getIntent().wireMode = getDevice().getWireMode();
                super.getCallback().wireModeChanged(getIntent().wireMode);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
