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
package ch.quantasy.gateway.service.device.co2;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.co2.CO2Device;
import ch.quantasy.tinkerforge.device.co2.CO2DeviceCallback;
import ch.quantasy.tinkerforge.device.co2.DeviceCO2ConcentrationCallbackThreshold;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class CO2Service extends AbstractDeviceService<CO2Device, CO2ServiceContract> implements CO2DeviceCallback {

    public CO2Service(CO2Device device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new CO2ServiceContract(device));
        addDescription(getServiceContract().INTENT_CO2_CONCENTRATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_CO2_CONCENTRATION_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..10000]\n max: [0..10000]");

        addDescription(getServiceContract().EVENT_CO2_CONCENTRATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..10000]\n");
        addDescription(getServiceContract().EVENT_CO2_CONCENTRATION_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..10000]\n");
        addDescription(getServiceContract().STATUS_CO2_CONCENTRATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_CO2_CONCENTRATION_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..10000]\n max: [0..10000]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_DEBOUNCE_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_CO2_CONCENTRATION_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setCO2ConcentrationCallbackPeriod(period);
        }

        if (string.startsWith(getServiceContract().INTENT_CO2_CONCENTRATION_THRESHOLD)) {

            DeviceCO2ConcentrationCallbackThreshold threshold = getMapper().readValue(payload, DeviceCO2ConcentrationCallbackThreshold.class);
            getDevice().setCO2ConcentrationCallbackThreshold(threshold);
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void co2ConcentrationCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_CO2_CONCENTRATION_CALLBACK_PERIOD, period);
    }

    @Override
    public void co2ConcentrationCallbackThresholdChanged(DeviceCO2ConcentrationCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_CO2_CONCENTRATION_THRESHOLD, threshold);
    }

    @Override
    public void co2Concentration(int i) {
        addEvent(getServiceContract().EVENT_CO2_CONCENTRATION, new CO2ConcentrationEvent(i));
    }

    @Override
    public void co2ConcentrationReached(int i) {
        addEvent(getServiceContract().EVENT_CO2_CONCENTRATION_REACHED, new CO2ConcentrationEvent(i));
    }

    public static class CO2ConcentrationEvent {

        protected long timestamp;
        protected long value;

        public CO2ConcentrationEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public CO2ConcentrationEvent(long value, long timeStamp) {
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
