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
package ch.quantasy.tinkerforge.device.temperature;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TemperatureDevice extends GenericDevice<BrickletTemperature, TemperatureDeviceCallback> {

    private DeviceI2CMode mode;
    private Long callbackPeriod;
    private Long debouncePeriod;
    private DeviceTemperatureCallbackThreshold illuminanceThreshold;

    public TemperatureDevice(TinkerforgeStack stack, BrickletTemperature device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners(BrickletTemperature device) {
        device.addTemperatureListener(super.getCallback());
        device.addTemperatureReachedListener(super.getCallback());

        if (mode != null) {
            setI2CMode(mode);
        }
        if (callbackPeriod != null) {
            setTemperatureCallbackPeriod(this.callbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (illuminanceThreshold != null) {
            setTemperatureCallbackThreshold(illuminanceThreshold);
        }

    }

    @Override
    protected void removeDeviceListeners(BrickletTemperature device) {
        device.removeTemperatureListener(super.getCallback());
        device.removeTemperatureReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTemperatureCallbackPeriod(Long period) {
        try {
            getDevice().setTemperatureCallbackPeriod(period);
            this.callbackPeriod = getDevice().getTemperatureCallbackPeriod();
            super.getCallback().temperatureCallbackPeriodChanged(this.callbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTemperatureCallbackThreshold(DeviceTemperatureCallbackThreshold threshold) {
        try {
            getDevice().setTemperatureCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.illuminanceThreshold = new DeviceTemperatureCallbackThreshold(getDevice().getTemperatureCallbackThreshold());
            super.getCallback().temperatureCallbackThresholdChanged(this.illuminanceThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setI2CMode(DeviceI2CMode mode) {
        try {
            getDevice().setI2CMode(mode.getMode().getValue());
            this.mode = new DeviceI2CMode(getDevice().getI2CMode());
            super.getCallback().i2CModeChanged(this.mode);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(TemperatureDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
