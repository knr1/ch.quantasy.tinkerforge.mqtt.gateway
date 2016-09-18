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
package ch.quantasy.gateway.agent.led;

import ch.quantasy.gateway.service.device.ledStrip.LEDStripService;
import ch.quantasy.gateway.service.device.ledStrip.LEDStripServiceContract;
import ch.quantasy.gateway.service.device.motionDetector.MotionDetectorServiceContract;
import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import ch.quantasy.mqtt.gateway.agent.Agent;
import ch.quantasy.mqtt.gateway.agent.AgentContract;
import ch.quantasy.mqtt.gateway.agent.MessageConsumer;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceConfig;
import ch.quantasy.tinkerforge.device.led.LEDFrame;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDeviceCallback;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class AmbientLEDLightAgent {

    enum LightsState {
        On, Transition, Off;
    }
    private final ManagerServiceContract managerServiceContract;
    private final Wave wave1;
    private final Wave wave2;
    private final SunSet sunSet1;
    private final SunSet sunSet2;

    private Thread waveThread1;
    private Thread waveThread2;
    private Thread timerThread;
    private final int frameDurationInMillis;
    private final int amountOfLEDs;
    private final Agent agent;
    private final int amountOfChannels;
    private int delayInMinutes;

    public AmbientLEDLightAgent(URI mqttURI) throws MqttException {
        frameDurationInMillis = 55;
        amountOfLEDs = 120;
        delayInMinutes = 1;
        managerServiceContract = new ManagerServiceContract("Manager");
        agent = new Agent(mqttURI, "349h3fdh", new AgentContract("Agent", "AmbientLEDLight", "r4iu4re98"));
        agent.connect();
        connectRemoteServices(new TinkerforgeStackAddress("lights01"));

        LEDStripServiceContract ledServiceContract1 = new LEDStripServiceContract("oZU", TinkerforgeDeviceClass.LEDStrip.toString());
        LEDStripServiceContract ledServiceContract2 = new LEDStripServiceContract("p5z", TinkerforgeDeviceClass.LEDStrip.toString());
        MotionDetectorServiceContract motionDetectorServiceContract = new MotionDetectorServiceContract("kgx", TinkerforgeDeviceClass.MotionDetector.toString());
        LEDStripDeviceConfig config = new LEDStripDeviceConfig(LEDStripDeviceConfig.ChipType.WS2811, 2000000, frameDurationInMillis, amountOfLEDs, LEDStripDeviceConfig.ChannelMapping.BRG);
        amountOfChannels = config.getChipType().getNumberOfChannels();

        wave1 = new Wave(ledServiceContract1, config);
        wave2 = new Wave(ledServiceContract2, config);
        sunSet1 = new SunSet(ledServiceContract1, config);
        sunSet2 = new SunSet(ledServiceContract2, config);

        agent.subscribe(ledServiceContract1.EVENT_LEDs_RENDERED, wave1);
        agent.subscribe(ledServiceContract2.EVENT_LEDs_RENDERED, wave2);
        agent.subscribe(ledServiceContract1.EVENT_LEDs_RENDERED, sunSet1);
        agent.subscribe(ledServiceContract2.EVENT_LEDs_RENDERED, sunSet2);

        agent.subscribe(motionDetectorServiceContract.EVENT_MOTION_DETECTED, new MessageConsumer() {
            @Override
            public void messageArrived(Agent agent, String topic, byte[] mm) throws Exception {
                setLightsState(LightsState.On);
            }
        });
        agent.subscribe(motionDetectorServiceContract.EVENT_DETECTION_CYCLE_ENDED, new MessageConsumer() {
            @Override
            public void messageArrived(Agent agent, String topic, byte[] mm) throws Exception {
                setLightsState(LightsState.Transition);
            }
        });

    }

    public synchronized void setLightsState(LightsState lightState) {
        if (lightState == LightsState.On) {
            if (timerThread != null) {
                timerThread.interrupt();
            }
            switchOn();
        }
        if (lightState == LightsState.Transition) {
            timerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(delayInMinutes * 60 * 1000);
                        switchOff(delayInMinutes * 60 * 1000);
                    } catch (InterruptedException ex) {
                        // Interrupted while sleeping... That is ok....
                    }
                }
            };
            timerThread.start();
        }
    }

    public synchronized void switchOn() {
        Thread.interrupted();
        if (waveThread1 != null || waveThread1 != null) {
            return;
        }
        if (sunSetThread1 != null) {
            sunSetThread1.interrupt();
        }
        if (sunSetThread2 != null) {
            sunSetThread2.interrupt();
        }

        waveThread1 = new Thread(wave1);
        waveThread2 = new Thread(wave2);
        waveThread1.start();
        waveThread2.start();
    }

    private Thread sunSetThread1;
    private Thread sunSetThread2;

    public void switchOff(long timeInMillis) {
        synchronized (this) {
            if (waveThread1 == null || waveThread2 == null) {
                return;
            }
            waveThread1.interrupt();
            waveThread2.interrupt();
            waveThread1.yield();
            waveThread2.yield();
            waveThread1 = null;
            waveThread2 = null;
        }
        sunSetThread1 = new Thread(sunSet1);
        sunSetThread2 = new Thread(sunSet2);
        sunSet1.setLEDs(wave1.getLEDs());
        sunSet2.setLEDs(wave2.getLEDs());

        sunSetThread1.start();
        sunSetThread2.start();
        //agent.addMessage(wave1.getLedServiceContract().INTENT_FRAME, new LEDFrame(amountOfChannels, amountOfLEDs));
        //agent.addMessage(wave2.getLedServiceContract().INTENT_FRAME, new LEDFrame(amountOfChannels, amountOfLEDs));

    }

    private void connectRemoteServices(TinkerforgeStackAddress... addresses) {
        for (TinkerforgeStackAddress address : addresses) {
            agent.addMessage(managerServiceContract.INTENT_STACK_ADDRESS_ADD, address);
        }
    }

    public abstract class LEDAbility implements Runnable, MessageConsumer {

        protected final LEDStripServiceContract ledServiceContract;

        protected int counter = 0;
        protected List<LEDFrame> frames = new ArrayList<>();
        protected LEDFrame leds = new LEDFrame(amountOfChannels, amountOfLEDs);

        public LEDAbility(LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            this.ledServiceContract = ledServiceContract;
            agent.addMessage(ledServiceContract.INTENT_CONFIG, config);
        }

        public LEDStripServiceContract getLedServiceContract() {
            return ledServiceContract;
        }

        public LEDFrame getLEDs() {
            return leds;
        }

        public void setLEDs(LEDFrame leds) {
            this.leds = leds;
        }

        @Override
        public void messageArrived(Agent agent, String topic, byte[] payload) throws Exception {
            synchronized (this) {
                LEDStripService.FrameRenderedEvent[] framesRendered = agent.getMapper().readValue(payload, LEDStripService.FrameRenderedEvent[].class);
                if (framesRendered.length > 0) {
                    counter = framesRendered[framesRendered.length - 1].getFramesBuffered();
                    this.notifyAll();
                }
            }
        }

    }

    class SunSet extends AmbientLEDLightAgent.LEDAbility {

        public SunSet(LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            super(ledServiceContract, config);
        }

        public void run() {
            agent.addMessage(ledServiceContract.INTENT_FRAME, leds);

            double[][] floatingLEDs = new double[amountOfChannels][amountOfLEDs];
            for (int i = 0; i < amountOfLEDs; i++) {
                for (int j = 0; j < amountOfChannels; j++) {
                    floatingLEDs[j][i] = leds.getColorChannel(j)[i];
                }
            }
            try {
                while (true) {
                    for (int frameCount = 0; frameCount < 100; frameCount++) {
                        LEDFrame newLEDs = new LEDFrame(amountOfChannels, amountOfLEDs);
                        int sum = 0;
                        for (int i = 0; i < amountOfLEDs; i++) {
                            for (int j = 0; j < amountOfChannels; j++) {
                                floatingLEDs[j][i] *= 0.993;
                                newLEDs.getColorChannel(j)[i] = (short) (floatingLEDs[j][i] + 0.5);
                                sum += newLEDs.getColorChannel(j)[i];
                            }
                        }
                        frames.add(newLEDs);
                        synchronized (this) {
                            if (sum == 0) {
                                agent.addMessage(ledServiceContract.INTENT_FRAMES, frames.toArray(new LEDFrame[frames.size()]));
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                    System.out.println("FRAMES: " + frames.size());
                    agent.addMessage(ledServiceContract.INTENT_FRAMES, frames.toArray(new LEDFrame[frames.size()]));
                    frames.clear();
                    Thread.sleep(frameDurationInMillis * 50);

                    synchronized (this) {
                        System.out.println(counter);
                        while (counter > 100) {
                            wait();
                        }
                    }
                }
            } catch (InterruptedException ex) {
                agent.addMessage(ledServiceContract.INTENT_FRAME, leds);
            }
        }
    }

    public class Wave extends LEDAbility {

        public Wave(LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            super(ledServiceContract, config);
            double sineRed = 0;
            double sineGreen = 0;
            double sineBlue = 0;

            for (int i = 0; i < leds.getColorChannel(0).length; i++) {
                sineRed = Math.sin((i / 120.0) * Math.PI * 2);
                sineGreen = Math.sin((i / 60.0) * Math.PI * 2);
                sineBlue = Math.sin((i / 30.0) * Math.PI * 2);

                leds.getColorChannel(0)[i] = (short) (127.0 + (sineRed * 127.0));
                leds.getColorChannel(1)[i] = (short) (127.0 + (sineGreen * 127.0));
                leds.getColorChannel(2)[i] = (short) (127.0 + (sineBlue * 127.0));
            }
        }

        public void run() {
            agent.addMessage(ledServiceContract.INTENT_FRAME, leds);

            try {
                LEDFrame newLEDs = new LEDFrame(amountOfChannels, amountOfLEDs);
                while (true) {
                    for (int frameCount = 0; frameCount < 100; frameCount++) {
                        for (int i = 1; i < leds.getColorChannel(0).length; i++) {
                            newLEDs.getColorChannel(0)[i] = leds.getColorChannel(0)[i - 1];
                            newLEDs.getColorChannel(1)[i] = leds.getColorChannel(1)[i - 1];
                            newLEDs.getColorChannel(2)[i] = leds.getColorChannel(2)[i - 1];
                        }
                        newLEDs.getColorChannel(0)[0] = leds.getColorChannel(1)[amountOfLEDs - 1];
                        newLEDs.getColorChannel(1)[0] = leds.getColorChannel(2)[amountOfLEDs - 1];
                        newLEDs.getColorChannel(2)[0] = leds.getColorChannel(0)[amountOfLEDs - 1];
                        LEDFrame tmpLEDs = leds;
                        leds = newLEDs;
                        newLEDs = tmpLEDs;
                        frames.add(new LEDFrame(leds));
                    }
                    System.out.println("FRAMES: " + frames.size());
                    agent.addMessage(ledServiceContract.INTENT_FRAMES, frames.toArray(new LEDFrame[frames.size()]));
                    frames.clear();

                    Thread.sleep(frameDurationInMillis * 50);

                    synchronized (this) {
                        System.out.println(counter);
                        while (counter > 100) {
                            wait();
                        }
                    }
                }
            } catch (InterruptedException ex) {
                agent.addMessage(ledServiceContract.INTENT_FRAME, leds);
            }
        }
    }

    class MovingDot extends LEDAbility {

        public MovingDot(LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            super(ledServiceContract, config);
        }

        public void run() {
            leds.getColorChannel(0)[0] = 255;
            leds.getColorChannel(1)[0] = 255;
            leds.getColorChannel(2)[0] = 255;

            LEDFrame newLEDs = new LEDFrame(amountOfChannels, amountOfLEDs);
            try {
                while (true) {
                    for (int frameCount = 0; frameCount < 100; frameCount++) {
                        for (int i = 1; i < leds.getColorChannel(0).length; i++) {
                            newLEDs.getColorChannel(0)[i] = leds.getColorChannel(0)[i - 1];
                            newLEDs.getColorChannel(1)[i] = leds.getColorChannel(1)[i - 1];
                            newLEDs.getColorChannel(2)[i] = leds.getColorChannel(2)[i - 1];
                        }
                        newLEDs.getColorChannel(0)[0] = leds.getColorChannel(0)[amountOfLEDs - 1];
                        newLEDs.getColorChannel(1)[0] = leds.getColorChannel(1)[amountOfLEDs - 1];
                        newLEDs.getColorChannel(2)[0] = leds.getColorChannel(2)[amountOfLEDs - 1];
                        LEDFrame tmpLEDs = leds;
                        leds = newLEDs;
                        newLEDs = tmpLEDs;
                        frames.add(new LEDFrame(leds));
                    }
                    System.out.println("FRAMES:" + frames.size());
                    agent.addMessage(ledServiceContract.INTENT_FRAMES, frames.toArray(new LEDFrame[frames.size()]));

                    frames.clear();

                    Thread.sleep(frameDurationInMillis * 50);

                    synchronized (this) {
                        System.out.println(counter);
                        while (counter > 100) {
                            this.wait();
                        }
                    }
                }
            } catch (InterruptedException ex) {
                agent.addMessage(ledServiceContract.INTENT_FRAME, new LEDFrame(amountOfChannels, amountOfLEDs));

            }
        }

    }

    public static void main(String[] args) throws Throwable {
        URI mqttURI = URI.create("tcp://127.0.0.1:1883");
        if (args.length > 0) {
            mqttURI = URI.create(args[0]);
        } else {
            System.out.printf("Per default, 'tcp://127.0.0.1:1883' is chosen.\nYou can provide another address as first argument i.e.: tcp://iot.eclipse.org:1883\n");
        }
        System.out.printf("\n%s will be used as broker address.\n", mqttURI);
        AmbientLEDLightAgent agent = new AmbientLEDLightAgent(mqttURI);
        System.in.read();
        agent.switchOff(0);
    }
}
