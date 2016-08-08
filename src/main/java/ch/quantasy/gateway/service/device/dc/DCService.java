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
package ch.quantasy.gateway.service.device.dc;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dc.DCDevice;
import ch.quantasy.tinkerforge.device.dc.DCDeviceCallback;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DCService extends AbstractDeviceService<DCDevice, DCServiceContract> implements DCDeviceCallback {

    public DCService(DCDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new DCServiceContract(device));
        addDescription(getServiceContract().INTENT_ACCELERATION, "[0.." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DRIVER_MODE, "[0|1]");
        addDescription(getServiceContract().INTENT_ENABLED, "[true|false]");
        addDescription(getServiceContract().INTENT_MINIMUM_VOLTAGE, "[6.." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_PWM_FREQUENCY, "[1..20000]");
        addDescription(getServiceContract().INTENT_VELOCITY_VELOCITY, "-32767..32767");
        addDescription(getServiceContract().INTENT_VELOCITY_CALLBACK_PERIOD, "[0.." + Integer.MAX_VALUE + "]");

        addDescription(getServiceContract().EVENT_FULL_BRAKE, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_UNDERVOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_VELOCITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_EMERGENCY_SHUTDOWN, "[0.." + Long.MAX_VALUE + "]");

        addDescription(getServiceContract().STATUS_ACCELERATION, "[0.." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DRIVER_MODE, "[0|1]");
        addDescription(getServiceContract().STATUS_ENABLED, "[true|false]");
        addDescription(getServiceContract().STATUS_MINIMUM_VOLTAGE, "[6.." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_PWM_FREQUENCY, "[1..20000]");
        addDescription(getServiceContract().STATUS_VELOCITY_VELOCITY, "-32767..32767");
        addDescription(getServiceContract().STATUS_VELOCITY_CALLBACK_PERIOD, "[0.." + Integer.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_ENABLED)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setEnabled(enabled);
        }

        if (string.startsWith(getServiceContract().INTENT_ACCELERATION)) {
            Integer acceleration = getMapper().readValue(payload, Integer.class);
            getDevice().setAcceleration(acceleration);
        }
        if (string.startsWith(getServiceContract().INTENT_DRIVER_MODE)) {
            Short driverMode = getMapper().readValue(payload, Short.class);
            getDevice().setDriveMode(driverMode);
        }
        if (string.startsWith(getServiceContract().INTENT_MINIMUM_VOLTAGE)) {
            Integer minimumVoltage = getMapper().readValue(payload, Integer.class);
            getDevice().setMinimumVoltage(minimumVoltage);
        }
        if (string.startsWith(getServiceContract().INTENT_PWM_FREQUENCY)) {
            Integer pwmFrequency = getMapper().readValue(payload, Integer.class);
            getDevice().setPWMFrequency(pwmFrequency);
        }
        if (string.startsWith(getServiceContract().INTENT_VELOCITY_VELOCITY)) {
            Short velocity = getMapper().readValue(payload, Short.class);
            getDevice().setVelocity(velocity);
        }
        if (string.startsWith(getServiceContract().INTENT_VELOCITY_CALLBACK_PERIOD)) {
            Integer velocityPeriod = getMapper().readValue(payload, Integer.class);
            getDevice().setVelocityPeriod(velocityPeriod);
        }
    }

    @Override
    public void PWMFrequencyChanged(Integer pwmFrequency) {
        addStatus(getServiceContract().STATUS_PWM_FREQUENCY, pwmFrequency);
    }

    @Override
    public void accelerationChanged(Integer acceleration) {
        addStatus(getServiceContract().STATUS_ACCELERATION, acceleration);
    }

    @Override
    public void driveModeChanged(Short driverMode) {
        addStatus(getServiceContract().STATUS_DRIVER_MODE, driverMode);
    }

    @Override
    public void minimumVoltageChanged(Integer minimumVoltage) {
        addStatus(getServiceContract().STATUS_MINIMUM_VOLTAGE, minimumVoltage);

    }

    @Override
    public void velocityPeriodChanged(Integer velocityPeriod) {
        addStatus(getServiceContract().STATUS_VELOCITY_CALLBACK_PERIOD, velocityPeriod);
    }

    @Override
    public void velocityChanged(Short velocity) {
        addStatus(getServiceContract().STATUS_VELOCITY_VELOCITY, velocity);
    }

    @Override
    public void currentVelocity(short s) {
        addEvent(getServiceContract().EVENT_VELOCITY, new VelocityEvent(s));
    }

    @Override
    public void emergencyShutdown() {
        addEvent(getServiceContract().EVENT_EMERGENCY_SHUTDOWN, System.currentTimeMillis());
    }

    @Override
    public void underVoltage(int i) {
        addEvent(getServiceContract().EVENT_UNDERVOLTAGE, new UnderVoltageEvent(i));
    }

    @Override
    public void velocityReached(short s) {
        addEvent(getServiceContract().EVENT_FULL_BRAKE, new VelocityEvent(s));
    }

    @Override
    public void enabledChanged(Boolean isEnabled) {
        addStatus(getServiceContract().STATUS_ENABLED, isEnabled);
    }

    @Override
    public void fullBrake() {
        addEvent(getServiceContract().EVENT_FULL_BRAKE, System.currentTimeMillis());
    }

    public static class VelocityEvent {

        protected long timestamp;
        protected short value;

        private VelocityEvent() {
        }

        public VelocityEvent(short value) {
            this(value, System.currentTimeMillis());
        }

        public VelocityEvent(short value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getValue() {
            return value;
        }

    }

    public static class UnderVoltageEvent {

        protected long timestamp;
        protected int value;

        private UnderVoltageEvent() {
        }

        public UnderVoltageEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public UnderVoltageEvent(int value, long timeStamp) {
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
