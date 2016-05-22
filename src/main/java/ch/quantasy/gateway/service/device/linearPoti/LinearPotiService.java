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
package ch.quantasy.gateway.service.device.linearPoti;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.linearPoti.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.tinkerforge.device.linearPoti.DevicePositionCallbackThreshold;
import ch.quantasy.tinkerforge.device.linearPoti.LinearPotiDevice;
import ch.quantasy.tinkerforge.device.linearPoti.LinearPotiDeviceCallback;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class LinearPotiService extends AbstractDeviceService<LinearPotiDevice, LinearPotiServiceContract> implements LinearPotiDeviceCallback {

    public LinearPotiService(LinearPotiDevice device, URI mqttURI) throws MqttException {
        super(device, new LinearPotiServiceContract(device), mqttURI);
        addDescription(getServiceContract().INTENT_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_POSITION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getServiceContract().INTENT_POSITION_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..100]\n max: [0..100]");
        addDescription(getServiceContract().EVENT_ANALOG_VALUE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        addDescription(getServiceContract().EVENT_POSITION, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..100]\n");
        addDescription(getServiceContract().EVENT_ANALOG_VALUE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        addDescription(getServiceContract().EVENT_POSITION_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..100]\n");
        addDescription(getServiceContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_POSITION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getServiceContract().STATUS_POSITION_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..100]\n max: [0..100]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
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
            if (string.startsWith(getServiceContract().INTENT_ANALOG_VALUE_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setAnalogValueCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_POSITION_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setPositionCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_ANALOG_VALUE_THRESHOLD)) {
                DeviceAnalogValueCallbackThreshold threshold = getMapper().readValue(payload, DeviceAnalogValueCallbackThreshold.class);
                getDevice().setAnalogValueThreshold(threshold);
            }
            if (string.startsWith(getServiceContract().INTENT_POSITION_THRESHOLD)) {
                DevicePositionCallbackThreshold threshold = getMapper().readValue(payload, DevicePositionCallbackThreshold.class);
                getDevice().setPositionCallbackThreshold(threshold);

            }

        } catch (Exception ex) {
            Logger.getLogger(LinearPotiService.class
                    .getName()).log(Level.INFO, null, ex);
            return;
        }
    }

    @Override
    public void analogValue(int i) {
        addEvent(getServiceContract().EVENT_ANALOG_VALUE, new AnalogValueEvent(i));
    }

    @Override
    public void analogValueReached(int i) {
        addEvent(getServiceContract().EVENT_ANALOG_VALUE_REACHED, new AnalogValueEvent(i));
    }

    @Override
    public void position(int i) {
        addEvent(getServiceContract().EVENT_POSITION, new PositionEvent(i));
    }

    @Override
    public void positionReached(int i) {
        addEvent(getServiceContract().EVENT_POSITION_REACHED, new PositionEvent(i));
    }

    @Override
    public void analogValueCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, period);
    }

    @Override
    public void positionCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_POSITION_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_ANALOG_VALUE_THRESHOLD, threshold);
    }

    @Override
    public void positionCallbackThresholdChanged(DevicePositionCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_POSITION_THRESHOLD, threshold);

    }

    public static class AnalogValueEvent {

        protected long timestamp;
        protected int value;

        public AnalogValueEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public AnalogValueEvent(int value, long timeStamp) {
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

    public static class PositionEvent {

        long timestamp;
        int value;

        public PositionEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public PositionEvent(int value, long timeStamp) {
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
