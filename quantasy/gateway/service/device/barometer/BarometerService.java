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
package ch.quantasy.gateway.service.device.barometer;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.barometer.DeviceAirPressureCallbackThreshold;
import ch.quantasy.tinkerforge.device.barometer.DeviceAltitudeCallbackThreshold;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import ch.quantasy.tinkerforge.device.barometer.BarometerDeviceCallback;
import ch.quantasy.tinkerforge.device.barometer.DeviceAveraging;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class BarometerService extends AbstractDeviceService<BarometerDevice, BarometerServiceContract> implements BarometerDeviceCallback {

    public BarometerService(BarometerDevice device) throws MqttException {
        super(device, new BarometerServiceContract(device));
        addDescription(getServiceContract().INTENT_AIR_PRESSURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_AIR_PRESSURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [10000..1200000]\n max: [10000..1200000]");
        addDescription(getServiceContract().INTENT_ALTITUDE_THRESHOLD, "option: [x|o|i|<|>]\n min: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n max: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_AVERAGING, "averagingPressure: [0..10]\n averagingTemperature: [0..255]\n movingAveragePressure: [0..25]");
        addDescription(getServiceContract().INTENT_REFERENCE_AIR_PRESSURE, "[" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        addDescription(getServiceContract().EVENT_AIR_PRESSURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [10000..1200000]\n");
        addDescription(getServiceContract().EVENT_ALTITUDE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        addDescription(getServiceContract().EVENT_AIR_PRESSURE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [10000..1200000]\n");
        addDescription(getServiceContract().EVENT_ALTITUDE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        addDescription(getServiceContract().STATUS_AIR_PRESSURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_AIR_PRESSURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [10000..1200000]\n max: [10000..1200000]");
        addDescription(getServiceContract().STATUS_ALTITUDE_THRESHOLD, "option: [x|o|i|<|>]\n min: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n max: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_AVERAGING, "averagingPressure: [0..10]\n averagingTemperature: [0..255]\n movingAveragePressure: [0..25]");
        addDescription(getServiceContract().STATUS_REFERENCE_AIR_PRESSURE, "[" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;

        }
        try {
            if (string.startsWith(getServiceContract().INTENT_DEBOUNCE_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setDebouncePeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_AIR_PRESSURE_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setAirPressureCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_ALTITUDE_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setAltitudeCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_AIR_PRESSURE_THRESHOLD)) {

                DeviceAirPressureCallbackThreshold threshold = getMapper().readValue(payload, DeviceAirPressureCallbackThreshold.class);
                getDevice().setAirPressureThreshold(threshold);
            }
            if (string.startsWith(getServiceContract().INTENT_ALTITUDE_THRESHOLD)) {

                DeviceAltitudeCallbackThreshold threshold = getMapper().readValue(payload, DeviceAltitudeCallbackThreshold.class);
                getDevice().setAltitudeCallbackThreshold(threshold);

            }
            if (string.startsWith(getServiceContract().INTENT_AVERAGING)) {

                DeviceAveraging averaging = getMapper().readValue(payload, DeviceAveraging.class);
                getDevice().setAveraging(averaging);

            }
            if (string.startsWith(getServiceContract().INTENT_REFERENCE_AIR_PRESSURE)) {
                Integer reference = getMapper().readValue(payload, Integer.class);
                getDevice().setReferenceAirPressure(reference);

            }

        } catch (IOException ex) {
            Logger.getLogger(BarometerService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void airPressure(int i) {

        addEvent(getServiceContract().EVENT_AIR_PRESSURE, new AirPressureEvent(i));
    }

    @Override
    public void airPressureReached(int i) {
        addEvent(getServiceContract().EVENT_AIR_PRESSURE_REACHED, new AirPressureEvent(i));
    }

    @Override
    public void altitude(int i) {
        addEvent(getServiceContract().EVENT_ALTITUDE, new AltitudeEvent(i));
    }

    @Override
    public void altitudeReached(int i) {
        addEvent(getServiceContract().EVENT_ALTITUDE_REACHED, new AltitudeEvent(i));
    }

    @Override
    public void airPressureCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_AIR_PRESSURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void altitudeCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_ALTITUDE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void airPressureCallbackThresholdChanged(DeviceAirPressureCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_AIR_PRESSURE_THRESHOLD, threshold);
    }

    @Override
    public void altitudeCallbackThresholdChanged(DeviceAltitudeCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_ALTITUDE_THRESHOLD, threshold);
    }

    @Override
    public void averagingChanged(DeviceAveraging averaging) {
        addStatus(getServiceContract().STATUS_AVERAGING, averaging);
    }

    @Override
    public void referenceAirPressureChanged(Integer referenceAirPressure) {
        addStatus(getServiceContract().STATUS_REFERENCE_AIR_PRESSURE, referenceAirPressure);
    }

    class AirPressureEvent {

        protected long timestamp;
        protected int airPressure;

        public AirPressureEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public AirPressureEvent(int value, long timeStamp) {
            this.airPressure = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getAirPressure() {
            return airPressure;
        }

    }

    class AltitudeEvent {

        long timestamp;
        int altitude;

        public AltitudeEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public AltitudeEvent(int value, long timeStamp) {
            this.altitude = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getAltitude() {
            return altitude;
        }

    }

}
