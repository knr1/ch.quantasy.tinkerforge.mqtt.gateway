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
package ch.quantasy.gateway.service.device.barometer;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.barometer.DeviceAirPressureCallbackThreshold;
import ch.quantasy.tinkerforge.device.barometer.DeviceAltitudeCallbackThreshold;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import ch.quantasy.tinkerforge.device.barometer.BarometerDeviceCallback;
import ch.quantasy.tinkerforge.device.barometer.DeviceAveraging;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class BarometerService extends AbstractDeviceService<BarometerDevice, BarometerServiceContract> implements BarometerDeviceCallback {

    public BarometerService(BarometerDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new BarometerServiceContract(device));
        publishDescription(getContract().INTENT_AIR_PRESSURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_AIR_PRESSURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [10000..1200000]\n max: [10000..1200000]");
        publishDescription(getContract().INTENT_ALTITUDE_THRESHOLD, "option: [x|o|i|<|>]\n min: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n max: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_AVERAGING, "averagingPressure: [0..10]\n averagingTemperature: [0..255]\n movingAveragePressure: [0..25]");
        publishDescription(getContract().INTENT_REFERENCE_AIR_PRESSURE, "[" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        publishDescription(getContract().EVENT_AIR_PRESSURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [10000..1200000]\n");
        publishDescription(getContract().EVENT_ALTITUDE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        publishDescription(getContract().EVENT_AIR_PRESSURE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [10000..1200000]\n");
        publishDescription(getContract().EVENT_ALTITUDE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        publishDescription(getContract().STATUS_AIR_PRESSURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_AIR_PRESSURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [10000..1200000]\n max: [10000..1200000]");
        publishDescription(getContract().STATUS_ALTITUDE_THRESHOLD, "option: [x|o|i|<|>]\n min: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n max: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_AVERAGING, "averagingPressure: [0..10]\n averagingTemperature: [0..255]\n movingAveragePressure: [0..25]");
        publishDescription(getContract().STATUS_REFERENCE_AIR_PRESSURE, "[" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_AIR_PRESSURE_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAirPressureCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ALTITUDE_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAltitudeCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_AIR_PRESSURE_THRESHOLD)) {

            DeviceAirPressureCallbackThreshold threshold = getMapper().readValue(payload, DeviceAirPressureCallbackThreshold.class);
            getDevice().setAirPressureThreshold(threshold);
        }
        if (string.startsWith(getContract().INTENT_ALTITUDE_THRESHOLD)) {

            DeviceAltitudeCallbackThreshold threshold = getMapper().readValue(payload, DeviceAltitudeCallbackThreshold.class);
            getDevice().setAltitudeCallbackThreshold(threshold);

        }
        if (string.startsWith(getContract().INTENT_AVERAGING)) {

            DeviceAveraging averaging = getMapper().readValue(payload, DeviceAveraging.class);
            getDevice().setAveraging(averaging);

        }
        if (string.startsWith(getContract().INTENT_REFERENCE_AIR_PRESSURE)) {
            Integer reference = getMapper().readValue(payload, Integer.class);
            getDevice().setReferenceAirPressure(reference);

        }

    }

    @Override
    public void airPressure(int i) {

        publishEvent(getContract().EVENT_AIR_PRESSURE, i);
    }

    @Override
    public void airPressureReached(int i) {
        publishEvent(getContract().EVENT_AIR_PRESSURE_REACHED, i);
    }

    @Override
    public void altitude(int i) {
        publishEvent(getContract().EVENT_ALTITUDE, i);
    }

    @Override
    public void altitudeReached(int i) {
        publishEvent(getContract().EVENT_ALTITUDE_REACHED, i);
    }

    @Override
    public void airPressureCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_AIR_PRESSURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void altitudeCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_ALTITUDE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void airPressureCallbackThresholdChanged(DeviceAirPressureCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_AIR_PRESSURE_THRESHOLD, threshold);
    }

    @Override
    public void altitudeCallbackThresholdChanged(DeviceAltitudeCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_ALTITUDE_THRESHOLD, threshold);
    }

    @Override
    public void averagingChanged(DeviceAveraging averaging) {
        publishStatus(getContract().STATUS_AVERAGING, averaging);
    }

    @Override
    public void referenceAirPressureChanged(Integer referenceAirPressure) {
        publishStatus(getContract().STATUS_REFERENCE_AIR_PRESSURE, referenceAirPressure);
    }

    
    
}
