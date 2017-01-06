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
package ch.quantasy.gateway.service.device.servo;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.servo.Servo;
import ch.quantasy.tinkerforge.device.servo.ServoDevice;
import ch.quantasy.tinkerforge.device.servo.ServoDeviceCallback;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class ServoService extends AbstractDeviceService<ServoDevice, ServoServiceContract> implements ServoDeviceCallback {

    public ServoService(ServoDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new ServoServiceContract(device));
        publishDescription(getContract().INTENT_SERVOS, "--- \n  {- \n  id: [0..6]\n  [enabled: [true|false]\n|]  [position: [-32767..32767]\n|]  [acceleration: [0..65536]\n|]  [velocity: [0..65535]\n|]  [degree:\n    min: [-32767..32767]\n    max: [-32767..32767]\n|]  [period: [1..65535]\n|]  [pulseWidth:\n    min: [1..65535]\n    max: [1..65535]|]}_7");
        publishDescription(getContract().INTENT_STATUS_LED, "[true|false]");
        publishDescription(getContract().INTENT_MINIMUM_VOLTAGE, "[5000.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_OUTPUT_VOLTAGE, "[2000..9000]");

        publishDescription(getContract().EVENT_POSITION_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [0..6]\n position: [-32767..32767]");
        publishDescription(getContract().EVENT_UNDERVOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_VELOCITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [0..6]\n value: [0.." + Short.MAX_VALUE + "]");

        publishDescription(getContract().STATUS_SERVOS, "--- \n  {- \n  id: [0..6]\n  enabled: [true|false|null]\n  position: [-32767..32767|null]\n  acceleration: [0..65536|null]\n  velocity: [0..65535|null]\n  degree: [[\n    min: [-32767..32767]\n    max: [-32767..32767]\n]|null]\n  period: [1..65535|null]\n  pulseWidth: [[\n    min: [1..65535]\n    max: [1..65535]]|null]}_7");
        publishDescription(getContract().STATUS_STATUS_LED, "[true|false]");
        publishDescription(getContract().STATUS_MINIMUM_VOLTAGE, "[6.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_OUTPUT_VOLTAGE, "[1..20000]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_STATUS_LED)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setStatusLED(enabled);
        }
        if (string.startsWith(getContract().INTENT_SERVOS)) {
            Servo[] servos = getMapper().readValue(payload, Servo[].class);
            getDevice().setServos(servos);
        }
        if (string.startsWith(getContract().INTENT_MINIMUM_VOLTAGE)) {
            Integer value = getMapper().readValue(payload, Integer.class);
            getDevice().setMinimumVoltage(value);
        }
        if (string.startsWith(getContract().INTENT_OUTPUT_VOLTAGE)) {
            Integer value = getMapper().readValue(payload, Integer.class);
            getDevice().setOutputVoltage(value);
        }

    }

    @Override
    public void minimumVoltageChanged(Integer minimumVoltage) {
        publishStatus(getContract().STATUS_MINIMUM_VOLTAGE, minimumVoltage);

    }

    @Override
    public void underVoltage(int i) {
        publishEvent(getContract().EVENT_UNDERVOLTAGE, i);
    }

    @Override
    public void statusLEDChanged(Boolean statusLED) {
        publishStatus(getContract().STATUS_STATUS_LED, statusLED);
    }

    @Override
    public void outputVoltageChanged(Integer outputVoltage) {
        publishStatus(getContract().STATUS_OUTPUT_VOLTAGE, outputVoltage);

    }

    @Override
    public void positionReached(short s, short s1) {
        publishEvent(getContract().EVENT_POSITION_REACHED, new Position(s, s1));
    }

    @Override
    public void velocityReached(short s, short s1) {
        publishEvent(getContract().EVENT_VELOCITY_REACHED, new Velocity(s, s1));

    }

    @Override
    public void servosChanged(Collection<Servo> values) {
        publishStatus(getContract().STATUS_SERVOS,values);
    }

    public static class Position {

        private short id;
        private short position;

        private Position() {
        }

        public Position(short id, short position) {
            this.id = id;
            this.position = position;
        }

        public short getId() {
            return id;
        }

        public short getPosition() {
            return position;
        }

    }

    public static class Velocity {

        private short id;
        private short velocity;

        private Velocity() {
        }

        public Velocity(short id, short velocity) {
            this.id = id;
            this.velocity = velocity;
        }

        public short getId() {
            return id;
        }

        public short getVelocity() {
            return velocity;
        }

    }

}
