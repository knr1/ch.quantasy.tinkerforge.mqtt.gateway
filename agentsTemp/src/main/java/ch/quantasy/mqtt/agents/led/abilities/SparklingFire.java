/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.mqtt.agents.led.abilities;

import ch.quantasy.gateway.service.device.ledStrip.LEDStripServiceContract;
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
public class SparklingFire extends AnLEDAbility {

    Random random = new Random();

    private final List<LEDFrame> frames;

    public SparklingFire(GatewayClient gatewayClient, LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
        super(gatewayClient, ledServiceContract, config);
        frames = new ArrayList<>();
    }

    public void run() {
        super.setLEDFrame(getNewLEDFrame());

        LEDFrame leds = super.getNewLEDFrame();
        LEDFrame flash = super.getNewLEDFrame();

        for (int position = 0; position < flash.getNumberOfLEDs(); position++) {
            for (int channel = 0; channel < flash.getNumberOfChannels(); channel++) {
                flash.setColor(channel, position, (short) 255);
            }
        }
        super.setLEDFrame(flash);

        int RED = 255;
        int GREEN = 90;
        int BLUE = 10;
        for (int position = 0; position < leds.getNumberOfLEDs(); position++) {
            double damper = random.nextDouble();
            leds.setColor((short) 0, (short) position, (short) Math.max(RED / 2, Math.min(RED, RED * damper)));
            leds.setColor((short) 1, (short) position, (short) Math.max(GREEN / 2, Math.min(GREEN, GREEN * damper)));
            leds.setColor((short) 2, (short) position, (short) Math.max(BLUE / 2, Math.min(BLUE, BLUE * damper)));

        }
        try {
            while (true) {
                while (frames.size() < 150) {
                    for (int position = 1; position < leds.getNumberOfLEDs(); position++) {
                        double damper = random.nextDouble();
                        leds.setColor((short) 0, (short) position, (short) Math.max(RED / 2, Math.min(RED, RED * damper)));
                        leds.setColor((short) 1, (short) position, (short) Math.max(GREEN / 2, Math.min(GREEN, GREEN * damper)));
                        leds.setColor((short) 2, (short) position, (short) Math.max(BLUE / 2, Math.min(BLUE, BLUE * damper)));
                    }
                    LEDFrame sparkly = new LEDFrame(leds);
                    for (int position = 0; position < leds.getNumberOfLEDs(); position++) {
                        if (random.nextInt(100) == 0) {
                            for (int channel = 0; channel < super.getConfig().getChipType().numberOfChannels; channel++) {
                                sparkly.setColor((short) channel, (short) position, (short) 255);
                            }
                        }
                    }
                    frames.add(new LEDFrame(sparkly));
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
