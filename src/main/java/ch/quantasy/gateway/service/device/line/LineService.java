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
package ch.quantasy.gateway.service.device.line;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.line.DeviceReflectivityCallbackThreshold;
import ch.quantasy.tinkerforge.device.line.LineDevice;
import ch.quantasy.tinkerforge.device.line.LineDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class LineService extends AbstractDeviceService<LineDevice, LineServiceContract> implements LineDeviceCallback {

    public LineService(LineDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new LineServiceContract(device));
        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_REFLECTIVITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_REFLECTIVITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");

        addDescription(getContract().EVENT_REFLECTIVITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [[0..4095]\n");
        addDescription(getContract().EVENT_REFLECTIVITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        addDescription(getContract().STATUS_REFLECTIVITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_REFLECTIVITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_REFLECTIVITY_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setReflectivityCallbackPeriod(period);
        }

        if (string.startsWith(getContract().INTENT_REFLECTIVITY_THRESHOLD)) {

            DeviceReflectivityCallbackThreshold threshold = getMapper().readValue(payload, DeviceReflectivityCallbackThreshold.class);
            getDevice().setReflectivityCallbackThreshold(threshold);
        }
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void reflectivityCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_REFLECTIVITY_CALLBACK_PERIOD, period);
    }

    @Override
    public void reflectivityThresholdChanged(DeviceReflectivityCallbackThreshold threshold) {
        addStatus(getContract().STATUS_REFLECTIVITY_THRESHOLD, threshold);
    }

    @Override
    public void reflectivity(int i) {
        addEvent(getContract().EVENT_REFLECTIVITY, new ReflectivityEvent(i));
    }

    @Override
    public void reflectivityReached(int i) {
        addEvent(getContract().EVENT_REFLECTIVITY_REACHED, new ReflectivityEvent(i));
    }

    public static class ReflectivityEvent {

        protected long timestamp;
        protected int value;

        public ReflectivityEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public ReflectivityEvent(int value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getValue() {
            return value;
        }

    }

}
