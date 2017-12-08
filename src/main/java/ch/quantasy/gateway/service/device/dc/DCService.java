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

import ch.quantasy.gateway.message.analogInV2.VoltageEvent;
import ch.quantasy.gateway.message.dc.EmergencyShutdownEvent;
import ch.quantasy.gateway.message.dc.FullBrakeEvent;
import ch.quantasy.gateway.message.dc.UnderVoltageEvent;
import ch.quantasy.gateway.message.dc.VelocityEvent;
import ch.quantasy.gateway.message.dc.AccelerationStatus;
import ch.quantasy.gateway.message.dc.DriveModeStatus;
import ch.quantasy.gateway.message.dc.EnableStatus;
import ch.quantasy.gateway.message.dc.MinimumVoltageStatus;
import ch.quantasy.gateway.message.dc.PwmFrequencyStatus;
import ch.quantasy.gateway.message.dc.VelocityPeriodStatus;
import ch.quantasy.gateway.message.dc.VelocityStatus;
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
        readyToPublishStatus(getContract().STATUS_PWM_FREQUENCY, new PwmFrequencyStatus(pwmFrequency));
    }

    @Override
    public void accelerationChanged(Integer acceleration) {
        readyToPublishStatus(getContract().STATUS_ACCELERATION, new AccelerationStatus(acceleration));
    }

    @Override
    public void driveModeChanged(Short driverMode) {
        readyToPublishStatus(getContract().STATUS_DRIVER_MODE, new DriveModeStatus(driverMode));
    }

    @Override
    public void minimumVoltageChanged(Integer minimumVoltage) {
        readyToPublishStatus(getContract().STATUS_MINIMUM_VOLTAGE, new MinimumVoltageStatus(minimumVoltage));

    }

    @Override
    public void velocityPeriodChanged(Integer velocityPeriod) {
        readyToPublishStatus(getContract().STATUS_VELOCITY_CALLBACK_PERIOD, new VelocityPeriodStatus(velocityPeriod));
    }

    @Override
    public void velocityChanged(Short velocity) {
        readyToPublishStatus(getContract().STATUS_VELOCITY_VELOCITY, new VelocityStatus(velocity));
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
        readyToPublishStatus(getContract().STATUS_ENABLED, new EnableStatus(isEnabled));
    }

    @Override
    public void fullBrake() {
        readyToPublishEvent(getContract().EVENT_FULL_BRAKE, new FullBrakeEvent());
    }

}
