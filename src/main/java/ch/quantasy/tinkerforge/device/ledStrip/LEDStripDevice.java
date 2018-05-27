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

import ch.quantasy.gateway.binding.tinkerforge.ledStrip.LEDFrame;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.LEDStripDeviceConfig;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.LedStripIntent;
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

    private List<LEDFrame.Chunk> chunkList;
    private LEDFrame currentLEDFrame;
    private Thread publisherThread;
    private final Publisher publisher;
    private boolean readyToSend;
    private boolean sent;
    private final Object deviceLock = new Object();

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
            this.notifyAll();
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
                LEDStripDeviceConfig config = intent.config;
                setNumberOfLEDs(config.getNumberOfLEDs(), config.getChipType().getNumberOfChannels());
                setChipType(config.getChipType().getType());
                setClockFrequencyOfICsInHz(config.getClockFrequencyOfICsInHz());
                setFrameDurationInMilliseconds(config.getFrameDurationInMilliseconds());
                setChannelMapping(config.getChannelMapping().getMapping());
                synchronized (deviceLock) {
                    LEDStripDeviceConfig.ChipType chipType = LEDStripDeviceConfig.ChipType.getChipTypeFor(getDevice().getChipType(), config.getChipType().getNumberOfChannels());
                    getIntent().config = new LEDStripDeviceConfig(chipType, getDevice().getClockFrequency(), getDevice().getFrameDuration(), intent.config.getNumberOfLEDs(), intent.config.getChannelMapping());
                }
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

    private void setNumberOfLEDs(final int numberOfLEDs, final int numberOfChannels) {
        currentLEDFrame = new LEDFrame(numberOfChannels, numberOfLEDs, null);
    }

    private void setFrameDurationInMilliseconds(
            final int frameDurationInMilliseconds) throws TimeoutException, NotConnectedException {
        synchronized (deviceLock) {
            if (getDevice() != null) {
                getDevice()
                        .setFrameDuration(frameDurationInMilliseconds);

            }
        }
    }

    private void setChipType(int chipType) throws TimeoutException, NotConnectedException {
        synchronized (deviceLock) {
            if (getDevice() != null) {
                getDevice().setChipType(chipType);
            }
        }
    }

    private void setClockFrequencyOfICsInHz(final long clockFrequencyOfICsInHz) throws TimeoutException, NotConnectedException {
        synchronized (deviceLock) {
            if (getDevice() != null) {
                getDevice().setClockFrequency(clockFrequencyOfICsInHz);
            }
        }
    }

    private void setChannelMapping(short channelMapping) throws TimeoutException, NotConnectedException {
        synchronized (deviceLock) {
            if (getDevice() != null) {
                getDevice().setChannelMapping(channelMapping);
            }
        }

    }

    /**
     * This will guarantee the display of the set LEDs! in the next rendering!
     *
     * @param leds
     */
    public synchronized void setRGBLEDs(BlockingDeque<LEDFrame> queue) {
        try {
            final LEDFrame leds = queue.take();
            LEDStripDeviceConfig localConfig = getIntent().config;
            if (leds == null) {
                frameRendered(0);
                return;
            }
            if (localConfig == null) {
                frameRendered(0);
                return;
            }
            if (localConfig.getNumberOfLEDs() != leds.getNumberOfLEDs()) {
                frameRendered(0);

                return;
            }
            if (localConfig.getChipType().getNumberOfChannels() != leds.getNumberOfChannels()) {
                frameRendered(0);
                return;
            }

            this.chunkList = leds.getDeltaChunks(currentLEDFrame, localConfig.getNumberOfLEDsPerWrite());
            currentLEDFrame = leds;

            if (currentLEDFrame.getDurationInMillis() != null) {

                setFrameDurationInMilliseconds(leds.getDurationInMillis());

            } else {
                setFrameDurationInMilliseconds(localConfig.getFrameDurationInMilliseconds());
            }

            sendRGBLEDFrame();
        } catch (Exception ex) {
            Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    this.wait(1000);
                } catch (InterruptedException ex) {
                    //Fine, we just go on
                    Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            sent = false;
        }
        try {
            LEDStripDeviceConfig localConfig = getIntent().config;
            if (localConfig != null) {
                for (LEDFrame.Chunk chunk : chunkList) {
                    if (chunk.leds.length == 3) {
                        synchronized (deviceLock) {
                            getDevice()
                                    .setRGBValues(chunk.position, (short) (Math.min(chunk.leds[0].length, localConfig.getNumberOfLEDs() - chunk.position)),
                                            chunk.leds[0], chunk.leds[1],
                                            chunk.leds[2]);
                        }
                        readyToSend = false;

                    } else if (chunk.leds.length == 4) {
                        synchronized (deviceLock) {
                            getDevice()
                                    .setRGBWValues(chunk.position, (short) (Math.min(chunk.leds[0].length, localConfig.getNumberOfLEDs() - chunk.position)),
                                            chunk.leds[0], chunk.leds[1],
                                            chunk.leds[2], chunk.leds[3]);
                        }
                        readyToSend = false;
                    }
                }
            }
            sent = true;
        } catch (final TimeoutException e) {
            // Eh... ok
            Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, e);

        } catch (final NotConnectedException e) {
            // Well...
            Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, e);

        } catch (final NullPointerException e) {
            // Uups, device vanished...
            Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, e);
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
            //synchronized (publishingQueue) {
            if (publishingQueue != null) {
                publishingQueue.clear();
                try {
                    synchronized (deviceLock) {
                        getDevice().setFrameDuration(1);
                    }
                } catch (TimeoutException ex) {
                    Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotConnectedException ex) {
                    Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, ex);
                }
                publishingQueue.offer(frame);
            }
            //}

        }

        public void add(LEDFrame[] frames) {

            // synchronized (publishingQueue) {
            for (LEDFrame frame : frames) {
                publishingQueue.offer(frame);
                //   }
            }
        }

        @Override
        public void run() {
            while (true) {
                try {

                    //LEDFrame frame = publishingQueue.take();
                    //if (frame != null) {
                    setRGBLEDs(publishingQueue);
                    //}

                } catch (Exception ex) {
                    Logger.getLogger(LEDStripDevice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}
