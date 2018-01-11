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
package ch.quantasy.gateway.service.tinkerforge.thermoCouple;

import ch.quantasy.gateway.message.thermoCouple.ErrorEvent;
import ch.quantasy.gateway.message.thermoCouple.TemperatureEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.gateway.message.thermoCouple.DeviceConfiguration;
import ch.quantasy.gateway.message.thermoCouple.DeviceTemperatureCallbackThreshold;
import ch.quantasy.gateway.message.thermoCouple.ConfigurationStatus;
import ch.quantasy.gateway.message.thermoCouple.DebouncePeriodStatus;
import ch.quantasy.gateway.message.thermoCouple.TemperatureCallbackPeriodStatus;
import ch.quantasy.gateway.message.thermoCouple.TemperatureCallbackThresholdStatus;
import ch.quantasy.tinkerforge.device.thermoCouple.ThermoCoupleDevice;
import ch.quantasy.tinkerforge.device.thermoCouple.ThermoCoupleDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class ThermoCoupleService extends AbstractDeviceService<ThermoCoupleDevice, ThermoCoupleServiceContract> implements ThermoCoupleDeviceCallback {

    public ThermoCoupleService(ThermoCoupleDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new ThermoCoupleServiceContract(device));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void temperatureCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, new TemperatureCallbackPeriodStatus(period));
    }

    @Override
    public void temperatureCallbackThresholdChanged(DeviceTemperatureCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_TEMPERATURE_THRESHOLD, new TemperatureCallbackThresholdStatus(threshold));
    }

    @Override
    public void configurationChanged(DeviceConfiguration configuration) {
        readyToPublishStatus(getContract().STATUS_CONFIGURATION, new ConfigurationStatus(configuration));
    }

    @Override
    public void temperature(int i) {
        readyToPublishEvent(getContract().EVENT_TEMPERATURE, new TemperatureEvent(i));
    }

    @Override
    public void temperatureReached(int i) {
        readyToPublishEvent(getContract().EVENT_TEMPERATURE_REACHED, new TemperatureEvent(i));
    }

    @Override
    public void errorState(boolean bln, boolean bln1) {
        readyToPublishEvent(getContract().EVENT_ERROR, new ErrorEvent(bln, bln1));
    }

}
