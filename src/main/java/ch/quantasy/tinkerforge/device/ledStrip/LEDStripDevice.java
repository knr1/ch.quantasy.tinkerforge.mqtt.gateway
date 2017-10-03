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
package ch.quantasy.tinkerforge.device.ledStrip;

import ch.quantasy.gateway.intent.ledStrip.LEDFrame;
import ch.quantasy.gateway.intent.ledStrip.LEDStripDeviceConfig;
import ch.quantasy.gateway.intent.ledStrip.LedStripIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LEDStripDevice extends GenericDevice<BrickletLEDStrip, LEDStripDeviceCallback, LedStripIntent> implements BrickletLEDStrip.FrameRenderedListener {

    public static final int DEFAULT_NUMBER_OF_LEDS = 50;

    // Frame duration in Milliseconds 20ms (1000ms / 50frames = 20ms per frame)
    public static final int DEFAULT_FRAME_DURATION_IN_MILLISECONDS = 20;

    // IC-refresh in Hz
    public static final int DEFAULT_CLOCK_FREQUENCY_OF_ICS_IN_HZ = 2000000;

    // ChipType
    public static final LEDStripDeviceConfig.ChipType DEFAULT_CHIP_TYPE = LEDStripDeviceConfig.ChipType.WS2801;

    public static final LEDStripDeviceConfig.ChannelMapping DEFAULT_CHANNEL_MAPPING = LEDStripDeviceConfig.ChannelMapping.RGB;

    private int numberOfWrites;
    private List<LEDFrame.Chunk> chunkList;
    private LEDFrame currentLEDFrame;
    private Thread publisherThread;
    private final Publisher publisher;
    private boolean readyToSend;
    private boolean sent;

    public LEDStripDevice(TinkerforgeStack stack, BrickletLEDStrip device) throws NotConnectedException, TimeoutException {
        super(stack, device, new LedStripIntent());
        this.publisher = new Publisher();
        getIntent().config = new LEDStripDeviceConfig(LEDStripDevice.DEFAULT_CHIP_TYPE, LEDStripDevice.DEFAULT_CLOCK_FREQUENCY_OF_ICS_IN_HZ, LEDStripDevice.DEFAULT_FRAME_DURATION_IN_MILLISECONDS, LEDStripDevice.DEFAULT_NUMBER_OF_LEDS, LEDStripDevice.DEFAULT_CHANNEL_MAPPING);

    }

    @Override
    protected void addDeviceListeners(BrickletLEDStrip device) {
        device.addFrameRenderedListener(this);
        try {
            this.setup();
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void removeDeviceListeners(BrickletLEDStrip device) {
        this.readyToSend = false;
        device.removeFrameRenderedListener(this);
    }

    private void setup() throws TimeoutException, NotConnectedException {
        synchronized (this) {
            this.notify();
            this.readyToSend = true;
        }

        if (publisherThread == null) {
            publisherThread = new Thread(publisher);
            //publisherThread.setDaemon(true);
            publisherThread.start();
        }
    }

    @Override
    public void update(LedStripIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }

        if (intent.config != null) {
            try {
                setNumberOfLEDs(intent.config.getNumberOfLEDs(), intent.config.getChipType().getNumberOfChannels(), intent.config.getNumberOfLEDsPerWrite());
                setChipType(intent.config.getChipType().getType());
                setClockFrequencyOfICsInHz(intent.config.getClockFrequencyOfICsInHz());
                setFrameDurationInMilliseconds(intent.config.getFrameDurationInMilliseconds());
                setChannelMapping(intent.config.getChannelMapping().getMapping());

                LEDStripDeviceConfig.ChipType chipType = LEDStripDeviceConfig.ChipType.getChipTypeFor(getDevice().getChipType(), intent.config.getChipType().getNumberOfChannels());
                getIntent().config = new LEDStripDeviceConfig(chipType, getDevice().getClockFrequency(), getDevice().getFrameDuration(), intent.config.getNumberOfLEDs(), intent.config.getChannelMapping());
                super.getCallback().configurationChanged(getIntent().config);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.LEDFrame != null) {
            publisher.add(intent.LEDFrame);
        }
        if (intent.LEDFrames != null) {
            publisher.add(intent.LEDFrames);
        }

    }

    private synchronized void setNumberOfLEDs(final int numberOfLEDs, final int numberOfChannels, final int numberOfLEDsPerWrite) {
        this.numberOfWrites = (int) Math.ceil((double) numberOfLEDs / numberOfLEDsPerWrite);
        currentLEDFrame = new LEDFrame(numberOfChannels, numberOfLEDs);
    }

    private void setFrameDurationInMilliseconds(
            final int frameDurationInMilliseconds) throws TimeoutException, NotConnectedException {
        if (getDevice() != null) {
            getDevice()
                    .setFrameDuration(frameDurationInMilliseconds);

        }
    }

    private void setChipType(int chipType) throws TimeoutException, NotConnectedException {
        if (getDevice() != null) {
            getDevice().setChipType(chipType);
        }
    }

    private void setClockFrequencyOfICsInHz(final long clockFrequencyOfICsInHz) throws TimeoutException, NotConnectedException {
        if (getDevice() != null) {
            getDevice().setClockFrequency(clockFrequencyOfICsInHz);
        }
    }

    private void setChannelMapping(short channelMapping) throws TimeoutException, NotConnectedException {
        if (getDevice() != null) {
            getDevice().setChannelMapping(channelMapping);
        }
    }

    /**
     * This will guarantee the display of the set LEDs! in the next rendering!
     *
     * @param leds
     */
    public void setRGBLEDs(final LEDFrame leds) {
        LEDStripDeviceConfig localConfig = getIntent().config;
        if (leds == null) {
            return;
        }
        if (localConfig.getNumberOfLEDs() != leds.getNumberOfLEDs()) {
            return;
        }
        if (localConfig.getChipType().getNumberOfChannels() != leds.getNumberOfChannels()) {
            return;
        }

        this.chunkList = leds.getDeltaChunks(currentLEDFrame, getIntent().config.getNumberOfLEDsPerWrite());
        currentLEDFrame = leds;
        sendRGBLEDFrame();
    }

    /**
     * This method is called via setRGBLEDs.
     */
    private void sendRGBLEDFrame() {
        if (getDevice() == null) {
            return;
        }
        synchronized (this) {
            while (!readyToSend) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    //Fine, we just go on
                }

            }
            readyToSend = false;
            sent = false;
        }
        try {
            LEDStripDeviceConfig localConfig = getIntent().config;
            for (LEDFrame.Chunk chunk : chunkList) {
                if (chunk.leds.length == 3) {
                    getDevice()
                            .setRGBValues(chunk.position, (short) (Math.min(chunk.leds[0].length, localConfig.getNumberOfLEDs() - chunk.position)),
                                    chunk.leds[0], chunk.leds[1],
                                    chunk.leds[2]);
                } else if (chunk.leds.length == 4) {
                    getDevice()
                            .setRGBWValues(chunk.position, (short) (Math.min(chunk.leds[0].length, localConfig.getNumberOfLEDs() - chunk.position)),
                                    chunk.leds[0], chunk.leds[1],
                                    chunk.leds[2], chunk.leds[3]);
                }
            }
            sent = true;
        } catch (final TimeoutException e) {
            // Eh... ok
        } catch (final NotConnectedException e) {
            // Well...
        } catch (final NullPointerException e) {
            // Uups, device vanished...
        }
    }

    @Override
    public void frameRendered(final int length) {
        if (readyToSend != true) {
            if (sent != true) {
                super.getCallback().isLaging();
            }
            readyToSend = true;
            super.getCallback().frameRendered(publisher.getQueueSize());
        }
        synchronized (this) {
            this.notifyAll();
        }
    }

    class Publisher implements Runnable {

        private final BlockingDeque<LEDFrame> publishingQueue;

        public Publisher() {
            this.publishingQueue = new LinkedBlockingDeque<>();
        }

        public int getQueueSize() {
            return publishingQueue.size();
        }

        public void add(LEDFrame frame) {
            synchronized (this) {
                if (publishingQueue != null) {
                    publishingQueue.clear();
                    publishingQueue.offer(frame);
                }
            }

        }

        public void add(LEDFrame[] frames) {

            synchronized (this) {
                for (LEDFrame frame : frames) {
                    publishingQueue.offer(frame);
                }
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (LEDStripDevice.this) {
                        setRGBLEDs(publishingQueue.take());
                    }

                } catch (Exception ex) {
                    Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}