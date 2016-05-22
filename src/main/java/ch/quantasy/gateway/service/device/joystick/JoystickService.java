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
package ch.quantasy.gateway.service.device.joystick;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.joystick.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.tinkerforge.device.joystick.DevicePositionCallbackThreshold;
import ch.quantasy.tinkerforge.device.joystick.JoystickDevice;
import ch.quantasy.tinkerforge.device.joystick.JoystickDeviceCallback;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class JoystickService extends AbstractDeviceService<JoystickDevice, JoystickServiceContract> implements JoystickDeviceCallback {

    public JoystickService(JoystickDevice device, URI mqttURI) throws MqttException {
        super(device, new JoystickServiceContract(device), mqttURI);
        addDescription(getServiceContract().INTENT_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_POSITION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n minX: [0..4095]\n maxX: [0..4095]\n minY: [0..4095]\n maxY: [0..4095]");
        addDescription(getServiceContract().INTENT_POSITION_THRESHOLD, "option: [x|o|i|<|>]\n minX: [-100..100]\n maxX: [-100..100]\n minY: [-100..100]\n maxY: [-100..100]");
        addDescription(getServiceContract().INTENT_CALIBRATE, "[true|false]");

        addDescription(getServiceContract().EVENT_CALIBRATE, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_PRESSED, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_RELEASED, "[0.." + Long.MAX_VALUE + "]");

        addDescription(getServiceContract().EVENT_ANALOG_VALUE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        addDescription(getServiceContract().EVENT_POSITION, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..9000]\n");
        addDescription(getServiceContract().EVENT_ANALOG_VALUE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [0..4095]\n y: [0..4095]");
        addDescription(getServiceContract().EVENT_POSITION_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [-100..100]\n y: [-100..100]");
        addDescription(getServiceContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_POSITION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n minX: [0..4095]\n maxX: [0..4095]\n minY: [0..4095]\n maxY: [0..4095]");
        addDescription(getServiceContract().STATUS_POSITION_THRESHOLD, "option: [x|o|i|<|>]\n minX: [-100..100]\n maxX: [-100..100]\n minY: [-100..100]\n maxY: [-100..100]");
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
                getDevice().setIlluminanceCallbackThreshold(threshold);

            }
            if (string.startsWith(getServiceContract().INTENT_CALIBRATE)) {
                Boolean calibrate = getMapper().readValue(payload, Boolean.class);
                getDevice().setCalibration(calibrate);
            }

        } catch (Exception ex) {
            Logger.getLogger(JoystickService.class
                    .getName()).log(Level.INFO, null, ex);
            return;
        }
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
    public void postionCallbackThresholdChanged(DevicePositionCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_POSITION_THRESHOLD, threshold);
    }

    @Override
    public void calibrated() {
        addEvent(getServiceContract().EVENT_CALIBRATE, System.currentTimeMillis());
    }

    @Override
    public void analogValue(int i, int i1) {
        addEvent(getServiceContract().EVENT_ANALOG_VALUE, new AnalogValueEvent(i, i1));
    }

    @Override
    public void analogValueReached(int i, int i1) {
        addEvent(getServiceContract().EVENT_ANALOG_VALUE_REACHED, new AnalogValueEvent(i, i1));
    }

    @Override
    public void position(short s, short s1) {
        addEvent(getServiceContract().EVENT_POSITION, new PositionEvent(s, s1));
    }

    @Override
    public void positionReached(short s, short s1) {
        addEvent(getServiceContract().EVENT_POSITION_REACHED, new PositionEvent(s, s1));
    }

    @Override
    public void pressed() {
        addEvent(getServiceContract().EVENT_PRESSED, System.currentTimeMillis());
    }

    @Override
    public void released() {
        addEvent(getServiceContract().EVENT_RELEASED, System.currentTimeMillis());
    }

    public static class AnalogValueEvent {

        protected long timestamp;
        protected int x;
        protected int y;

        public AnalogValueEvent(int x, int y) {
            this(x, y, System.currentTimeMillis());
        }

        public AnalogValueEvent(int x, int y, long timeStamp) {
            this.x = x;
            this.y = y;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    public static class PositionEvent {

        protected long timestamp;
        protected short x;
        protected short y;

        public PositionEvent(short x, short y) {
            this(x, y, System.currentTimeMillis());
        }

        public PositionEvent(short x, short y, long timeStamp) {
            this.x = x;
            this.y = y;
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

    }

}
