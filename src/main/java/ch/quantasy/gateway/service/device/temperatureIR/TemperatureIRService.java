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
package ch.quantasy.gateway.service.device.temperatureIR;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.temperatureIR.DeviceAmbientTemperatureCallbackThreshold;
import ch.quantasy.tinkerforge.device.temperatureIR.DeviceObjectTemperatureCallbackThreshold;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDevice;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class TemperatureIRService extends AbstractDeviceService<TemperatureIRDevice, TemperatureIRServiceContract> implements TemperatureIRDeviceCallback {

    public TemperatureIRService(TemperatureIRDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new TemperatureIRServiceContract(device));
        addDescription(getContract().INTENT_AMBIENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_OBJECT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_AMBIENT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-400..1250]\n max: [-400..1250]");
        addDescription(getContract().INTENT_OBJECT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-700..3800]\n max: [-700..3800]");
        addDescription(getContract().EVENT_AMBIENT_TEMPERATURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-400..1250]\n");
        addDescription(getContract().EVENT_OBJECT_TEMPERATURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-700..3800]\n");
        addDescription(getContract().EVENT_AMBIENT_TEMPERATURE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-400..1250]\n");
        addDescription(getContract().EVENT_OBJECT_TEMPERATURE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-700..3800]\n");
        addDescription(getContract().STATUS_AMBIENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_OBJECT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_AMBIENT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-400..1250]\n max: [-400..1250]");
        addDescription(getContract().STATUS_OBJECT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-700..3800]\n max: [-700..3800]");
        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_AMBIENT_TEMPERATURE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAmbientTemperatureCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_OBJECT_TEMPERATURE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setObjectTemperatureCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_AMBIENT_TEMPERATURE_THRESHOLD)) {
            DeviceAmbientTemperatureCallbackThreshold threshold = getMapper().readValue(payload, DeviceAmbientTemperatureCallbackThreshold.class);
            getDevice().setAmbientTemperatureThreshold(threshold);
        }
        if (string.startsWith(getContract().INTENT_OBJECT_TEMPERATURE_THRESHOLD)) {
            DeviceObjectTemperatureCallbackThreshold threshold = getMapper().readValue(payload, DeviceObjectTemperatureCallbackThreshold.class);
            getDevice().setObjectTemperatureThreshold(threshold);

        }
    }

    @Override
    public void ambientTemperature(short s) {
        addEvent(getContract().EVENT_AMBIENT_TEMPERATURE, new AmbientTemperatureEvent(s));
    }

    @Override
    public void ambientTemperatureReached(short s) {
        addEvent(getContract().EVENT_AMBIENT_TEMPERATURE_REACHED, new AmbientTemperatureEvent(s));
    }

    @Override
    public void objectTemperature(short s) {
        addEvent(getContract().EVENT_OBJECT_TEMPERATURE, new ObjectTemperatureEvent(s));
    }

    @Override
    public void objectTemperatureReached(short s) {
        addEvent(getContract().EVENT_OBJECT_TEMPERATURE_REACHED, new ObjectTemperatureEvent(s));
    }

    @Override
    public void ambientTemperatureCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_AMBIENT_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void objectTemperatureCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_OBJECT_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void ambientTemperatureCallbackThresholdChanged(DeviceAmbientTemperatureCallbackThreshold threshold) {
        addStatus(getContract().STATUS_AMBIENT_TEMPERATURE_THRESHOLD, threshold);
    }

    @Override
    public void objectTemperatureCallbackThresholdChanged(DeviceObjectTemperatureCallbackThreshold threshold) {
        addStatus(getContract().STATUS_OBJECT_TEMPERATURE_THRESHOLD, threshold);

    }

    public static class AmbientTemperatureEvent {

        protected long timestamp;
        protected short value;

        public AmbientTemperatureEvent(short value) {
            this(value, System.currentTimeMillis());
        }

        public AmbientTemperatureEvent(short value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getValue() {
            return value;
        }

    }

    public static class ObjectTemperatureEvent {

        long timestamp;
        short value;

        public ObjectTemperatureEvent(short value) {
            this(value, System.currentTimeMillis());
        }

        public ObjectTemperatureEvent(short value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getHumidity() {
            return value;
        }

    }

}
