/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service.device.dustDetector;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dustDetector.DeviceDustDensityCallbackThreshold;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDevice;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class DustDetectorService extends AbstractDeviceService<DustDetectorDevice, DustDetectorServiceContract> implements DustDetectorDeviceCallback {

    public DustDetectorService(DustDetectorDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new DustDetectorServiceContract(device));
        addDescription(getServiceContract().INTENT_DUST_DENSITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_MOVING_AVERAGE, "[0..100]");

        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DUST_DENSITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..500]\n max: [0..500]");

        addDescription(getServiceContract().EVENT_DUST_DENSITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..500]\n");
        addDescription(getServiceContract().EVENT_DUST_DENSITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..500]\n");
        addDescription(getServiceContract().STATUS_DUST_DENSITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DUST_DENSITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..500]\n max: [0..500]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_MOVING_AVERAGE, "[0..100]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_MOVING_AVERAGE)) {

            Short average = getMapper().readValue(payload, Short.class);
            getDevice().setMovingAverage(average);
        }
        if (string.startsWith(getServiceContract().INTENT_DUST_DENSITY_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDustDensityCallbackPeriod(period);
        }

        if (string.startsWith(getServiceContract().INTENT_DUST_DENSITY_THRESHOLD)) {

            DeviceDustDensityCallbackThreshold threshold = getMapper().readValue(payload, DeviceDustDensityCallbackThreshold.class);
            getDevice().setDustDensityCallbackThreshold(threshold);
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void movingAverageChanged(short movingAverage) {
        addStatus(getServiceContract().STATUS_MOVING_AVERAGE, movingAverage);
    }

    @Override
    public void dustDensityCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DUST_DENSITY_CALLBACK_PERIOD, period);
    }

    @Override
    public void dustDensityCallbackThresholdChanged(DeviceDustDensityCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_DUST_DENSITY_THRESHOLD, threshold);
    }

    @Override
    public void dustDensity(int i) {
        addEvent(getServiceContract().EVENT_DUST_DENSITY, new DustDensityEvent(i));
    }

    @Override
    public void dustDensityReached(int i) {
        addEvent(getServiceContract().EVENT_DUST_DENSITY_REACHED, new DustDensityEvent(i));
    }

    public static class DustDensityEvent {

        protected long timestamp;
        protected long value;

        public DustDensityEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public DustDensityEvent(long value, long timeStamp) {
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
