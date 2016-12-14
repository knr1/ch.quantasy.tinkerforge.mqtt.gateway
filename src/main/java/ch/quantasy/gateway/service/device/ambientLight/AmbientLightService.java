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
package ch.quantasy.gateway.service.device.ambientLight;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.ambientLight.AmbientLightDevice;
import ch.quantasy.tinkerforge.device.ambientLight.AmbientLightDeviceCallback;
import ch.quantasy.tinkerforge.device.ambientLight.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.tinkerforge.device.ambientLight.DeviceIlluminanceCallbackThreshold;

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class AmbientLightService extends AbstractDeviceService<AmbientLightDevice, AmbientLightServiceContract> implements AmbientLightDeviceCallback {

    public AmbientLightService(AmbientLightDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new AmbientLightServiceContract(device));
        publishDescription(getContract().INTENT_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_IllUMINANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        publishDescription(getContract().INTENT_ILLUMINANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..9000]\n max: [0..9000]");
        publishDescription(getContract().EVENT_ANALOG_VALUE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        publishDescription(getContract().EVENT_ILLUMINANCE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..9000]\n");
        publishDescription(getContract().EVENT_ANALOG_VALUE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        publishDescription(getContract().EVENT_ILLUMINANCE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..9000]\n");
        publishDescription(getContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ILLUMINANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        publishDescription(getContract().STATUS_ILLUMINANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..9000]\n max: [0..9000]");
        publishDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ANALOG_VALUE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAnalogValueCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_IllUMINANCE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setIlluminanceCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ANALOG_VALUE_THRESHOLD)) {
            DeviceAnalogValueCallbackThreshold threshold = getMapper().readValue(payload, DeviceAnalogValueCallbackThreshold.class);
            getDevice().setAnalogValueThreshold(threshold);
        }
        if (string.startsWith(getContract().INTENT_ILLUMINANCE_THRESHOLD)) {
            DeviceIlluminanceCallbackThreshold threshold = getMapper().readValue(payload, DeviceIlluminanceCallbackThreshold.class);
            getDevice().setIlluminanceCallbackThreshold(threshold);

        }

    }

    @Override
    public void analogValue(int i) {
        publishEvent(getContract().EVENT_ANALOG_VALUE, i);
    }

    @Override
    public void analogValueReached(int i) {
        publishEvent(getContract().EVENT_ANALOG_VALUE_REACHED, i);
    }

    @Override
    public void illuminance(int i) {
        publishEvent(getContract().EVENT_ILLUMINANCE, i);
    }

    @Override
    public void illuminanceReached(int i) {
        publishEvent(getContract().EVENT_ILLUMINANCE_REACHED, i);
    }

    @Override
    public void analogValueCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, period);
    }

    @Override
    public void illuminanceCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_ILLUMINANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_ANALOG_VALUE_THRESHOLD, threshold);
    }

    @Override
    public void illuminanceCallbackThresholdChanged(DeviceIlluminanceCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_ILLUMINANCE_THRESHOLD, threshold);

    }

}
