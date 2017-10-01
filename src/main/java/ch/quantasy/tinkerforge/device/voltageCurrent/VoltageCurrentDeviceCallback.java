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
import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletVoltageCurrent;

/**
 *
 * @author reto
 */
public interface VoltageCurrentDeviceCallback extends DeviceCallback, BrickletVoltageCurrent.CurrentListener, BrickletVoltageCurrent.CurrentReachedListener, BrickletVoltageCurrent.PowerListener, BrickletVoltageCurrent.PowerReachedListener, BrickletVoltageCurrent.VoltageListener, BrickletVoltageCurrent.VoltageReachedListener {

    public void debouncePeriodChanged(long period);

    public void configurationChanged(DeviceConfiguration average);

    public void calibrationChanged(DeviceCalibration calibration);

    public void currentCallbackPeriodChanged(long period);

    public void currentCallbackThresholdChanged(DeviceCurrentCallbackThreshold threshold);

    public void voltageCallbackThresholdChanged(DeviceVoltagCallbackThreshold voltageThreshold);

    public void voltageCallbackPeriodChanged(long voltageCallbackPeriod);

    public void powerCallbackPeriodChanged(long powerCallbackPeriod);

    public void powerCallbackThresholdChanged(DevicePowerCallbackThreshold powerThreshold);
}
