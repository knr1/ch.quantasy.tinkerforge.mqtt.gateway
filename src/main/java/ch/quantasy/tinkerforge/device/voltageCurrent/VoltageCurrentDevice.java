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
package ch.quantasy.tinkerforge.device.voltageCurrent;

import ch.quantasy.gateway.intent.voltageCurrent.DeviceVoltagCallbackThreshold;
import ch.quantasy.gateway.intent.voltageCurrent.DeviceConfiguration;
import ch.quantasy.gateway.intent.voltageCurrent.DeviceCalibration;
import ch.quantasy.gateway.intent.voltageCurrent.DeviceCurrentCallbackThreshold;
import ch.quantasy.gateway.intent.voltageCurrent.DevicePowerCallbackThreshold;
import ch.quantasy.gateway.intent.voltageCurrent.VoltageCurrentIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class VoltageCurrentDevice extends GenericDevice<BrickletVoltageCurrent, VoltageCurrentDeviceCallback, VoltageCurrentIntent> {

    public VoltageCurrentDevice(TinkerforgeStack stack, BrickletVoltageCurrent device) throws NotConnectedException, TimeoutException {
        super(stack, device, new VoltageCurrentIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletVoltageCurrent device) {
        device.addCurrentListener(super.getCallback());
        device.addCurrentReachedListener(super.getCallback());
        device.addPowerListener(super.getCallback());
        device.addPowerReachedListener(super.getCallback());
        device.addVoltageListener(super.getCallback());
        device.addVoltageReachedListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletVoltageCurrent device) {
        device.removeCurrentListener(super.getCallback());
        device.removeCurrentReachedListener(super.getCallback());
        device.removePowerListener(super.getCallback());
        device.removePowerReachedListener(super.getCallback());
        device.removeVoltageListener(super.getCallback());
        device.removeVoltageReachedListener(super.getCallback());
    }

    @Override
    public void update(VoltageCurrentIntent intent) {
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
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.currentCallbackPeriod != null) {
            try {
                getDevice().setCurrentCallbackPeriod(intent.currentCallbackPeriod);
                getIntent().currentCallbackPeriod = getDevice().getCurrentCallbackPeriod();
                super.getCallback().currentCallbackPeriodChanged(getIntent().currentCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.currentCalbackThreshold != null) {
            try {
                getDevice().setCurrentCallbackThreshold(intent.currentCalbackThreshold.getOption(), intent.currentCalbackThreshold.getMin(), intent.currentCalbackThreshold.getMax());
                getIntent().currentCalbackThreshold = new DeviceCurrentCallbackThreshold(getDevice().getCurrentCallbackThreshold());
                super.getCallback().currentCallbackThresholdChanged(getIntent().currentCalbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.voltageCallbackPeriod != null) {
            try {
                getDevice().setVoltageCallbackPeriod(intent.voltageCallbackPeriod);
                getIntent().voltageCallbackPeriod = getDevice().getVoltageCallbackPeriod();
                super.getCallback().voltageCallbackPeriodChanged(getIntent().voltageCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.voltageCallbackThreshold != null) {
            try {
                getDevice().setVoltageCallbackThreshold(intent.voltageCallbackThreshold.getOption(), intent.voltageCallbackThreshold.getMin(), intent.voltageCallbackThreshold.getMax());
                getIntent().voltageCallbackThreshold = new DeviceVoltagCallbackThreshold(getDevice().getVoltageCallbackThreshold());
                super.getCallback().voltageCallbackThresholdChanged(getIntent().voltageCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.powerCallbackPeriod != null) {
            try {
                getDevice().setPowerCallbackPeriod(intent.powerCallbackPeriod);
                getIntent().powerCallbackPeriod = getDevice().getPowerCallbackPeriod();
                super.getCallback().powerCallbackPeriodChanged(getIntent().powerCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.powerCallbackThreshold != null) {
            try {
                getDevice().setPowerCallbackThreshold(intent.powerCallbackThreshold.getOption(), intent.powerCallbackThreshold.getMin(), intent.powerCallbackThreshold.getMax());
                getIntent().powerCallbackThreshold = new DevicePowerCallbackThreshold(getDevice().getPowerCallbackThreshold());
                super.getCallback().powerCallbackThresholdChanged(getIntent().powerCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.configuration != null) {
            try {
                getDevice().setConfiguration(intent.configuration.getAveraging().getValue(), intent.configuration.getVoltageConversionTime().getValue(), intent.configuration.getCurrentConversionTime().getValue());
                getIntent().configuration = new DeviceConfiguration(getDevice().getConfiguration());
                super.getCallback().configurationChanged(getIntent().configuration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.calibration != null) {
            try {
                getDevice().setCalibration(intent.calibration.getGainMultiplier(), intent.calibration.getGainDivisor());
                getIntent().calibration = new DeviceCalibration(getDevice().getCalibration());
                super.getCallback().calibrationChanged(getIntent().calibration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
