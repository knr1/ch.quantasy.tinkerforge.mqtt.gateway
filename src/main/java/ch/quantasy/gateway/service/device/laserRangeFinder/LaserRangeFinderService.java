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
package ch.quantasy.gateway.service.device.laserRangeFinder;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.laserRangeFinder.DeviceAveraging;
import ch.quantasy.tinkerforge.device.laserRangeFinder.DeviceDistanceCallbackThreshold;
import ch.quantasy.tinkerforge.device.laserRangeFinder.DeviceMode;
import ch.quantasy.tinkerforge.device.laserRangeFinder.DeviceVelocityCallbackThreshold;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDevice;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDeviceCallback;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class LaserRangeFinderService extends AbstractDeviceService<LaserRangeFinderDevice, LaserRangeFinderServiceContract> implements LaserRangeFinderDeviceCallback {

    public LaserRangeFinderService(LaserRangeFinderDevice device, URI mqttURI) throws MqttException {
        super(device, new LaserRangeFinderServiceContract(device), mqttURI);
        addDescription(getServiceContract().INTENT_DISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4000]\n max: [0..4000]");
        addDescription(getServiceContract().INTENT_VELOCITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [-127..127]\n max: [-127..127]");
        addDescription(getServiceContract().INTENT_DEVICE_MODE, "mode: [distance|velocity_12_7|velocity_31_75|velocity_63_5|velocity_127]");
        addDescription(getServiceContract().INTENT_LASER, "[true|false]");
        addDescription(getServiceContract().INTENT_MOVING_AVERAGE, "averagingDistance:[0..30]\n averagingVelocity:[0..30]");

        addDescription(getServiceContract().EVENT_DISTANCE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4000]\n");
        addDescription(getServiceContract().EVENT_VELOCITY, "timestamp: [-127.." + Long.MAX_VALUE + "]\n value: [0..127]\n");
        addDescription(getServiceContract().EVENT_DISTANCE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4000]\n");
        addDescription(getServiceContract().EVENT_VELOCITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-127..127]\n");
        addDescription(getServiceContract().STATUS_DISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4000]\n max: [0..4000]");
        addDescription(getServiceContract().STATUS_VELOCITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [-127..-127]\n max: [-127..127]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DEVICE_MODE, "mode: [distance|velocity_12_7|velocity_31_75|velocity_63_5|velocity_127]");
        addDescription(getServiceContract().STATUS_LASER, "[true|false]");
        addDescription(getServiceContract().STATUS_MOVING_AVERAGE, "averagingDistance:[0..30]\n averagingVelocity:[0..30]");

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;
        }
        try {
            if (string.startsWith(getServiceContract().INTENT_DEBOUNCE_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setDebouncePeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_DISTANCE_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setDistanceCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_VELOCITY_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setVelocityCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_DISTANCE_THRESHOLD)) {
                DeviceDistanceCallbackThreshold threshold = getMapper().readValue(payload, DeviceDistanceCallbackThreshold.class);
                getDevice().setDistanceCallbackThreshold(threshold);
            }
            if (string.startsWith(getServiceContract().INTENT_VELOCITY_THRESHOLD)) {
                DeviceVelocityCallbackThreshold threshold = getMapper().readValue(payload, DeviceVelocityCallbackThreshold.class);
                getDevice().setVelocityCallbackThreshold(threshold);
            }
            if (string.startsWith(getServiceContract().INTENT_LASER)) {
                Boolean laserEnabled = getMapper().readValue(payload, Boolean.class);
                getDevice().setLaser(laserEnabled);
            }
            if (string.startsWith(getServiceContract().INTENT_MOVING_AVERAGE)) {
                DeviceAveraging movingAverage = getMapper().readValue(payload, DeviceAveraging.class);
                getDevice().setMovingAverage(movingAverage);
            }
            if (string.startsWith(getServiceContract().INTENT_DEVICE_MODE)) {
                DeviceMode deviceMode = getMapper().readValue(payload, DeviceMode.class);
                getDevice().setMode(deviceMode);
            }

        } catch (Exception ex) {
            Logger.getLogger(LaserRangeFinderService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    @Override
    public void distance(int i) {
        addEvent(getServiceContract().EVENT_DISTANCE, new DistanceEvent(i));
    }

    @Override
    public void distanceReached(int i) {
        addEvent(getServiceContract().EVENT_DISTANCE_REACHED, new DistanceEvent(i));
    }

    @Override
    public void velocity(short i) {
        addEvent(getServiceContract().EVENT_VELOCITY, new VelocityEvent(i));
    }

    @Override
    public void velocityReached(short i) {
        addEvent(getServiceContract().EVENT_VELOCITY_REACHED, new VelocityEvent(i));
    }

    @Override
    public void distanceCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DISTANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void velocityCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_VELOCITY_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void distanceCallbackThresholdChanged(DeviceDistanceCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_DISTANCE_THRESHOLD, threshold);
    }

    @Override
    public void velocityCallbackThresholdChanged(DeviceVelocityCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_VELOCITY_THRESHOLD, threshold);
    }

    @Override
    public void laserStatusChanged(boolean laserEnabled) {
        addStatus(getServiceContract().STATUS_LASER, laserEnabled);
    }

    @Override
    public void movingAverageChanged(DeviceAveraging averaging) {
        addStatus(getServiceContract().STATUS_MOVING_AVERAGE, averaging);
    }

    @Override
    public void deviceModeChanged(DeviceMode deviceMode) {
        addStatus(getServiceContract().STATUS_DEVICE_MODE, deviceMode);
    }

    public static class DistanceEvent {

        protected long timestamp;
        protected int value;

        public DistanceEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public DistanceEvent(int value, long timeStamp) {
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

    public static class VelocityEvent {

        long timestamp;
        int value;

        public VelocityEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public VelocityEvent(int value, long timeStamp) {
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
