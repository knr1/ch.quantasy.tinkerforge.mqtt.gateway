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

import ch.quantasy.gateway.service.device.joystick.JoystickService;
import ch.quantasy.gateway.service.device.joystick.JoystickServiceContract;
import ch.quantasy.gateway.service.device.servo.ServoServiceContract;
import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import ch.quantasy.mqtt.agents.GenericAgent;
import ch.quantasy.mqtt.agents.GenericAgentContract;
import ch.quantasy.mqtt.agents.led.AmbientLEDLightAgent;
import ch.quantasy.mqtt.gateway.client.GCEvent;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.servo.Degree;
import ch.quantasy.tinkerforge.device.servo.PulseWidth;
import ch.quantasy.tinkerforge.device.servo.Servo;

/**
 *
 * @author reto
 */
public class ServoJoystickAgent extends GenericAgent {

    private final ManagerServiceContract managerServiceContract;
    private final ServoServiceContract servoServiceContract;
    private final JoystickServiceContract joystickServiceContract;

    private final Servo[] servos;

    public ServoJoystickAgent(URI mqttURI) throws MqttException, InterruptedException {
        super(mqttURI, "epnerp4", new GenericAgentContract("ServoJoystick", "SJ01"));
        connect();

        managerServiceContract = new ManagerServiceContract("Manager");
        servoServiceContract = new ServoServiceContract("6JLxaK", TinkerforgeDeviceClass.Servo.toString());
        joystickServiceContract = new JoystickServiceContract("hBc", TinkerforgeDeviceClass.Joystick.toString());

        connectStacks(new TinkerforgeStackAddress("localhost"));

        publishIntent(servoServiceContract.INTENT_STATUS_LED, false);
        servos = new Servo[2];
        servos[0] = new Servo(0);
        servos[1] = new Servo(1);

        servos[0].setPulseWidth(new PulseWidth(1000, 2000));
        servos[0].setDegree(new Degree((short) -32767, (short) 32767));
        servos[0].setPeriod(20000);
        servos[0].setEnabled(true);

        servos[1].setPulseWidth(new PulseWidth(1000, 2000));
        servos[1].setDegree(new Degree((short) -32767, (short) 32767));
        servos[1].setPeriod(20000);
        servos[1].setEnabled(true);
        publishIntent(servoServiceContract.INTENT_SERVOS, servos);
        publishIntent(servoServiceContract.INTENT_STATUS_LED, true);
        publishIntent(joystickServiceContract.INTENT_CALIBRATE, true);

        subscribe(joystickServiceContract.EVENT_POSITION, (topic, payload) -> {
            GCEvent<JoystickService.Position>[] position = toEventArray(payload, JoystickService.Position.class);
            int joystickX = position[0].getValue().getX();
            int joystickY = position[0].getValue().getY();

            if (joystickX > 0) {
                servos[0].setPosition((short) 32767);
            }
            if (joystickX < 0) {
                servos[0].setPosition((short) -32767);
            }
            int velocityX = (int) (Math.abs((65535.0 / (100 * 100)) * (joystickX * joystickX)) + 0.5);
            if (joystickY > 0) {
                servos[1].setPosition((short) 32767);
            }
            if (joystickY < 0) {
                servos[1].setPosition((short) -32767);
            }
            int velocityY = (int) (Math.abs((65535.0 / (100 * 100)) * (joystickY * joystickY)) + 0.5);

            if (velocityX > 2) {
                servos[0].setVelocity(velocityX);
                servos[0].setEnabled(true);

            } else {
                servos[0].setVelocity(0);
                servos[0].setEnabled(false);
            }
            if (velocityY > 2) {
                servos[1].setVelocity(velocityY);
                servos[1].setEnabled(true);

            } else {
                servos[1].setVelocity(0);
                servos[1].setEnabled(false);

            }
            publishIntent(servoServiceContract.INTENT_SERVOS, servos);
        });
        publishIntent(joystickServiceContract.INTENT_POSITION_CALLBACK_PERIOD, 10);

    }

    private void connectRemoteServices(String... addresses) {
        for (String address : addresses) {
            publishIntent(managerServiceContract.INTENT_STACK_ADDRESS_ADD, new TinkerforgeStackAddress(address));
            try {
                //Bad idea! Better wait for the event of the managerService... having accepted the stack(s).
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AmbientLEDLightAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        URI mqttURI = URI.create("tcp://localhost:1883");
        if (args.length > 0) {
            mqttURI = URI.create(args[0]);
        } else {
            System.out.printf("Per default, 'tcp://127.0.0.1:1883' is chosen.\nYou can provide another address as first argument i.e.: tcp://iot.eclipse.org:1883\n");
        }
        System.out.printf("\n%s will be used as broker address.\n", mqttURI);
        ServoJoystickAgent agent = new ServoJoystickAgent(mqttURI);
        System.in.read();
    }

}
