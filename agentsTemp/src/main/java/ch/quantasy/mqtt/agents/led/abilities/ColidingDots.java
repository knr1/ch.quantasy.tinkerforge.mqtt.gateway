package ch.quantasy.mqtt.agents.led.abilities;


import ch.quantasy.gateway.service.device.ledStrip.LEDStripServiceContract;
import ch.quantasy.mqtt.gateway.client.GatewayClient;
import ch.quantasy.tinkerforge.device.led.LEDFrame;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceConfig;
import java.util.ArrayList;
import java.util.List;

public class ColidingDots extends AnLEDAbility {

        private final List<LEDFrame> frames;

        public ColidingDots(GatewayClient gatewayClient, LEDStripServiceContract ledServiceContract, LEDStripDeviceConfig config) {
            super(gatewayClient, ledServiceContract, config);
            frames = new ArrayList<>();
        }

        public void run() {
            super.setLEDFrame(getNewLEDFrame());

            LEDFrame leds = super.getNewLEDFrame();
            LEDFrame flash= super.getNewLEDFrame();
            
            for(int position=0;position<flash.getNumberOfLEDs();position++){
                for(int channel=0;channel<flash.getNumberOfChannels();channel++){
                    flash.setColor(channel, position, (short)255);
                }
            }

            //for (int i = 0; i < leds.getNumberOfChannels(); i++) {
            int i = 0;
            leds.setColor((short) i, (short) 0, (short) 255);
            leds.setColor(i + 1, (short) 0, (short) 255);
            //}
            try {
                LEDFrame newLEDs = super.getNewLEDFrame();
                while (true) {
                    while (frames.size() < 300) {
                        //for (int channel = 0; channel < leds.getNumberOfChannels(); channel++) {
                        int channel = 0;
                        for (int position = 1; position < leds.getNumberOfLEDs(); position++) {
                            newLEDs.setColor((short) channel, position, leds.getColor(channel, position - 1));
                            newLEDs.setColor((short) (channel+1), position-1, leds.getColor(channel+1, position));
                        }
                        newLEDs.setColor((short) channel, 0, leds.getColor(channel, leds.getNumberOfLEDs() - 1));
                        newLEDs.setColor((short) (channel + 1), leds.getNumberOfLEDs()-1, leds.getColor(channel+1, 0));

                        //}
                        LEDFrame tmpLEDs = leds;
                        leds = newLEDs;
                        newLEDs = tmpLEDs;
                        frames.add(new LEDFrame(leds));
                        if(leds.getColor(0, leds.getNumberOfLEDs()/2)!=0){
                            frames.add(flash);
                        }
                    }
                    super.setLEDFrames(frames);
                    frames.clear();

                    Thread.sleep(getConfig().getFrameDurationInMilliseconds() * 50);

                    synchronized (this) {
                        while (getCounter() > 100) {
                            this.wait(getConfig().getFrameDurationInMilliseconds() * 1000);
                        }
                    }
                }
            } catch (InterruptedException ex) {
                super.setLEDFrame(getNewLEDFrame());
            }
        }
    }
