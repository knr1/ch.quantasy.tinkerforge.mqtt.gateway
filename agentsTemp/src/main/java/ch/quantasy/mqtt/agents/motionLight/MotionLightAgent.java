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
package ch.quantasy.mqtt.agents.motionLight;

import ch.quantasy.gateway.service.device.motionDetector.MotionDetectorServiceContract;
import ch.quantasy.gateway.service.device.remoteSwitch.RemoteSwitchServiceContract;
import ch.quantasy.mqtt.agents.GenericAgent;
import ch.quantasy.mqtt.agents.GenericAgentContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketCParameters;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 */
public class MotionLightAgent extends GenericAgent {

    private final RemoteSwitchServiceContract remoteSwitchServiceContract;
    private final MotionDetectorServiceContract motionDetectorServiceContract;
    private final Thread t;

    public MotionLightAgent(URI mqttURI) throws MqttException {
        super(mqttURI, "2408h40h4", new GenericAgentContract("MotionLight", "e48"));
        connect();
        connectStacks(new TinkerforgeStackAddress("erdgeschoss"));
        remoteSwitchServiceContract = new RemoteSwitchServiceContract("jKQ", TinkerforgeDeviceClass.RemoteSwitch.toString());
        motionDetectorServiceContract = new MotionDetectorServiceContract("kfT", TinkerforgeDeviceClass.MotionDetector.toString());

        subscribe(motionDetectorServiceContract.EVENT_DETECTION_CYCLE_ENDED, (topic, payload) -> {
            synchronized (MotionLightAgent.this) {
                delayStart = System.currentTimeMillis();
                MotionLightAgent.this.notifyAll();
            }
        });
        subscribe(motionDetectorServiceContract.EVENT_MOTION_DETECTED, (topic, payload) -> {
            synchronized (MotionLightAgent.this) {
                switchLight(SwitchSocketCParameters.SwitchTo.switchOn);
                delayStart = null;
            }
        });

        t = new Thread(new switcher());
        t.start();

    }

    private Long delayStart;

    private SwitchSocketCParameters.SwitchTo state;

    class switcher implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (MotionLightAgent.this) {
                    if (delayStart == null) {
                        try {
                            MotionLightAgent.this.wait(10000);
                        } catch (InterruptedException ex) {
                        }
                    }
                    if (delayStart != null) {
                        long delay = Math.max(0, 10000 - (System.currentTimeMillis() - delayStart));
                        try {
                            MotionLightAgent.this.wait(delay);
                        } catch (InterruptedException ex) {
                        }
                    }
                    if (delayStart != null) {
                        switchLight(SwitchSocketCParameters.SwitchTo.switchOff);
                        delayStart = null;
                    }
                }
            }
        }

    }

    private void switchLight(SwitchSocketCParameters.SwitchTo state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        SwitchSocketCParameters config = new SwitchSocketCParameters('D', (short) 3, state);
        String topic = remoteSwitchServiceContract.INTENT_SWITCH_SOCKET_C;
        publishIntent(topic, config);
        Logger.getLogger(MotionLightAgent.class.getName()).log(Level.INFO, "Switching:", state);
    }

    public static void main(String[] args) throws Throwable {
        URI mqttURI = URI.create("tcp://localhost:1883");
        if (args.length > 0) {
            mqttURI = URI.create(args[0]);
        } else {
            System.out.printf("Per default, 'tcp://127.0.0.1:1883' is chosen.\nYou can provide another address as first argument i.e.: tcp://iot.eclipse.org:1883\n");
        }
        System.out.printf("\n%s will be used as broker address.\n", mqttURI);
        MotionLightAgent agent = new MotionLightAgent(mqttURI);
        System.in.read();
    }

}
