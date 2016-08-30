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
package ch.quantasy.gateway.service.device.uvLight;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.uvLight.DeviceUVLightCallbackThreshold;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDevice;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class UVLightService extends AbstractDeviceService<UVLightDevice, UVLightServiceContract> implements UVLightDeviceCallback {

    public UVLightService(UVLightDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new UVLightServiceContract(device));
        addDescription(getServiceContract().INTENT_UV_LIGHT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_UV_LIGHT_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..328000]\n max: [0..328000]");

        addDescription(getServiceContract().EVENT_UV_LIGHT, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..328000]\n");
        addDescription(getServiceContract().EVENT_UV_LIGHT_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..328000]\n");
        addDescription(getServiceContract().STATUS_UV_LIGHT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_UV_LIGHT_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..328000]\n max: [0..328000]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_UV_LIGHT_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setUVLightCallbackPeriod(period);
        }

        if (string.startsWith(getServiceContract().INTENT_UV_LIGHT_THRESHOLD)) {

            DeviceUVLightCallbackThreshold threshold = getMapper().readValue(payload, DeviceUVLightCallbackThreshold.class);
            getDevice().setUVLightCallbackThreshold(threshold);
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void uvLightCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_UV_LIGHT_CALLBACK_PERIOD, period);
    }

    @Override
    public void uvLightCallbackThresholdChanged(DeviceUVLightCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_UV_LIGHT_THRESHOLD, threshold);
    }

    @Override
    public void uvLight(long i) {
        addEvent(getServiceContract().EVENT_UV_LIGHT, new UVLightEvent(i));
    }

    @Override
    public void uvLightReached(long i) {
        addEvent(getServiceContract().EVENT_UV_LIGHT_REACHED, new UVLightEvent(i));
    }

    public static class UVLightEvent {

        protected long timestamp;
        protected long value;

        public UVLightEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public UVLightEvent(long value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getValue() {
            return value;
        }

    }

}
