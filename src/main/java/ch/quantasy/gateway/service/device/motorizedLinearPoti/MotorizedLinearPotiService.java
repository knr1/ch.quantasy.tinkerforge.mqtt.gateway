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
package ch.quantasy.gateway.service.device.motorizedLinearPoti;

import ch.quantasy.gateway.message.motorizedLinearPoti.PositionEvent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.gateway.message.motorizedLinearPoti.DeviceMotorPosition;
import ch.quantasy.gateway.message.motorizedLinearPoti.DevicePositionCallbackConfiguration;
import ch.quantasy.gateway.message.motorizedLinearPoti.MotorPositionStatus;
import ch.quantasy.gateway.message.motorizedLinearPoti.PositionCallbackConfigurationStatus;
import ch.quantasy.gateway.message.motorizedLinearPoti.PositionReachedCallbackConfigurationStatus;
import ch.quantasy.tinkerforge.device.motorizedLinearPoti.MotorizedLinearPotiDevice;
import ch.quantasy.tinkerforge.device.motorizedLinearPoti.MotorizedLinearPotiDeviceCallback;

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class MotorizedLinearPotiService extends AbstractDeviceService<MotorizedLinearPotiDevice, MotorizedLinearPotiServiceContract> implements MotorizedLinearPotiDeviceCallback {

    public MotorizedLinearPotiService(MotorizedLinearPotiDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new MotorizedLinearPotiServiceContract(device));
    }

    @Override
    public void position(int i) {
        readyToPublishEvent(getContract().EVENT_POSITION, new PositionEvent(i));
    }

    @Override
    public void positionReached(int i) {
        readyToPublishEvent(getContract().EVENT_POSITION_REACHED, new PositionEvent(i));
    }

    @Override
    public void motorPositionChanged(DeviceMotorPosition motorPosition) {
        readyToPublishStatus(getContract().STATUS_MOTOR_POSITION, new MotorPositionStatus(motorPosition));
    }

    @Override
    public void positionReachedCallbackConfigurationChanged(boolean value) {
        readyToPublishStatus(getContract().STATUS_POSITION_REACHED_CALLBACK_CONFIGURATION, new PositionReachedCallbackConfigurationStatus(value));
    }

    @Override
    public void positionCallbackConfigurationChanged(DevicePositionCallbackConfiguration config) {
        readyToPublishStatus(getContract().STATUS_POSITION_CALLBACK_CONFIGURATION, new PositionCallbackConfigurationStatus(config));
    }

}
