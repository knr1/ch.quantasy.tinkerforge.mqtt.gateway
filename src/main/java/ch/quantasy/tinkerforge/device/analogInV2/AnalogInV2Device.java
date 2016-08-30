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
package ch.quantasy.tinkerforge.device.analogInV2;


import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletAnalogInV2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogInV2Device extends GenericDevice<BrickletAnalogInV2, AnalogInV2DeviceCallback> {

    private Long analogValueCallbackPeriod;
    private Long voltageCallbackPeriod;
    private Long debouncePeriod;
    private DeviceAnalogValueCallbackThreshold analogValueCallbackThreshold;
    private DeviceVoltageCallbackThreshold altitudeThreshold;
    private Short movingAverage;

    public AnalogInV2Device(TinkerforgeStackAddress address, BrickletAnalogInV2 device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAnalogValueListener(super.getCallback());
        getDevice().addAnalogValueReachedListener(super.getCallback());
        getDevice().addVoltageListener(super.getCallback());
        getDevice().addVoltageReachedListener(super.getCallback());
        if (analogValueCallbackPeriod != null) {
            setAnalogValueCallbackPeriod(analogValueCallbackPeriod);
        }
        if (voltageCallbackPeriod != null) {
            setVoltageCallbackPeriod(this.voltageCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (analogValueCallbackThreshold != null) {
            setAirPressureThreshold(analogValueCallbackThreshold);
        }
        if (altitudeThreshold != null) {
            setVoltageCallbackThreshold(altitudeThreshold);
        }
        if (movingAverage != null) {
            setMovingAverage(movingAverage);
        }
       
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAnalogValueListener(super.getCallback());
        getDevice().removeAnalogValueReachedListener(super.getCallback());
        getDevice().removeVoltageListener(super.getCallback());
        getDevice().removeVoltageReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AnalogInV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAnalogValueCallbackPeriod(Long period) {
        try {
            getDevice().setAnalogValueCallbackPeriod(period);
            this.analogValueCallbackPeriod = getDevice().getAnalogValueCallbackPeriod();
            super.getCallback().analogValueCallbackPeriodChanged(this.analogValueCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AnalogInV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVoltageCallbackPeriod(Long period) {
        try {
            getDevice().setVoltageCallbackPeriod(period);
            this.voltageCallbackPeriod = getDevice().getVoltageCallbackPeriod();
            super.getCallback().voltageCallbackPeriodChanged(this.voltageCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AnalogInV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAirPressureThreshold(DeviceAnalogValueCallbackThreshold threshold) {
        try {
            getDevice().setAnalogValueCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.analogValueCallbackThreshold = new DeviceAnalogValueCallbackThreshold(getDevice().getAnalogValueCallbackThreshold());
            super.getCallback().analogValueCallbackThresholdChanged(this.analogValueCallbackThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AnalogInV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVoltageCallbackThreshold(DeviceVoltageCallbackThreshold threshold) {
        try {
            getDevice().setVoltageCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.altitudeThreshold = new DeviceVoltageCallbackThreshold(getDevice().getVoltageCallbackThreshold());
            super.getCallback().voltageCallbackThresholdChanged(this.altitudeThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AnalogInV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMovingAverage(Short movingAverage) {
        try {
            getDevice().setMovingAverage(movingAverage);
            this.movingAverage = getDevice().getMovingAverage();
            super.getCallback().movingAverageChanged(this.movingAverage);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(AnalogInV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
