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
package ch.quantasy.gateway.service.device.dc;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dc.DCDevice;
import ch.quantasy.tinkerforge.device.dc.DCDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class DCService extends AbstractDeviceService<DCDevice, DCServiceContract> implements DCDeviceCallback {

    public DCService(DCDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new DCServiceContract(device));
        publishDescription(getContract().INTENT_ACCELERATION, "[0.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_DRIVER_MODE, "[0|1]");
        publishDescription(getContract().INTENT_ENABLED, "[true|false]");
        publishDescription(getContract().INTENT_MINIMUM_VOLTAGE, "[6000.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_PWM_FREQUENCY, "[1..20000]");
        publishDescription(getContract().INTENT_VELOCITY_VELOCITY, "-32767..32767");
        publishDescription(getContract().INTENT_VELOCITY_CALLBACK_PERIOD, "[0.." + Integer.MAX_VALUE + "]");

        publishDescription(getContract().EVENT_FULL_BRAKE, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_UNDERVOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_VELOCITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_VELOCITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Short.MAX_VALUE + "]");
        
        publishDescription(getContract().EVENT_EMERGENCY_SHUTDOWN, "[0.." + Long.MAX_VALUE + "]");

        publishDescription(getContract().STATUS_ACCELERATION, "[0.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_DRIVER_MODE, "[0|1]");
        publishDescription(getContract().STATUS_ENABLED, "[true|false]");
        publishDescription(getContract().STATUS_MINIMUM_VOLTAGE, "[6.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_PWM_FREQUENCY, "[1..20000]");
        publishDescription(getContract().STATUS_VELOCITY_VELOCITY, "-32767..32767");
        publishDescription(getContract().STATUS_VELOCITY_CALLBACK_PERIOD, "[0.." + Integer.MAX_VALUE + "]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_ENABLED)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setEnabled(enabled);
        }

        if (string.startsWith(getContract().INTENT_ACCELERATION)) {
            Integer acceleration = getMapper().readValue(payload, Integer.class);
            getDevice().setAcceleration(acceleration);
        }
        if (string.startsWith(getContract().INTENT_DRIVER_MODE)) {
            Short driverMode = getMapper().readValue(payload, Short.class);
            getDevice().setDriveMode(driverMode);
        }
        if (string.startsWith(getContract().INTENT_MINIMUM_VOLTAGE)) {
            Integer minimumVoltage = getMapper().readValue(payload, Integer.class);
            getDevice().setMinimumVoltage(minimumVoltage);
        }
        if (string.startsWith(getContract().INTENT_PWM_FREQUENCY)) {
            Integer pwmFrequency = getMapper().readValue(payload, Integer.class);
            getDevice().setPWMFrequency(pwmFrequency);
        }
        if (string.startsWith(getContract().INTENT_VELOCITY_VELOCITY)) {
            Short velocity = getMapper().readValue(payload, Short.class);
            getDevice().setVelocity(velocity);
        }
        if (string.startsWith(getContract().INTENT_VELOCITY_CALLBACK_PERIOD)) {
            Integer velocityPeriod = getMapper().readValue(payload, Integer.class);
            getDevice().setVelocityPeriod(velocityPeriod);
        }
    }

    @Override
    public void PWMFrequencyChanged(Integer pwmFrequency) {
        publishStatus(getContract().STATUS_PWM_FREQUENCY, pwmFrequency);
    }

    @Override
    public void accelerationChanged(Integer acceleration) {
        publishStatus(getContract().STATUS_ACCELERATION, acceleration);
    }

    @Override
    public void driveModeChanged(Short driverMode) {
        publishStatus(getContract().STATUS_DRIVER_MODE, driverMode);
    }

    @Override
    public void minimumVoltageChanged(Integer minimumVoltage) {
        publishStatus(getContract().STATUS_MINIMUM_VOLTAGE, minimumVoltage);

    }

    @Override
    public void velocityPeriodChanged(Integer velocityPeriod) {
        publishStatus(getContract().STATUS_VELOCITY_CALLBACK_PERIOD, velocityPeriod);
    }

    @Override
    public void velocityChanged(Short velocity) {
        publishStatus(getContract().STATUS_VELOCITY_VELOCITY, velocity);
    }

    @Override
    public void currentVelocity(short s) {
        publishEvent(getContract().EVENT_VELOCITY, s);
    }

    @Override
    public void emergencyShutdown() {
        publishEvent(getContract().EVENT_EMERGENCY_SHUTDOWN, System.currentTimeMillis());
    }

    @Override
    public void underVoltage(int i) {
        publishEvent(getContract().EVENT_UNDERVOLTAGE, i);
    }

    @Override
    public void velocityReached(short s) {
        publishEvent(getContract().EVENT_VELOCITY_REACHED, s);
    }

    @Override
    public void enabledChanged(Boolean isEnabled) {
        publishStatus(getContract().STATUS_ENABLED, isEnabled);
    }

    @Override
    public void fullBrake() {
        publishEvent(getContract().EVENT_FULL_BRAKE, System.currentTimeMillis());
    }

    
    
}
