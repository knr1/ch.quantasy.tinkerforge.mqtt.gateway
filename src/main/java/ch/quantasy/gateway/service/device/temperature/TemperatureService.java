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
package ch.quantasy.gateway.service.device.temperature;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.temperature.DeviceI2CMode;
import ch.quantasy.tinkerforge.device.temperature.DeviceTemperatureCallbackThreshold;
import ch.quantasy.tinkerforge.device.temperature.TemperatureDevice;
import ch.quantasy.tinkerforge.device.temperature.TemperatureDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class TemperatureService extends AbstractDeviceService<TemperatureDevice, TemperatureServiceContract> implements TemperatureDeviceCallback {

    public TemperatureService(TemperatureDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new TemperatureServiceContract(device));
        publishDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-2500..8500]\n max: [-2500..8500]");
        publishDescription(getContract().INTENT_I2C_MODE, "mode:[Fast|Slow]");

        publishDescription(getContract().EVENT_TEMPERATURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-2500..8500]\n");
        publishDescription(getContract().EVENT_TEMPERATURE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-2500..8500]\n");
        publishDescription(getContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-2500..8500]\n max: [-2500..8500]");
        publishDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_I2CMODE, "mode:[Slow|Fast]");
    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_TEMPERATURE_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setTemperatureCallbackPeriod(period);
        }

        if (string.startsWith(getContract().INTENT_TEMPERATURE_THRESHOLD)) {

            DeviceTemperatureCallbackThreshold threshold = getMapper().readValue(payload, DeviceTemperatureCallbackThreshold.class);
            getDevice().setTemperatureCallbackThreshold(threshold);
        }

        if (string.startsWith(getContract().INTENT_I2C_MODE)) {

            DeviceI2CMode mode = getMapper().readValue(payload, DeviceI2CMode.class);
            getDevice().setI2CMode(mode);
        }
    }

    @Override
    public void debouncePeriodChanged(long period) {
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void temperatureCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void temperatureCallbackThresholdChanged(DeviceTemperatureCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_TEMPERATURE_THRESHOLD, threshold);
    }

    @Override
    public void i2CModeChanged(DeviceI2CMode mode) {
        publishStatus(getContract().STATUS_I2CMODE, mode);
    }

    @Override
    public void temperature(short i) {
        publishEvent(getContract().EVENT_TEMPERATURE, i);
    }

    @Override
    public void temperatureReached(short i) {
        publishEvent(getContract().EVENT_TEMPERATURE_REACHED, i);
    }

}
