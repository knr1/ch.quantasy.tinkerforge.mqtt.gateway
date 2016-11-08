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
package ch.quantasy.gateway.service.device.voltageCurrent;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.voltageCurrent.DeviceCalibration;
import ch.quantasy.tinkerforge.device.voltageCurrent.DeviceConfiguration;
import ch.quantasy.tinkerforge.device.voltageCurrent.DeviceVoltageCurrentCallbackThreshold;
import ch.quantasy.tinkerforge.device.voltageCurrent.VoltageCurrentDevice;
import ch.quantasy.tinkerforge.device.voltageCurrent.VoltageCurrentDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class VoltageCurrentService extends AbstractDeviceService<VoltageCurrentDevice, VoltageCurrentServiceContract> implements VoltageCurrentDeviceCallback {

    public VoltageCurrentService(VoltageCurrentDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new VoltageCurrentServiceContract(device));

        addDescription(getContract().INTENT_CALIBRATION, "gainMultiplier: [1.."+Integer.MAX_VALUE+"]\n gainDivisor: [1.."+Integer.MAX_VALUE+"]");
        addDescription(getContract().STATUS_CALIBRATION, "gainMultiplier: [1.."+Integer.MAX_VALUE+"]\n gainDivisor: [1.."+Integer.MAX_VALUE+"]");

        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_VOLTAGE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-50001..50001]\n max: [-50001..50001]");
        addDescription(getContract().INTENT_CURRENT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_CURRENT_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..36000]\n max: [0..36000]");
        addDescription(getContract().INTENT_POWER_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_POWER_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..720000]\n max: [0..720000]");
        addDescription(getContract().INTENT_CONFIGURATION, "averaging: [AVERAGING_1|AVERAGING_4|AVERAGING_16|AVERAGING_64|AVERAGING_128|AVERAGING_256|AVERAGING_512|AVERAGING_1024]\n"
                + " voltageConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]\n"
                + " currentConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]\n");
        addDescription(getContract().EVENT_VOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-50001..50001]\n");
        addDescription(getContract().EVENT_VOLTAGE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [-50001..50001]");
        addDescription(getContract().STATUS_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_VOLTAGE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..50001]\n max: [-50001..50001]");
        addDescription(getContract().EVENT_CURRENT, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..36000]\n");
        addDescription(getContract().EVENT_CURRENT_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..36000]");
        addDescription(getContract().STATUS_CURRENT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_CURRENT_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..36000]\n max: [0..36000]");
        addDescription(getContract().EVENT_POWER, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..720000]\n");
        addDescription(getContract().EVENT_POWER_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..720000]");
        addDescription(getContract().STATUS_POWER_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_POWER_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..720000]\n max: [0..720000]");
        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_CONFIGURATION, "averaging: [AVERAGING_1|AVERAGING_4|AVERAGING_16|AVERAGING_64|AVERAGING_128|AVERAGING_256|AVERAGING_512|AVERAGING_1024]\n"
                + " voltageConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]\n"
                + " currentConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]\n");
        

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_VOLTAGE_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setVoltageCallbackPeriod(period);
        }

        if (string.startsWith(getContract().INTENT_VOLTAGE_THRESHOLD)) {

            DeviceVoltageCurrentCallbackThreshold threshold = getMapper().readValue(payload, DeviceVoltageCurrentCallbackThreshold.class);
            getDevice().setVoltageCallbackThreshold(threshold);
        }
        if (string.startsWith(getContract().INTENT_CURRENT_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setCurrentCallbackPeriod(period);
        }

        if (string.startsWith(getContract().INTENT_CURRENT_THRESHOLD)) {

            DeviceVoltageCurrentCallbackThreshold threshold = getMapper().readValue(payload, DeviceVoltageCurrentCallbackThreshold.class);
            getDevice().setCurrentCallbackThreshold(threshold);
        }

        if (string.startsWith(getContract().INTENT_POWER_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setPowerCallbackPeriod(period);
        }

        if (string.startsWith(getContract().INTENT_POWER_THRESHOLD)) {

            DeviceVoltageCurrentCallbackThreshold threshold = getMapper().readValue(payload, DeviceVoltageCurrentCallbackThreshold.class);
            getDevice().setPowerCallbackThreshold(threshold);
        }

        if (string.startsWith(getContract().INTENT_CONFIGURATION)) {
            DeviceConfiguration configuration = getMapper().readValue(payload, DeviceConfiguration.class);
            getDevice().setConfiguration(configuration);
        }
        if (string.startsWith(getContract().INTENT_CALIBRATION)) {
            DeviceCalibration calibration = getMapper().readValue(payload, DeviceCalibration.class);
            getDevice().setCalibration(calibration);
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void configurationChanged(DeviceConfiguration configuration) {
        addStatus(getContract().STATUS_CONFIGURATION, configuration);
    }

    @Override
    public void currentCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_CURRENT_CALLBACK_PERIOD, period);
    }

    @Override
    public void currentCallbackThresholdChanged(DeviceVoltageCurrentCallbackThreshold threshold) {
        addStatus(getContract().STATUS_CURRENT_THRESHOLD, threshold);
    }

    @Override
    public void calibrationChanged(DeviceCalibration calibration) {
        addStatus(getContract().STATUS_CALIBRATION, calibration);
    }

    @Override
    public void voltageCallbackThresholdChanged(DeviceVoltageCurrentCallbackThreshold voltageThreshold) {
        addStatus(getContract().STATUS_VOLTAGE_THRESHOLD, voltageThreshold);
    }

    @Override
    public void voltageCallbackPeriodChanged(long voltageCallbackPeriod) {
        addStatus(getContract().STATUS_VOLTAGE_CALLBACK_PERIOD, voltageCallbackPeriod);
    }

    @Override
    public void powerCallbackPeriodChanged(long powerCallbackPeriod) {
        addStatus(getContract().STATUS_POWER_CALLBACK_PERIOD, powerCallbackPeriod);
    }

    @Override
    public void powerCallbackThresholdChanged(DeviceVoltageCurrentCallbackThreshold powerThreshold) {
        addStatus(getContract().STATUS_POWER_THRESHOLD, powerThreshold);
    }

    @Override
    public void current(int i) {
        addEvent(getContract().EVENT_CURRENT, i);
    }

    @Override
    public void currentReached(int i) {
        addEvent(getContract().EVENT_CURRENT_REACHED, i);
    }

    @Override
    public void power(int i) {
        addEvent(getContract().EVENT_POWER, i);
    }

    @Override
    public void powerReached(int i) {
        addEvent(getContract().EVENT_POWER_REACHED, i);
    }

    @Override
    public void voltage(int i) {
        addEvent(getContract().EVENT_VOLTAGE, i);
    }

    @Override
    public void voltageReached(int i) {
        addEvent(getContract().EVENT_VOLTAGE_REACHED, i);
    }

}
