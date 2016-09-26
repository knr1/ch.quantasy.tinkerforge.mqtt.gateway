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
        On, OnTimer, TransitionToOff, Off, TransitionToOn;
    }
    private final ManagerServiceContract managerServiceContract;
    private final Wave wave1;
    private final Thread waveThread1;
    private final Wave wave2;
    private final Thread waveThread2;
    private Thread timerThread;
    private final int frameDurationInMillis;
    private final int amountOfLEDs;
    private final Agent agent;
    private int delayInMinutes;

    public AmbientLEDLightAgent(URI mqttURI) throws MqttException {
        frameDurationInMillis = 55;
        amountOfLEDs = 120;
        delayInMinutes = 1;
        managerServiceContract = new ManagerServiceContract("Manager");
        agent = new Agent(mqttURI, "349h3fdh", new AgentContract("Agent", "AmbientLEDLight", "r4iu4re98"));
        agent.connect();
        connectRemoteServices(new TinkerforgeStackAddress("lights02"));

        LEDStripServiceContract ledServiceContract1 = new LEDStripServiceContract("jJE", TinkerforgeDeviceClass.LEDStrip.toString());
        LEDStripServiceContract ledServiceContract2 = new LEDStripServiceContract("p5z", TinkerforgeDeviceClass.LEDStrip.toString());

        MotionDetectorServiceContract motionDetectorServiceContract = new MotionDetectorServiceContract("kfP", TinkerforgeDeviceClass.MotionDetector.toString());
        LEDStripDeviceConfig config = new LEDStripDeviceConfig(LEDStripDeviceConfig.ChipType.WS2811, 2000000, frameDurationInMillis, amountOfLEDs, LEDStripDeviceConfig.ChannelMapping.BRG);
        agent.addMessage(ledServiceContract1.INTENT_CONFIG, config);
        agent.addMessage(ledServiceContract2.INTENT_CONFIG, config);

        wave1 = new Wave(ledServiceContract1, config);
        wave2 = new Wave(ledServiceContract2, config);

        agent.subscribe(ledServiceContract1.EVENT_LEDs_RENDERED, wave1);
        agent.subscribe(ledServiceContract2.EVENT_LEDs_RENDERED, wave2);
        waveThread1 = new Thread(wave1);
        waveThread1.start();
        waveThread2 = new Thread(wave2);
        waveThread2.start();

        agent.subscribe(motionDetectorServiceContract.EVENT_MOTION_DETECTED, new MessageConsumer() {
            @Override
            public void messageArrived(Agent agent, String topic, byte[] mm) throws Exception {
                if (timerThread != null) {
                    timerThread.interrupt();
                }
                wave1.setTargetBrightness(1.0, 0.01);
                wave2.setTargetBrightness(1.0, 0.01);
            }
        });
        agent.subscribe(motionDetectorServiceContract.EVENT_DETECTION_CYCLE_ENDED, new MessageConsumer() {
            @Override
            public void messageArrived(Agent agent, String topic, byte[] mm) throws Exception {
                timerThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(delayInMinutes * 60 * 1000);
                            wave1.setTargetBrightness(0.0, 0.001);
                            wave2.setTargetBrightness(0.0, 0.001);
                        } catch (InterruptedException ex) {
                            // Interrupted while sleeping... That is ok....
                        }
                    }
                };
                timerThread.start();
            }
        });
    }

    private void connectRemoteServices(TinkerforgeStackAddress... addresses) {
        for (TinkerforgeStackAddress address : addresses) {
            agent.addMessage(managerServiceContract.INTENT_STACK_ADDRESS_ADD, address);
        }
    }

    public class Wave extends AnLEDAbility {

        private final LEDFrame prototypeLEDFrame;
        private double brightness;

        private double targetBrightness;
        private double step;

        private final List<LEDFrame> frames;

        public synchronized double getTargetBrightness() {
            return targetBrightness;
        }

        public synchronized double getStep() {
            return step;
        }

        public synchronized double getBrightness() {
            return brightness;
        }

        public synchronized void setTargetBrightness(double targetBrightness, double step) {

            if (this.targetBrightness != targetBrightness) {
                clearFrames();
                this.targetBrightness = targetBrightness;
            }
            this.step = step;
            notifyAll();
        }

        /**
         * Can be between 0 and infinity
         *
         * @param brightness
         */
        public synchronized void setBrightness(double brightnessFactor) {
            this.brightness = brightnessFactor;
            this.notifyAll();
        }

        public Wave(LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            super(agent, ledServiceContract, config);
            frames = new ArrayList<>();

            double sineRed = 0;
            double sineGreen = 0;
            double sineBlue = 0;
            prototypeLEDFrame = super.getNewLEDFrame();
            for (int i = 0; i < prototypeLEDFrame.getNumberOfLEDs(); i++) {
                sineRed = Math.sin((i / 120.0) * Math.PI * 2);
                sineGreen = Math.sin((i / 60.0) * Math.PI * 2);
                sineBlue = Math.sin((i / 30.0) * Math.PI * 2);

                prototypeLEDFrame.setColor(0, i, (short) (127.0 + (sineRed * 127.0)));
                prototypeLEDFrame.setColor(1, i, (short) (127.0 + (sineGreen * 127.0)));
                prototypeLEDFrame.setColor(2, i, (short) (127.0 + (sineBlue * 127.0)));
            }
        }

        public void clearFrames() {
            synchronized (frames) {
                frames.clear();
                //Hier noch die LEDs im LEDDevice löschen
            }
        }

        public void run() {
            LEDFrame currentLEDFrame = new LEDFrame(prototypeLEDFrame, 1.0);
            super.addLEDFrame(currentLEDFrame);
            try {
                while (true) {
                    while (frames.size() < 100) {
                        LEDFrame newLEDFrame = super.getNewLEDFrame();
                        synchronized (this) {
                            double targetBrightness = getTargetBrightness();
                            double brightness = getBrightness();
                            double step = getStep();
                            if (brightness > targetBrightness) {
                                this.setBrightness(Math.max(targetBrightness, brightness - step));

                            } else if (brightness < targetBrightness) {
                                this.setBrightness(Math.min(targetBrightness, brightness + step));
                            }
                        }
                        for (int channel = 0; channel < currentLEDFrame.getNumberOfChannels(); channel++) {
                            for (int i = 1; i < currentLEDFrame.getNumberOfLEDs(); i++) {
                                newLEDFrame.setColor(channel, i, currentLEDFrame.getColor(channel, i - 1));
                            }
                            newLEDFrame.setColor(channel, 0, currentLEDFrame.getColor(channel, amountOfLEDs - 1));
                        }
                        synchronized (frames) {
                            frames.add(new LEDFrame(newLEDFrame, brightness));
                        }
                        currentLEDFrame = newLEDFrame;

                    }
                    System.out.println("FRAMES: " + frames.size());
                    synchronized (frames) {
                        super.addLEDFrame(frames);
                        frames.clear();
                    }

                    Thread.sleep(frameDurationInMillis * 50);

                    synchronized (this) {
                        System.out.println(getCounter());
                        while (getCounter() > 100 || (getBrightness() <= 0 && getTargetBrightness() <= 0)) {
                            wait();
                        }
                    }
                }
            } catch (InterruptedException ex) {
                System.out.printf("%s: Is interrupted: ", Thread.currentThread());
            }
        }

//    class MovingDot extends AnLEDAbility {
//
//        public MovingDot(LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
//            super(ledServiceContract, config);
//        }
//
//        public void run() {
//            leds.getColorChannel(0)[0] = 255;
//            leds.getColorChannel(1)[0] = 255;
//            leds.getColorChannel(2)[0] = 255;
//
//            LEDFrame newLEDs = new LEDFrame(amountOfChannels, amountOfLEDs);
//            try {
//                while (true) {
//                    for (int frameCount = 0; frameCount < 100; frameCount++) {
//                        for (int i = 1; i < leds.getColorChannel(0).length; i++) {
//                            newLEDs.getColorChannel(0)[i] = leds.getColorChannel(0)[i - 1];
//                            newLEDs.getColorChannel(1)[i] = leds.getColorChannel(1)[i - 1];
//                            newLEDs.getColorChannel(2)[i] = leds.getColorChannel(2)[i - 1];
//                        }
//                        newLEDs.getColorChannel(0)[0] = leds.getColorChannel(0)[amountOfLEDs - 1];
//                        newLEDs.getColorChannel(1)[0] = leds.getColorChannel(1)[amountOfLEDs - 1];
//                        newLEDs.getColorChannel(2)[0] = leds.getColorChannel(2)[amountOfLEDs - 1];
//                        LEDFrame tmpLEDs = leds;
//                        leds = newLEDs;
//                        newLEDs = tmpLEDs;
//                        frames.add(new LEDFrame(leds));
//                    }
//                    System.out.println("FRAMES:" + frames.size());
//                    agent.addMessage(ledServiceContract.INTENT_FRAMES, frames.toArray(new LEDFrame[frames.size()]));
//
//                    frames.clear();
//
//                    Thread.sleep(frameDurationInMillis * 50);
//
//                    synchronized (this) {
//                        System.out.println(counter);
//                        while (counter > 100) {
//                            this.wait();
//                        }
//                    }
//                }
//            } catch (InterruptedException ex) {
//                agent.addMessage(ledServiceContract.INTENT_FRAME, new LEDFrame(amountOfChannels, amountOfLEDs));
//
//            }
//        }
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
    }
}
