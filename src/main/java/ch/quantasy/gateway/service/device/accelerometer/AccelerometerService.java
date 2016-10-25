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
package ch.quantasy.gateway.service.device.accelerometer;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.accelerometer.AccelerometerDevice;
import ch.quantasy.tinkerforge.device.accelerometer.AccelerometerDeviceCallback;
import ch.quantasy.tinkerforge.device.accelerometer.DeviceAccelerationCallbackThreshold;
import ch.quantasy.tinkerforge.device.accelerometer.DeviceConfiguration;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class AccelerometerService extends AbstractDeviceService<AccelerometerDevice, AccelerometerServiceContract> implements AccelerometerDeviceCallback {

    public AccelerometerService(AccelerometerDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new AccelerometerServiceContract(device));
        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_ACCELERATION_THRESHOLD, "option: [x|o|i|<|>]\n minX: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n minY: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n minZ: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n maxX: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n maxY: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n maxZ: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getContract().INTENT_CONFIGURATION, "dataRate: [OFF|Hz3|Hz6|Hz12|Hz25|Hz50|Hz100|Hz400|Hz800|Hz1600]\n fullScale: [G2|G4|G6|G8|G16\n filterBandwidth: [Hz800|Hz400|Hz200|Hz50]");

        addDescription(getContract().EVENT_ACCELERATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n");
        addDescription(getContract().EVENT_ACCELERATION_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");

        addDescription(getContract().STATUS_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_ACCELERATION_THRESHOLD, "option: [x|o|i|<|>]\n minX: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n minY: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n minZ: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n maxX: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n maxY: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n maxZ: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_CONFIGURATION, "dataRate: [OFF|Hz3|Hz6|Hz12|Hz25|Hz50|Hz100|Hz400|Hz800|Hz1600]\n fullScale: [G2|G4|G6|G8|G16\n filterBandwidth: [Hz800|Hz400|Hz200|Hz50]");
    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ACCELERATION_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAccelerationCallbackPeriod(period);
        }

        if (string.startsWith(getContract().INTENT_ACCELERATION_THRESHOLD)) {

            DeviceAccelerationCallbackThreshold threshold = getMapper().readValue(payload, DeviceAccelerationCallbackThreshold.class);
            getDevice().setAccelerationCallbackThreshold(threshold);
        }

        if (string.startsWith(getContract().INTENT_CONFIGURATION)) {

            DeviceConfiguration configuration = getMapper().readValue(payload, DeviceConfiguration.class);
            getDevice().setConfiguration(configuration);
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void accelerationCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_ACCELERATION_CALLBACK_PERIOD, period);
    }

    @Override
    public void accelerationCallbackThresholdChanged(DeviceAccelerationCallbackThreshold threshold) {
        addStatus(getContract().STATUS_ACCELERATION_THRESHOLD, threshold);
    }

    @Override
    public void configurationChanged(DeviceConfiguration configuration) {
        addStatus(getContract().STATUS_CONFIGURATION, configuration);
    }

    @Override
    public void acceleration(short x, short y, short z) {
        addEvent(getContract().EVENT_ACCELERATION, new AccelerationEvent(x, y, z));
    }

    @Override
    public void accelerationReached(short x, short y, short z) {
        addEvent(getContract().EVENT_ACCELERATION_REACHED, new AccelerationEvent(x, y, z));
    }

    public static class AccelerationEvent {

        protected long timestamp;
        protected short x;
        protected short y;
        protected short z;

        public AccelerationEvent(short x, short y, short z) {
            this(x, y, z, System.currentTimeMillis());
        }

        public AccelerationEvent(short x, short y, short z, long timeStamp) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getX() {
            return x;
        }

        public short getY() {
            return y;
        }

        public short getZ() {
            return z;
        }

    }

}
