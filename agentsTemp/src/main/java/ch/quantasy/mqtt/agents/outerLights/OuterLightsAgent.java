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
package ch.quantasy.mqtt.agents.outerLights;

import ch.quantasy.gateway.service.device.ambientLight.AmbientLightServiceContract;
import ch.quantasy.gateway.service.device.dc.DCServiceContract;
import ch.quantasy.gateway.service.device.linearPoti.LinearPotiServiceContract;
import ch.quantasy.gateway.service.device.motionDetector.MotionDetectorServiceContract;
import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import ch.quantasy.mqtt.agents.GenericAgent;
import ch.quantasy.mqtt.agents.GenericAgentContract;
import ch.quantasy.mqtt.gateway.client.GCEvent;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.mqtt.gateway.client.MessageReceiver;
import ch.quantasy.tinkerforge.device.ambientLight.DeviceIlluminanceCallbackThreshold;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reto
 */
public class OuterLightsAgent extends GenericAgent {

    private final ManagerServiceContract managerServiceContract;
    private final DCServiceContract dcServiceContract;
    private final LinearPotiServiceContract linearPotiServiceContract;
    private List<MotionDetectorServiceContract> motionDetectorServiceContracts;
    private List<AmbientLightServiceContract> ambientLightServiceContracts;

    private final Thread t;
    private final DelayedOff delayedOff;
    private int powerInPercent;

    public OuterLightsAgent(URI mqttURI) throws MqttException {
        super(mqttURI, "wrth563g", new GenericAgentContract("OuterLights", "01"));
        connect();
        powerInPercent = 100;
        delayedOff = new DelayedOff();
        t = new Thread(delayedOff);
        t.start();
        managerServiceContract = new ManagerServiceContract("Manager");
        motionDetectorServiceContracts = new ArrayList<>();
        ambientLightServiceContracts = new ArrayList<>();
        motionDetectorServiceContracts.add(new MotionDetectorServiceContract("kgB", TinkerforgeDeviceClass.MotionDetector.toString()));
        motionDetectorServiceContracts.add(new MotionDetectorServiceContract("kfP", TinkerforgeDeviceClass.MotionDetector.toString()));
        ambientLightServiceContracts.add(new AmbientLightServiceContract("jxr", TinkerforgeDeviceClass.AmbientLight.toString()));
        dcServiceContract = new DCServiceContract("6kP5Zh", TinkerforgeDeviceClass.DC.toString());
        linearPotiServiceContract = new LinearPotiServiceContract("bxJ", TinkerforgeDeviceClass.LinearPoti.toString());

        connectStacks(new TinkerforgeStackAddress("controller01"), new TinkerforgeStackAddress("erdgeschoss"));

        publishIntent(linearPotiServiceContract.INTENT_POSITION_CALLBACK_PERIOD, 100);
        publishIntent(dcServiceContract.INTENT_ACCELERATION, 10000);
        publishIntent(dcServiceContract.INTENT_DRIVER_MODE, 1);
        publishIntent(dcServiceContract.INTENT_PWM_FREQUENCY, 20000);
        publishIntent(dcServiceContract.INTENT_ENABLED, true);

        subscribe(linearPotiServiceContract.EVENT_POSITION, new MessageReceiver() {
            @Override
            public void messageReceived(String topic, byte[] mm) throws Exception {
                GCEvent<Integer>[] positionEvents = toEventArray(mm, Integer.class);
                int position = positionEvents[0].getValue();
                powerInPercent = position;
            }
        });
        for (MotionDetectorServiceContract motionDetectorServiceContract : motionDetectorServiceContracts) {
            subscribe(motionDetectorServiceContract.EVENT_MOTION_DETECTED, new MessageReceiver() {
                @Override
                public void messageReceived(String topic, byte[] mm) throws Exception {
                    delayedOff.delayUntil(System.currentTimeMillis() + (1000 * 60 * 60));
                }
            });
            subscribe(motionDetectorServiceContract.EVENT_DETECTION_CYCLE_ENDED, new MessageReceiver() {
                @Override
                public void messageReceived(String topic, byte[] mm) throws Exception {
                    delayedOff.delayUntil(System.currentTimeMillis() + 20000);
                }
            });
        }
        for (AmbientLightServiceContract ambientLightServiceContract : ambientLightServiceContracts) {
            publishIntent(ambientLightServiceContract.INTENT_DEBOUNCE_PERIOD, 5000);
            publishIntent(ambientLightServiceContract.INTENT_ILLUMINANCE_THRESHOLD, new DeviceIlluminanceCallbackThreshold('o', 20, 100));
            subscribe(ambientLightServiceContract.EVENT_ILLUMINANCE_REACHED, new MessageReceiver() {
                @Override
                public void messageReceived(String topic, byte[] payload) throws Exception {
                    GCEvent<Integer>[] illuminance = toEventArray(payload, Integer.class);
                    if (illuminance[0].getValue() < 100) {
                        delayedOff.setPaused(false);
                    } else if (illuminance[0].getValue() > 20) {
                        delayedOff.setPaused(true);
                    }
                    Logger.getLogger(OuterLightsAgent.class.getName()).log(Level.INFO, "Illuminance: ", illuminance[0]);
                }
            }
            );
        }
    }

    class DelayedOff implements Runnable {

        private long delayUntil;
        private int currentPowerInPercent;
        private boolean isPaused;

        public synchronized void delayUntil(long timeInFuture) {
            if (timeInFuture < System.currentTimeMillis()) {
                return;
            }
            this.delayUntil = timeInFuture;
            this.notifyAll();
        }

        public synchronized void setPaused(boolean isPaused) {
            this.isPaused = isPaused;
            this.notifyAll();
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    while (delayUntil < System.currentTimeMillis() || isPaused) {
                        try {
                            this.wait(10000);
                        } catch (InterruptedException ex) {
                        }
                    }
                    publishIntent(dcServiceContract.INTENT_VELOCITY_VELOCITY, (32767 / 100) * powerInPercent);
                    while (delayUntil > System.currentTimeMillis()) {
                        if (currentPowerInPercent != powerInPercent) {
                            currentPowerInPercent = powerInPercent;
                            publishIntent(dcServiceContract.INTENT_VELOCITY_VELOCITY, (32767 / 100) * currentPowerInPercent);
                        }
                        long delay = delayUntil - System.currentTimeMillis();
                        try {
                            this.wait(delay);
                        } catch (InterruptedException ex) {
                        }
                    }
                    publishIntent(dcServiceContract.INTENT_VELOCITY_VELOCITY, (32767 / 100) * 0);
                }
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
        OuterLightsAgent agent = new OuterLightsAgent(mqttURI);
        System.in.read();
    }

}
