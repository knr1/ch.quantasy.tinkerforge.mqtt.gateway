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
package ch.quantasy.gateway.service.device.analogInV2;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.analogInV2.AnalogInV2Device;
import ch.quantasy.tinkerforge.device.analogInV2.AnalogInV2DeviceCallback;
import ch.quantasy.tinkerforge.device.analogInV2.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.tinkerforge.device.analogInV2.DeviceVoltageCallbackThreshold;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class AnalogInV2Service extends AbstractDeviceService<AnalogInV2Device, AnalogInV2ServiceContract> implements AnalogInV2DeviceCallback {

    public AnalogInV2Service(AnalogInV2Device device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new AnalogInV2ServiceContract(device));
        addDescription(getContract().INTENT_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getContract().INTENT_VOLTAGE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..42000]\n max: [0..42000]");
        addDescription(getContract().INTENT_MOVING_AVERAGE, "[1..50]");

        addDescription(getContract().EVENT_ANALOG_VALUE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        addDescription(getContract().EVENT_VOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        addDescription(getContract().EVENT_ANALOG_VALUE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        addDescription(getContract().EVENT_VOLTAGE_REACHED, "timestamp: [0..42000]\n value: [0..42000]\n");
        addDescription(getContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getContract().STATUS_VOLTAGE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..42000]\n max: [0..42000]");
        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_MOVING_AVERAGE, "[1..50]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ANALOG_VALUE_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAnalogValueCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_VOLTAGE_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setVoltageCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ANALOG_VALUE_THRESHOLD)) {

            DeviceAnalogValueCallbackThreshold threshold = getMapper().readValue(payload, DeviceAnalogValueCallbackThreshold.class);
            getDevice().setAirPressureThreshold(threshold);
        }
        if (string.startsWith(getContract().INTENT_VOLTAGE_THRESHOLD)) {

            DeviceVoltageCallbackThreshold threshold = getMapper().readValue(payload, DeviceVoltageCallbackThreshold.class);
            getDevice().setVoltageCallbackThreshold(threshold);

        }
        if (string.startsWith(getContract().INTENT_MOVING_AVERAGE)) {

            Short movingAverage = getMapper().readValue(payload, Short.class);
            getDevice().setMovingAverage(movingAverage);

        }

    }

    @Override
    public void analogValue(int i) {

        addEvent(getContract().EVENT_ANALOG_VALUE, i);
    }

    @Override
    public void analogValueReached(int i) {
        addEvent(getContract().EVENT_ANALOG_VALUE_REACHED, i);
    }

    @Override
    public void voltage(int i) {
        addEvent(getContract().EVENT_VOLTAGE, i);
    }

    @Override
    public void voltageReached(int i) {
        addEvent(getContract().EVENT_VOLTAGE_REACHED, i);
    }

    @Override
    public void analogValueCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, period);
    }

    @Override
    public void voltageCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_VOLTAGE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold) {
        addStatus(getContract().STATUS_ANALOG_VALUE_THRESHOLD, threshold);
    }

    @Override
    public void voltageCallbackThresholdChanged(DeviceVoltageCallbackThreshold threshold) {
        addStatus(getContract().STATUS_VOLTAGE_THRESHOLD, threshold);
    }

    @Override
    public void movingAverageChanged(Short averaging) {
        addStatus(getContract().STATUS_MOVING_AVERAGE, averaging);
    }

}
