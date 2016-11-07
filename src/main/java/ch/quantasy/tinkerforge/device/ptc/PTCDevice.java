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
public class PTCDevice extends GenericDevice<BrickletPTC, PTCDeviceCallback> {

    private Short wireMode;
    private Long temperatureCallbackPeriod;
    private Long resistanceCallbackPeriod;
    private Long debouncePeriod;
    private DeviceTemperatureCallbackThreshold temperatureThreshold;
    private DeviceResistanceCallbackThreshold resistanceThreshold;
    private DeviceNoiseReductionFilter filter;

    public PTCDevice(TinkerforgeStack stack, BrickletPTC device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners(BrickletPTC device) {
        device.addTemperatureListener(super.getCallback());
        device.addTemperatureReachedListener(super.getCallback());
        device.addResistanceListener(super.getCallback());
        device.addResistanceReachedListener(super.getCallback());

        if (wireMode != null) {
            setWireMode(wireMode);
        }
        if (temperatureCallbackPeriod != null) {
            setTemperatureCallbackPeriod(this.temperatureCallbackPeriod);
        }
        if (resistanceCallbackPeriod != null) {
            setResistanceCallbackPeriod(this.resistanceCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (temperatureThreshold != null) {
            setTemperatureCallbackThreshold(temperatureThreshold);
        }
        if (filter != null) {
            setNoiseReductionFilter(filter);
        }

    }

    @Override
    protected void removeDeviceListeners(BrickletPTC device) {
        device.removeTemperatureListener(super.getCallback());
        device.removeTemperatureReachedListener(super.getCallback());
        device.removeResistanceListener(super.getCallback());
        device.removeResistanceReachedListener(super.getCallback());

    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTemperatureCallbackPeriod(Long period) {
        try {
            getDevice().setTemperatureCallbackPeriod(period);
            this.resistanceCallbackPeriod = getDevice().getTemperatureCallbackPeriod();
            super.getCallback().temperatureCallbackPeriodChanged(this.resistanceCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTemperatureCallbackThreshold(DeviceTemperatureCallbackThreshold threshold) {
        try {
            getDevice().setTemperatureCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.temperatureThreshold = new DeviceTemperatureCallbackThreshold(getDevice().getTemperatureCallbackThreshold());
            super.getCallback().temperatureCallbackThresholdChanged(this.temperatureThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setResistanceCallbackPeriod(Long period) {
        try {
            getDevice().setResistanceCallbackPeriod(period);
            this.resistanceCallbackPeriod = getDevice().getResistanceCallbackPeriod();
            super.getCallback().resistanceCallbackPeriodChanged(this.resistanceCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setResistanceCallbackThreshold(DeviceResistanceCallbackThreshold threshold) {
        try {
            getDevice().setResistanceCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.resistanceThreshold = new DeviceResistanceCallbackThreshold(getDevice().getResistanceCallbackThreshold());
            super.getCallback().resistanceCallbackThresholdChanged(this.resistanceThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNoiseReductionFilter(DeviceNoiseReductionFilter filter) {
        try {
            getDevice().setNoiseRejectionFilter(filter.getFilter().getValue());
            this.filter = new DeviceNoiseReductionFilter(getDevice().getNoiseRejectionFilter());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setWireMode(short wireMode) {
        try {
            getDevice().setWireMode(wireMode);
            this.wireMode = getDevice().getWireMode();
            super.getCallback().wireModeChanged(this.wireMode);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(PTCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}