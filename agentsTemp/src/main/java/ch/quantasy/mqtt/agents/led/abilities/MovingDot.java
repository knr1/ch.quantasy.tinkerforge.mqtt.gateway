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

/**
 *
 * @author reto
 */
class MovingDot extends AnLEDAbility {
        private final List<LEDFrame> frames;

        public MovingDot(GatewayClient gatewayClient, LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            super(gatewayClient, ledServiceContract, config);
            frames=new ArrayList<>();
        }

        public void run() {
            LEDFrame leds = super.getNewLEDFrame();

            for (int i = 0; i < leds.getNumberOfChannels(); i++) {
                leds.setColor((short) i, (short) 0, (short) 255);
            }
            try {
                LEDFrame newLEDs = super.getNewLEDFrame();
                while (true) {
                    while (frames.size() < 150) {
                        for (int led = 1; led < leds.getNumberOfLEDs(); led++) {
                            for (int channel = 0; channel < leds.getNumberOfChannels(); channel++) {
                                newLEDs.setColor((short) channel, (short) led, leds.getColor(channel, led - 1));
                            }
                        }
                        LEDFrame tmpLEDs = leds;
                        leds = newLEDs;
                        newLEDs = tmpLEDs;
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