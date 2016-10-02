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
package ch.quantasy.gateway.service.device.rotaryEncoder;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.rotaryEncoder.DeviceCountCallbackThreshold;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDevice;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDeviceCallback;

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class RotaryEncoderService extends AbstractDeviceService<RotaryEncoderDevice, RotaryEncoderServiceContract> implements RotaryEncoderDeviceCallback {

    public RotaryEncoderService(RotaryEncoderDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new RotaryEncoderServiceContract(device));
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_COUNT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_COUNT_THRESHOLD, "option: [x|o|i|<|>]\n min: [-150..150]\n max: [-150..150]");
        addDescription(getServiceContract().EVENT_PRESSED, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_RELEASED, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_COUNT_RESET, "timestamp: [0.." + Long.MAX_VALUE + "]\n count: [" + Long.MIN_VALUE + "0.." + Long.MAX_VALUE + "]\n");

        addDescription(getServiceContract().EVENT_COUNT, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..9000]\n");
        addDescription(getServiceContract().EVENT_COUNT_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-150..150]\n");
        addDescription(getServiceContract().STATUS_COUNT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_COUNT_THRESHOLD, "option: [x|o|i|<|>]\n min: [-150..150]\n max: [-150..150]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_DEBOUNCE_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }

        if (string.startsWith(getServiceContract().INTENT_COUNT_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setCountCallbackPeriod(period);
        }

        if (string.startsWith(getServiceContract().INTENT_COUNT_THRESHOLD)) {
            DeviceCountCallbackThreshold threshold = getMapper().readValue(payload, DeviceCountCallbackThreshold.class);
            getDevice().setPositionCallbackThreshold(threshold);

        }
        if (string.startsWith(getServiceContract().INTENT_COUNT_RESET)) {

            Boolean reset = getMapper().readValue(payload, Boolean.class);
            getDevice().setCountReset(reset);
        }

    }

    @Override
    public void count(int i) {
        addEvent(getServiceContract().EVENT_COUNT, new CountEvent(i));
    }

    @Override
    public void countReached(int i) {
        addEvent(getServiceContract().EVENT_COUNT_REACHED, new CountEvent(i));
    }

    @Override
    public void countCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_COUNT_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void countCallbackThresholdChanged(DeviceCountCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_COUNT_THRESHOLD, threshold);

    }

    @Override
    public void countReset(long latestCount) {
        addEvent(getServiceContract().EVENT_COUNT_RESET, new CountResetEvent(latestCount));
    }

    @Override
    public void pressed() {
        addEvent(getServiceContract().EVENT_PRESSED, System.currentTimeMillis());
    }

    @Override
    public void released() {
        addEvent(getServiceContract().EVENT_RELEASED, System.currentTimeMillis());
    }

    public static class CountEvent {

        long timestamp;
        int value;

        public CountEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public CountEvent(int value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }
        
        private CountEvent(){
            
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getValue() {
            return value;
        }

    }

    public static class CountResetEvent {

        protected long timestamp;
        protected long count;

        public CountResetEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public CountResetEvent(long value, long timeStamp) {
            this.count = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getCount() {
            return count;
        }
    }

}
