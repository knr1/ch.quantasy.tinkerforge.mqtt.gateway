/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.mqtt.agents.led.abilities;

import ch.quantasy.gateway.service.device.ledStrip.LEDStripServiceContract;
import ch.quantasy.mqtt.agents.led.LEDs20W;
import ch.quantasy.mqtt.gateway.client.GatewayClient;
import ch.quantasy.tinkerforge.device.led.LEDFrame;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author reto
 */
public class Fire extends AnLEDAbility {

        Random random = new Random();

        private final List<LEDFrame> frames;

        public Fire(GatewayClient client, LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            super(client, ledServiceContract, config);
            frames = new ArrayList<>();
        }

        public void run() {
            super.setLEDFrame(getNewLEDFrame());

            LEDFrame leds = super.getNewLEDFrame();
            
            int RED = 255;
            int GREEN = 90;
            int BLUE = 10;
            try {
                while (true) {
                    while (frames.size() < 150) {
                        for (int position = 0; position < leds.getNumberOfLEDs(); position++) {
                            double damper = random.nextDouble() * 0.8;
                            leds.setColor((short) 0, (short) position, (short) Math.max(RED / 2, Math.min(RED, RED * damper)));
                            leds.setColor((short) 1, (short) position, (short) Math.max(GREEN / 2, Math.min(GREEN, GREEN * damper)));
                            leds.setColor((short) 2, (short) position, (short) Math.max(BLUE / 2, Math.min(BLUE, BLUE * damper)));
                        }
                        frames.add(new LEDFrame(leds));
                    }
                    super.setLEDFrames(frames);
                    frames.clear();

                    Thread.sleep(super.getConfig().getFrameDurationInMilliseconds() * 50);

                    synchronized (this) {
                        while (getCounter() > 100) {
                            this.wait(super.getConfig().getFrameDurationInMilliseconds() * 1000);
                        }
                    }
                }
            } catch (InterruptedException ex) {
                super.setLEDFrame(getNewLEDFrame());
            }
        }
    }
