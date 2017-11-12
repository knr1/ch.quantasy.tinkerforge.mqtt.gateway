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

import ch.quantasy.gateway.message.event.analogInV2.VoltageEvent;
import ch.quantasy.gateway.message.event.dc.EmergencyShutdownEvent;
import ch.quantasy.gateway.message.event.dc.FullBrakeEvent;
import ch.quantasy.gateway.message.event.dc.UnderVoltageEvent;
import ch.quantasy.gateway.message.event.dc.VelocityEvent;
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
        readyToPublishEvent(getContract().EVENT_VELOCITY, new VelocityEvent(s));
    }

    @Override
    public void emergencyShutdown() {
        readyToPublishEvent(getContract().EVENT_EMERGENCY_SHUTDOWN, new EmergencyShutdownEvent());
    }

    @Override
    public void underVoltage(int i) {
        readyToPublishEvent(getContract().EVENT_UNDERVOLTAGE, new UnderVoltageEvent(i));
    }

    @Override
    public void velocityReached(short s) {
        readyToPublishEvent(getContract().EVENT_VELOCITY_REACHED, new VoltageEvent(s));
    }

    @Override
    public void enabledChanged(Boolean isEnabled) {
        publishStatus(getContract().STATUS_ENABLED, isEnabled);
    }

    @Override
    public void fullBrake() {
        readyToPublishEvent(getContract().EVENT_FULL_BRAKE, new FullBrakeEvent());
    }

}
