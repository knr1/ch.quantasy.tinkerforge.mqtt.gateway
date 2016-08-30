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
import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import ch.quantasy.mqtt.gateway.agent.AbstractAgent;
import ch.quantasy.mqtt.gateway.agent.AgentContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceConfig;
import ch.quantasy.tinkerforge.device.led.LEDFrame;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class MovingDotAgent extends AbstractAgent<AgentContract> {

    private final ManagerServiceContract managerServiceContract;
    private switcher switcher1;
    private switcher switcher2;

    private final Thread t1;
    private final Thread t2;
    private int frameDurationInMillis;
    private int amountOfLEDs;

    public MovingDotAgent(URI mqttURI) throws MqttException {
        super(mqttURI, "875786cbwe", new AgentContract("Agent","MovingDot","oZUp5z"));
        frameDurationInMillis = 55;
        amountOfLEDs = 120;
        managerServiceContract = new ManagerServiceContract("Manager");
        addMessage(managerServiceContract.INTENT_STACK_ADDRESS_ADD, "Lights01");

        LEDStripServiceContract ledServiceContract1 = new LEDStripServiceContract("oZU", TinkerforgeDeviceClass.LEDStrip.toString());
        LEDStripServiceContract ledServiceContract2 = new LEDStripServiceContract("p5z", TinkerforgeDeviceClass.LEDStrip.toString());
        LEDStripDeviceConfig config = new LEDStripDeviceConfig(LEDStripDeviceConfig.ChipType.WS2811, 2000000, frameDurationInMillis, amountOfLEDs, LEDStripDeviceConfig.ChannelMapping.BRG);

        switcher1 = new switcher(ledServiceContract1, config);
        switcher2 = new switcher(ledServiceContract2, config);

        t1 = new Thread(switcher1);
        t2 = new Thread(switcher2);
        t1.start();
        t2.start();
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        // System.out.println(string + ": " + new String(mm.getPayload()));
        if (string.contains(switcher1.getLedServiceContract().EVENT_LEDs_RENDERED)) {
            switcher1.messageArrived(string, mm);
        }
        if (string.contains(switcher2.getLedServiceContract().EVENT_LEDs_RENDERED)) {
            switcher2.messageArrived(string, mm);
        }

    }

    public void clear() {
        t1.interrupt();
        t2.interrupt();
        t1.yield();
        t2.yield();
        // addMessage(ledServiceContract1.INTENT_FRAME, new LEDFrame(120));
        // addMessage(ledServiceContract2.INTENT_FRAME, new LEDFrame(120));

    }

    class switcher implements Runnable {

        private final LEDStripServiceContract ledServiceContract;

        private int counter = 0;
        List<LEDFrame> frames = new ArrayList<>();
        private LEDFrame leds = new LEDFrame(amountOfLEDs);

        public switcher(LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            this.ledServiceContract = ledServiceContract;
            MovingDotAgent.super.addMessage(ledServiceContract.INTENT_CONFIG, config);
        }

        public LEDStripServiceContract getLedServiceContract() {
            return ledServiceContract;
        }
        
        

        public void messageArrived(String string, MqttMessage mm) throws Exception {
            synchronized (this) {
                LEDStripService.FrameRenderedEvent[] framesRendered = getMapper().readValue(mm.getPayload(), LEDStripService.FrameRenderedEvent[].class);
                if (framesRendered.length > 0) {
                    counter = framesRendered[framesRendered.length - 1].getFramesBuffered();
                }
                this.notifyAll();
            }
        }

        @Override
        public void run() {

            wave();
        }

        private void wave() {

            double sineRed = 0;
            double sineGreen = 0;
            double sineBlue = 0;
            LEDFrame newLEDs = new LEDFrame(amountOfLEDs);

            for (int i = 0; i < leds.getColorChannel(0).length; i++) {
                sineRed = Math.sin((i / 120.0) * Math.PI * 2);
                sineGreen = Math.sin((i / 60.0) * Math.PI * 2);
                sineBlue = Math.sin((i / 30.0) * Math.PI * 2);

                leds.getColorChannel(0)[i] = (short) (127.0 + (sineRed * 127.0));
                leds.getColorChannel(1)[i] = (short) (127.0 + (sineGreen * 127.0));
                leds.getColorChannel(2)[i] = (short) (127.0 + (sineBlue * 127.0));
            }
            try {
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
                    addMessage(ledServiceContract.INTENT_FRAMES, frames.toArray(new LEDFrame[frames.size()]));
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
                Logger.getLogger(MovingDotAgent.class.getName()).log(Level.SEVERE, null, ex);
                addMessage(ledServiceContract.INTENT_FRAME, new LEDFrame(amountOfLEDs));

            }
        }

        private void movingDot() {
            leds.getColorChannel(0)[0] = 255;
            leds.getColorChannel(1)[0] = 255;
            leds.getColorChannel(2)[0] = 255;

            LEDFrame newLEDs = new LEDFrame(amountOfLEDs);
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
                    addMessage(ledServiceContract.INTENT_FRAMES, frames.toArray(new LEDFrame[frames.size()]));

                    frames.clear();

                    Thread.sleep(frameDurationInMillis * 50);

                    synchronized (MovingDotAgent.this) {
                        System.out.println(counter);
                        while (counter > 100) {
                            MovingDotAgent.this.wait();
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MovingDotAgent.class.getName()).log(Level.SEVERE, null, ex);
                addMessage(ledServiceContract.INTENT_FRAME, new LEDFrame(120));

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
        MovingDotAgent agent = new MovingDotAgent(mqttURI);
        System.in.read();
        agent.clear();
    }

}
