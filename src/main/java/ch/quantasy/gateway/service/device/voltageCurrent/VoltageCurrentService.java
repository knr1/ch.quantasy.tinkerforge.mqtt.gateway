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
package ch.quantasy.gateway.service.device.voltageCurrent;

import ch.quantasy.gateway.message.event.voltageCurrent.CurrentEvent;
import ch.quantasy.gateway.message.event.voltageCurrent.PowerEvent;
import ch.quantasy.gateway.message.event.voltageCurrent.VoltageEvent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.gateway.message.intent.voltageCurrent.DeviceCalibration;
import ch.quantasy.gateway.message.intent.voltageCurrent.DeviceConfiguration;
import ch.quantasy.gateway.message.intent.voltageCurrent.DeviceCurrentCallbackThreshold;
import ch.quantasy.gateway.message.intent.voltageCurrent.DevicePowerCallbackThreshold;
import ch.quantasy.gateway.message.intent.voltageCurrent.DeviceVoltagCallbackThreshold;
import ch.quantasy.tinkerforge.device.voltageCurrent.VoltageCurrentDevice;
import ch.quantasy.tinkerforge.device.voltageCurrent.VoltageCurrentDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class VoltageCurrentService extends AbstractDeviceService<VoltageCurrentDevice, VoltageCurrentServiceContract> implements VoltageCurrentDeviceCallback {

    public VoltageCurrentService(VoltageCurrentDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new VoltageCurrentServiceContract(device));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void configurationChanged(DeviceConfiguration configuration) {
        publishStatus(getContract().STATUS_CONFIGURATION, configuration);
    }

    @Override
    public void currentCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_CURRENT_CALLBACK_PERIOD, period);
    }

    @Override
    public void currentCallbackThresholdChanged(DeviceCurrentCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_CURRENT_THRESHOLD, threshold);
    }

    @Override
    public void calibrationChanged(DeviceCalibration calibration) {
        publishStatus(getContract().STATUS_CALIBRATION, calibration);
    }

    @Override
    public void voltageCallbackThresholdChanged(DeviceVoltagCallbackThreshold voltageThreshold) {
        publishStatus(getContract().STATUS_VOLTAGE_THRESHOLD, voltageThreshold);
    }

    @Override
    public void voltageCallbackPeriodChanged(long voltageCallbackPeriod) {
        publishStatus(getContract().STATUS_VOLTAGE_CALLBACK_PERIOD, voltageCallbackPeriod);
    }

    @Override
    public void powerCallbackPeriodChanged(long powerCallbackPeriod) {
        publishStatus(getContract().STATUS_POWER_CALLBACK_PERIOD, powerCallbackPeriod);
    }

    @Override
    public void powerCallbackThresholdChanged(DevicePowerCallbackThreshold powerThreshold) {
        publishStatus(getContract().STATUS_POWER_THRESHOLD, powerThreshold);
    }

    @Override
    public void current(int i) {
        readyToPublishEvent(getContract().EVENT_CURRENT, new CurrentEvent(i));
    }

    @Override
    public void currentReached(int i) {
        readyToPublishEvent(getContract().EVENT_CURRENT_REACHED, new CurrentEvent(i));
    }

    @Override
    public void power(int i) {
        readyToPublishEvent(getContract().EVENT_POWER, new PowerEvent(i));
    }

    @Override
    public void powerReached(int i) {
        readyToPublishEvent(getContract().EVENT_POWER_REACHED, new PowerEvent(i));
    }

    @Override
    public void voltage(int i) {
        readyToPublishEvent(getContract().EVENT_VOLTAGE, new VoltageEvent(i));
    }

    @Override
    public void voltageReached(int i) {
        readyToPublishEvent(getContract().EVENT_VOLTAGE_REACHED, new VoltageEvent(i));
    }

}
