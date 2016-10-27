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
package ch.quantasy.gateway.service.device.loadCell;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.loadCell.DeviceConfiguration;
import ch.quantasy.tinkerforge.device.loadCell.DeviceWeightCallbackThreshold;
import ch.quantasy.tinkerforge.device.loadCell.LoadCellDevice;
import ch.quantasy.tinkerforge.device.loadCell.LoadCellDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class LoadCellService extends AbstractDeviceService<LoadCellDevice, LoadCellServiceContract> implements LoadCellDeviceCallback {

    public LoadCellService(LoadCellDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new LoadCellServiceContract(device));
        addDescription(getContract().INTENT_TARE, "[true|false]");

        addDescription(getContract().INTENT_MOVING_AVERAGE, "[1..40]");
        addDescription(getContract().STATUS_MOVING_AVERAGE, "[1..40]");

        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_WEIGHT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_WEIGHT_THRESHOLD, "option: [x|o|i|<|>]\n min: [-50001..50001]\n max: [-50001..50001]");
        addDescription(getContract().INTENT_CONFIGURATION, "gain:[gain128X|gain64X|gain32X]\n rate: [rate10Hz|rate80Hz]");
        addDescription(getContract().INTENT_LED, "true|false]");

        addDescription(getContract().EVENT_WEIGHT, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-50001..50001]\n");
        addDescription(getContract().EVENT_WEIGHT_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-50001..50001]");
        addDescription(getContract().STATUS_WEIGHT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_WEIGHT_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..50001]\n max: [-50001..50001]");
        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_CONFIGURATION, "gain:[gain128X|gain64X|gain32X]\n rate: [rate10Hz|rate80Hz]");
        addDescription(getContract().STATUS_LED, "[true|false]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_WEIGHT_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setWeightCallbackPeriod(period);
        }

        if (string.startsWith(getContract().INTENT_WEIGHT_THRESHOLD)) {

            DeviceWeightCallbackThreshold threshold = getMapper().readValue(payload, DeviceWeightCallbackThreshold.class);
            getDevice().setWeightCallbackThreshold(threshold);
        }

        if (string.startsWith(getContract().INTENT_CONFIGURATION)) {
            DeviceConfiguration configuration = getMapper().readValue(payload, DeviceConfiguration.class);
            getDevice().setConfiguration(configuration);
        }
        if (string.startsWith(getContract().INTENT_MOVING_AVERAGE)) {
            Short average = getMapper().readValue(payload, Short.class);
            getDevice().setMovingAverage(average);
        }
        if (string.startsWith(getContract().INTENT_TARE)) {
            Boolean tare = getMapper().readValue(payload, Boolean.class);
            getDevice().tare(tare);
        }
        if (string.startsWith(getContract().INTENT_LED)) {
            Boolean LED = getMapper().readValue(payload, Boolean.class);
            getDevice().setLED(LED);
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void weightCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_WEIGHT_CALLBACK_PERIOD, period);
    }

    @Override
    public void weightCallbackThresholdChanged(DeviceWeightCallbackThreshold threshold) {
        addStatus(getContract().STATUS_WEIGHT_THRESHOLD, threshold);
    }

    @Override
    public void configurationChanged(DeviceConfiguration configuration) {
        addStatus(getContract().STATUS_CONFIGURATION, configuration);
    }

    @Override
    public void movingAverageChanged(short average) {
        addStatus(getContract().STATUS_MOVING_AVERAGE, average);
    }

    @Override
    public void statusLEDChanged(boolean led) {
        addStatus(getContract().STATUS_LED, led);
    }

    @Override
    public void weight(int i) {
        addEvent(getContract().EVENT_WEIGHT, new WeightEvent(i));
    }

    @Override
    public void weightReached(int i) {
        addEvent(getContract().EVENT_WEIGHT_REACHED, new WeightEvent(i));
    }

    public static class WeightEvent {

        protected long timestamp;
        protected int value;

        public WeightEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public WeightEvent(int value, long timeStamp) {
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
