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
public class WaveAdjustableBrightness extends AnLEDAbility {

    private final LEDFrame prototypeLEDFrame;
    private double brightness = 1;
    private double ambientBrightness;

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
            synchronized (frames) {
                frames.clear();
            }
            this.targetBrightness = targetBrightness;
        }
        this.step = step;
        notifyAll();
    }

    /**
     * Can be between 0 and infinity
     *
     * @param brightness
     * @param brightness
     */
    public synchronized void setBrightness(double brightness) {
        this.brightness = brightness;
        this.notifyAll();
    }

    public synchronized void changeAmbientBrightness(double ambientBrightness) {

        this.ambientBrightness += ambientBrightness;
        this.ambientBrightness = Math.min(0, Math.max(-1, this.ambientBrightness));
        this.notifyAll();
        super.setLEDFrame(new LEDFrame(prototypeLEDFrame, Math.max(0, Math.min(1, brightness + this.ambientBrightness))));

    }

    public synchronized double getAmbientBrightness() {
        return ambientBrightness;
    }

    public WaveAdjustableBrightness(GatewayClient gatewayClient, LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
        super(gatewayClient, ledServiceContract, config);
        gatewayClient.publishIntent(ledServiceContract.INTENT_CONFIG, config);

        frames = new ArrayList<>();

        double[] sineChannels = new double[super.getConfig().getChipType().getNumberOfChannels()];

        prototypeLEDFrame = super.getNewLEDFrame();
        for (int i = 0; i < prototypeLEDFrame.getNumberOfLEDs(); i++) {
            for (int channel = 0; channel < sineChannels.length; channel++) {
                sineChannels[channel] = Math.sin((i / (120.0 - (30 * channel))) * Math.PI * 2);
                prototypeLEDFrame.setColor(channel, i, (short) (127.0 + (sineChannels[channel] * 127.0)));

            }
        }
        gatewayClient.subscribe(ledServiceContract.EVENT_LEDs_RENDERED, this);
    }

    public void clearFrames() {
        synchronized (this) {
            synchronized (frames) {
                frames.clear();
            }
            super.setLEDFrame(super.getNewLEDFrame());
        }
    }

    public void run() {
        LEDFrame currentLEDFrame = new LEDFrame(prototypeLEDFrame, 1.0);
        super.setLEDFrame(currentLEDFrame);
        try {
            short maxValue = 0;
            while (true) {
                while (frames.size() < 150 && (getBrightness() + getAmbientBrightness() > 0 || getTargetBrightness() > 0) && (getAmbientBrightness() >= -1)) {
                    LEDFrame newLEDFrame = super.getNewLEDFrame();
                    synchronized (this) {
                        double targetBrightness = getTargetBrightness();
                        double brightness = getBrightness();
                        double ambientBrightness = getAmbientBrightness();
                        double step = getStep();
                        if (brightness + ambientBrightness > targetBrightness) {
                            brightness = Math.max(brightness - step, targetBrightness - ambientBrightness);
                            this.setBrightness(brightness);
                        } else if (brightness < targetBrightness) {
                            brightness = Math.min(brightness + step, targetBrightness);
                            this.setBrightness(brightness);
                        }
                    }
                    for (int channel = 0; channel < currentLEDFrame.getNumberOfChannels(); channel++) {
                        for (int i = 1; i < currentLEDFrame.getNumberOfLEDs(); i++) {
                            newLEDFrame.setColor(channel, i, currentLEDFrame.getColor(channel, i - 1));
                        }
                        newLEDFrame.setColor(channel, 0, currentLEDFrame.getColor(channel, getConfig().getNumberOfLEDs() - 1));
                    }
                    synchronized (frames) {
                        frames.add(new LEDFrame(newLEDFrame, Math.max(0, Math.min(1, brightness + ambientBrightness))));
                    }
                    currentLEDFrame = newLEDFrame;
                }
                synchronized (frames) {
                    super.setLEDFrames(frames);
                    if (!frames.isEmpty()) {
                        maxValue = frames.get(frames.size() - 1).getMaxValue();

                    }

                    frames.clear();
                }
                Thread.sleep(getConfig().getFrameDurationInMilliseconds() * 20);
                synchronized (this) {
                    while (getCounter() > 100 || (getBrightness() + getAmbientBrightness() <= 0 && getTargetBrightness() <= 0) || (getAmbientBrightness() <= -1 && maxValue == 0)) {
                        wait(getConfig().getFrameDurationInMilliseconds() * 1000);
                    }
                }

            }
        } catch (InterruptedException ex) {
            System.out.printf("%s: Is interrupted: ", Thread.currentThread());
        }

    }
}
