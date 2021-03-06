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
package ch.quantasy.gateway.service.tinkerforge;

import ch.quantasy.gateway.binding.tinkerforge.servo.ServoServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.servo.PositionEvent;
import ch.quantasy.gateway.binding.tinkerforge.servo.UnderVoltageEvent;
import ch.quantasy.gateway.binding.tinkerforge.servo.VelocityEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.gateway.binding.tinkerforge.servo.Servo;
import ch.quantasy.gateway.binding.tinkerforge.dc.MinimumVoltageStatus;
import ch.quantasy.gateway.binding.tinkerforge.servo.OutputVoltageStatus;
import ch.quantasy.gateway.binding.tinkerforge.servo.ServosStatus;
import ch.quantasy.gateway.binding.tinkerforge.servo.StatusLEDStatus;
import ch.quantasy.tinkerforge.device.servo.ServoDevice;
import ch.quantasy.tinkerforge.device.servo.ServoDeviceCallback;
import java.net.URI;
import java.util.Set;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class ServoService extends AbstractDeviceService<ServoDevice, ServoServiceContract> implements ServoDeviceCallback {

    public ServoService(ServoDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new ServoServiceContract(device));
    }

    @Override
    public void minimumVoltageChanged(Integer minimumVoltage) {
        readyToPublishStatus(getContract().STATUS_MINIMUM_VOLTAGE, new MinimumVoltageStatus(minimumVoltage));

    }

    @Override
    public void underVoltage(int i) {
        readyToPublishEvent(getContract().EVENT_UNDERVOLTAGE, new UnderVoltageEvent(i));
    }

    @Override
    public void statusLEDChanged(Boolean statusLED) {
        readyToPublishStatus(getContract().STATUS_STATUS_LED, new StatusLEDStatus(statusLED));
    }

    @Override
    public void outputVoltageChanged(Integer outputVoltage) {
        readyToPublishStatus(getContract().STATUS_OUTPUT_VOLTAGE, new OutputVoltageStatus(outputVoltage));

    }

    @Override
    public void positionReached(short s, short s1) {
        readyToPublishEvent(getContract().EVENT_POSITION_REACHED, new PositionEvent(s, s1));
    }

    @Override
    public void velocityReached(short s, short s1) {
        readyToPublishEvent(getContract().EVENT_VELOCITY_REACHED, new VelocityEvent(s, s1));

    }

    @Override
    public void servosChanged(Set<Servo> values) {
        readyToPublishStatus(getContract().STATUS_SERVOS, new ServosStatus(values));
    }

}
