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
package ch.quantasy.mqtt.agents.led;

import ch.quantasy.mqtt.agents.led.abilities.WaveAdjustableBrightness;
import ch.quantasy.gateway.service.device.ledStrip.LEDStripServiceContract;
import ch.quantasy.gateway.service.device.motionDetector.MotionDetectorServiceContract;
import ch.quantasy.gateway.service.device.rotaryEncoder.RotaryEncoderServiceContract;
import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import ch.quantasy.mqtt.agents.GenericAgent;
import ch.quantasy.mqtt.agents.GenericAgentContract;
import ch.quantasy.mqtt.gateway.client.GCEvent;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceConfig;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.mqtt.gateway.client.MessageReceiver;

/**
 *
 * @author reto
 */
public class AmbientLEDLightAgent extends GenericAgent {

    private final ManagerServiceContract managerServiceContract;
    private final List<WaveAdjustableBrightness> waveList;
    private Thread timerThread;
    private final int frameDurationInMillis;
    private final int amountOfLEDs;
    private int delayInMinutes;

    public AmbientLEDLightAgent(URI mqttURI) throws MqttException {
        super(mqttURI, "349h3fdh", new GenericAgentContract("AmbientLEDLight", "test01"));
        connect();
        frameDurationInMillis = 55;
        amountOfLEDs = 120;
        delayInMinutes = 1;
        waveList = new ArrayList<>();
        managerServiceContract = new ManagerServiceContract("Manager");

        connectStacks(getStackAddresses());

        RotaryEncoderServiceContract rotaryEncoderServiceContract = new RotaryEncoderServiceContract("je3", TinkerforgeDeviceClass.RotaryEncoder.toString());

        MotionDetectorServiceContract motionDetectorServiceContract = new MotionDetectorServiceContract("kfP", TinkerforgeDeviceClass.MotionDetector.toString());
        LEDStripDeviceConfig config = new LEDStripDeviceConfig(LEDStripDeviceConfig.ChipType.WS2812RGBW, 2000000, frameDurationInMillis, amountOfLEDs, LEDStripDeviceConfig.ChannelMapping.BRGW);
        subscribe(rotaryEncoderServiceContract.EVENT_COUNT, new Brightness());
        subscribe(rotaryEncoderServiceContract.EVENT_PRESSED, new MessageReceiver() {
            @Override
            public void messageReceived(String topic, byte[] mm) throws Exception {
                for (WaveAdjustableBrightness wave : waveList) {
                    if (wave.getTargetBrightness() > 0) {
                        wave.clearFrames();
                        wave.setTargetBrightness(0, 1.0);
                    } else {
                        wave.setTargetBrightness(1, 1.0);
                    }
                }
            }
        });
        publishIntent(rotaryEncoderServiceContract.INTENT_COUNT_CALLBACK_PERIOD, 100);
        LEDStripServiceContract ledServiceContract = new LEDStripServiceContract("jJE", TinkerforgeDeviceClass.LEDStrip.toString());
        publishIntent(ledServiceContract.INTENT_CONFIG, config);

        waveList.add(new WaveAdjustableBrightness(this, ledServiceContract, config));
        for (WaveAdjustableBrightness wave : waveList) {
            new Thread(wave).start();
        }

        subscribe(motionDetectorServiceContract.EVENT_MOTION_DETECTED, new MessageReceiver() {
            @Override
            public void messageReceived(String topic, byte[] mm) throws Exception {
                if (timerThread != null) {
                    timerThread.interrupt();
                }
                for (WaveAdjustableBrightness wave : waveList) {
                    wave.setTargetBrightness(1.0, 0.01);

                }
            }
        });
        subscribe(motionDetectorServiceContract.EVENT_DETECTION_CYCLE_ENDED, new MessageReceiver() {
            @Override
            public void messageReceived(String topic, byte[] mm) throws Exception {
                timerThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(delayInMinutes * 60 * 1000);
                            for (WaveAdjustableBrightness wave : waveList) {
                                wave.setTargetBrightness(0.0, 0.001);
                            }
                        } catch (InterruptedException ex) {
                            // Interrupted while sleeping... That is ok....
                        }
                    }
                };
                timerThread.start();
            }
        });
    }

    public TinkerforgeStackAddress[] getStackAddresses() {
        return new TinkerforgeStackAddress[]{new TinkerforgeStackAddress("lights02"), new TinkerforgeStackAddress("erdgeschoss")};
    }

    public void changeAmbientBrightness(double ambientBrightness) {
        for (WaveAdjustableBrightness wave : waveList) {
            wave.changeAmbientBrightness(ambientBrightness);
        }
    }

    public class Brightness implements MessageReceiver {

        private Integer latestCount;

        @Override
        public void messageReceived(String topic, byte[] mm) throws Exception {
            GCEvent<Integer>[] countEvents = toEventArray(mm, Integer.class);
            if (latestCount == null) {
                latestCount = countEvents[0].getValue();
            }
            int difference = latestCount;
            latestCount = countEvents[0].getValue();
            changeAmbientBrightness((difference - latestCount) / 100.0);
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
    }
}
