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
package ch.quantasy.gateway.service.device.ptc;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.ptc.DeviceNoiseReductionFilter;
import ch.quantasy.tinkerforge.device.ptc.DeviceResistanceCallbackThreshold;
import ch.quantasy.tinkerforge.device.ptc.DeviceTemperatureCallbackThreshold;
import ch.quantasy.tinkerforge.device.ptc.PTCDevice;
import ch.quantasy.tinkerforge.device.ptc.PTCDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class PTCService extends AbstractDeviceService<PTCDevice, PTCServiceContract> implements PTCDeviceCallback {

    public PTCService(PTCDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new PTCServiceContract(device));
        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-24600..84900]\n max: [-24600..84900]");
        addDescription(getContract().INTENT_RESISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_RESISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0.." + Short.MAX_VALUE + "]\n max: [0.." + Short.MAX_VALUE + "]");
        addDescription(getContract().INTENT_NOISE_REDUCTION_FILTER, "filter: [Hz_50|Hz_60]");
        addDescription(getContract().INTENT_WIRE_MODE, "[2|3|4]");
        addDescription(getContract().EVENT_TEMPERATURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-24600..84900]");
        addDescription(getContract().EVENT_TEMPERATURE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-24600..84900]");
        addDescription(getContract().EVENT_RESISTANCE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Short.MAX_VALUE + "]");
        addDescription(getContract().EVENT_RESISTANCE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Short.MAX_VALUE + "]");
        addDescription(getContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-24600..84900]\n max: [-24600..84900]");
        addDescription(getContract().STATUS_RESISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_RESISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0.." + Short.MAX_VALUE + "]\n max: [0.." + Short.MAX_VALUE + "]");
        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_NOISE_REDUCTION_FILTER, "filter: [Hz_50|Hz_60]");
        addDescription(getContract().STATUS_WIRE_MODE, "[2|3|4]");
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
        if (string.startsWith(getContract().INTENT_RESISTANCE_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setResistanceCallbackPeriod(period);
        }

        if (string.startsWith(getContract().INTENT_RESISTANCE_THRESHOLD)) {

            DeviceResistanceCallbackThreshold threshold = getMapper().readValue(payload, DeviceResistanceCallbackThreshold.class);
            getDevice().setResistanceCallbackThreshold(threshold);
        }

        if (string.startsWith(getContract().INTENT_NOISE_REDUCTION_FILTER)) {
            DeviceNoiseReductionFilter filter = getMapper().readValue(payload, DeviceNoiseReductionFilter.class);
            getDevice().setNoiseReductionFilter(filter);
        }
        if (string.startsWith(getContract().INTENT_WIRE_MODE)) {
            Short wireMode = getMapper().readValue(payload, Short.class);
            getDevice().setWireMode(wireMode);
        }
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void temperatureCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void temperatureCallbackThresholdChanged(DeviceTemperatureCallbackThreshold threshold) {
        addStatus(getContract().STATUS_TEMPERATURE_THRESHOLD, threshold);
    }

    @Override
    public void noiseReductionFilterChanged(DeviceNoiseReductionFilter filter) {
        addStatus(getContract().STATUS_NOISE_REDUCTION_FILTER, filter);
    }

    @Override
    public void temperature(int i) {
        addEvent(getContract().EVENT_TEMPERATURE, new TemperatureEvent(i));
    }

    @Override
    public void temperatureReached(int i) {
        addEvent(getContract().EVENT_TEMPERATURE_REACHED, new TemperatureEvent(i));
    }

    @Override
    public void resistance(int i) {
        addEvent(getContract().EVENT_RESISTANCE, new ResistanceEvent(i));
    }

    @Override
    public void resistanceReached(int i) {
        addEvent(getContract().EVENT_RESISTANCE_REACHED, new ResistanceEvent(i));
    }

    @Override
    public void resistanceCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_RESISTANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void resistanceCallbackThresholdChanged(DeviceResistanceCallbackThreshold threshold) {
        addStatus(getContract().STATUS_RESISTANCE_THRESHOLD, threshold);
    }

    @Override
    public void wireModeChanged(short wireMode) {
        addStatus(getContract().STATUS_WIRE_MODE, wireMode);
    }

    public static class TemperatureEvent {

        protected long timestamp;
        protected long value;

        public TemperatureEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public TemperatureEvent(long value, long timeStamp) {
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

    public static class ResistanceEvent {

        protected long timestamp;
        protected long value;

        public ResistanceEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public ResistanceEvent(long value, long timeStamp) {
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
