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
package ch.quantasy.mqtt.agents.Servo;

import ch.quantasy.gateway.service.device.servo.ServoServiceContract;
import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import ch.quantasy.mqtt.agents.GenericAgent;
import ch.quantasy.mqtt.agents.GenericAgentContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.servo.Degree;
import ch.quantasy.tinkerforge.device.servo.PulseWidth;
import ch.quantasy.tinkerforge.device.servo.Servo;

/**
 *
 * @author reto
 */
public class ServoAgent extends GenericAgent {

    private final ManagerServiceContract managerServiceContract;
    private final ServoServiceContract servoServiceContract;

    public ServoAgent(URI mqttURI) throws MqttException, InterruptedException {
        super(mqttURI, "erspin4", new GenericAgentContract("Servo", "01"));
        connect();

        managerServiceContract = new ManagerServiceContract("Manager");
        servoServiceContract = new ServoServiceContract("6JLxaK", TinkerforgeDeviceClass.Servo.toString());

        connectStacks(new TinkerforgeStackAddress("localhost"));

        publishIntent(servoServiceContract.INTENT_STATUS_LED, false);
        Thread.sleep(2000);
        publishIntent(servoServiceContract.INTENT_STATUS_LED, true);
        Servo[] servos = new Servo[2];
        servos[0] = new Servo(0);
        servos[1] = new Servo(1);

        servos[0].setPulseWidth(new PulseWidth(1000, 2000));
        servos[0].setDegree(new Degree((short) -9000, (short) 9000));
        servos[0].setPeriod(20000);
        servos[0].setEnabled(true);

        servos[1].setPulseWidth(new PulseWidth(1000, 2000));
        servos[1].setDegree(new Degree((short) -9000, (short) 9000));
        servos[1].setPeriod(20000);
        servos[1].setEnabled(true);

        for (int i = 0; i < 5; i++) {
            servos[i % 2].setPosition((short) 9000);
            servos[i % 2].setVelocity(6500);
            publishIntent(servoServiceContract.INTENT_SERVOS, new Servo[]{servos[i % 2]});
            Thread.sleep(5000);
            servos[i % 2].setPosition((short) -9000);
            servos[i % 2].setVelocity(65535);

            publishIntent(servoServiceContract.INTENT_SERVOS, new Servo[]{servos[i % 2]});
            Thread.sleep(1000);
        }
        for (int i = 0; i < 5; i++) {
            servos[0].setPosition((short) 9000);
            servos[0].setVelocity(6500);
            servos[1].setPosition((short) 9000);
            servos[1].setVelocity(6500);
            publishIntent(servoServiceContract.INTENT_SERVOS, servos);
            Thread.sleep(5000);
            servos[0].setPosition((short) -9000);
            servos[0].setVelocity(65535);
            servos[1].setPosition((short) -9000);
            servos[1].setVelocity(65535);
            publishIntent(servoServiceContract.INTENT_SERVOS, servos);
            Thread.sleep(1000);
        }
        servos[0].setEnabled(false);
        servos[1].setEnabled(false);
        publishIntent(servoServiceContract.INTENT_SERVOS, servos);

    }

    public static void main(String[] args) throws Throwable {
        URI mqttURI = URI.create("tcp://localhost:1883");
        if (args.length > 0) {
            mqttURI = URI.create(args[0]);
        } else {
            System.out.printf("Per default, 'tcp://127.0.0.1:1883' is chosen.\nYou can provide another address as first argument i.e.: tcp://iot.eclipse.org:1883\n");
        }
        System.out.printf("\n%s will be used as broker address.\n", mqttURI);
        ServoAgent agent = new ServoAgent(mqttURI);
        System.in.read();
    }

}
