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
package ch.quantasy.gateway.message.ledStrip;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.ArraySize;
import ch.quantasy.mqtt.gateway.client.message.annotations.MultiArraySize;
import ch.quantasy.mqtt.gateway.client.message.annotations.NonNull;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 */
public class LEDFrame extends AValidator {

    @NonNull
    @MultiArraySize(values = {
        @ArraySize(min = 1, max = 4)
        ,@ArraySize(min = 1, max = 320)})
    //@Range(from = 0, to = 255)
    private short[][] channels;

    private LEDFrame() {
    }

    public LEDFrame(int amountOfChannels, int amountOfLEDs) {
        channels = new short[amountOfChannels][amountOfLEDs];
    }

    public LEDFrame(LEDFrame frame) {
        this(frame.channels);
    }

    public LEDFrame(short[][] channels) {
        this.channels = new short[channels.length][];
        this.channels[0] = channels[0].clone();

        for (int i = 1; i < this.channels.length; i++) {
            if (channels[i - 1].length != channels[i].length) {
                throw new IllegalArgumentException();
            }
            this.channels[i] = channels[i].clone();
        }
    }

    public short getMaxValue() {
        short maxValue = Short.MIN_VALUE;
        for (int i = 0; i < channels.length; i++) {
            for (int j = 0; j < channels[i].length; j++) {
                if (maxValue < channels[i][j]) {
                    maxValue = channels[i][j];
                }
            }
        }
        return maxValue;
    }

    public short getMinValue() {
        short minValue = Short.MAX_VALUE;
        for (int i = 0; i < channels.length; i++) {
            for (int j = 0; j < channels[i].length; j++) {
                if (minValue > channels[i][j]) {
                    minValue = channels[i][j];
                }
            }
        }
        return minValue;
    }

    public int getNumberOfChannels() {
        return channels.length;
    }

    public int getNumberOfLEDs() {
        return channels[0].length;
    }

    public short getColor(int channel, int position) {
        return channels[channel][position];
    }

    public void setColor(int channel, int position, short color) {
        channels[channel][position] = color;
    }

    public Chunk getChunk(int startPosition, int chunkLength) {
        Chunk chunk = new Chunk(startPosition, new short[getNumberOfChannels()][chunkLength]);
        for (int channel = 0; channel < getNumberOfChannels(); channel++) {
            System.arraycopy(channels[channel],
                    startPosition,
                    chunk.leds[channel],
                    0,
                    Math.min(channels[channel].length - startPosition, chunk.leds[channel].length));
        }
        return chunk;
    }

    /**
     * Returns a new LEDFrame with all input-Frames combined (Max-Value)...
     *
     * @param ledFrames
     * @return
     */
    public LEDFrame combine(LEDFrame... ledFrames) {
        short[][] leds = new short[channels.length][];

        for (LEDFrame ledFrame : ledFrames) {
            if (ledFrame.getNumberOfChannels() != this.getNumberOfChannels()) {
                continue;
            }
            if (ledFrame.getNumberOfLEDs() != this.getNumberOfLEDs()) {
                continue;
            }
            for (int c = 0; c < getNumberOfChannels(); c++) {
                leds[c] = channels[c].clone();
                for (int pos = 0; pos < getNumberOfLEDs(); pos++) {
                    leds[c][pos] = (short) Math.max(leds[c][pos], ledFrame.channels[c][pos]);
                }
            }
        }
        if (leds[0] == null) {
            return this;
        } else {
            return new LEDFrame(leds);
        }
    }

    /**
     * Returns a new LEDFrame with the specified brightness. 0 is true black,
     * 255 is true white
     *
     * @param ledFrame
     * @param brightnessFactor (something between 0.0 and Infinity)
     */
    public LEDFrame(LEDFrame ledFrame, double brightnessFactor) {
        this(ledFrame);
        for (int i = 0; i < channels.length; i++) {
            for (int j = 0; j < channels[i].length; j++) {
                channels[i][j] = (short) Math.min(255, (short) ((channels[i][j] * brightnessFactor) + 0.5));
                channels[i][j] = (short) Math.max(0, channels[i][j]);
                if (channels[i][j] < 0 || channels[i][j] > 255) {
                    Logger.getLogger(LEDFrame.class.getName()).log(Level.SEVERE, "Illegal Argument in frame: " + channels[i][j]);
                }
            }
        }
    }

    public List<Chunk> getDeltaChunks(LEDFrame oldFrame, int chunkLength) {
        List<Chunk> chunkList = new LinkedList<>();
        if (oldFrame == null) {
            return chunkList;
        }
        if (oldFrame.getNumberOfChannels() != this.getNumberOfChannels()) {
            return chunkList;
        }
        if (oldFrame.getNumberOfLEDs() != this.getNumberOfLEDs()) {
            return chunkList;
        }

        for (int position = 0; position < getNumberOfLEDs(); position++) {
            for (int channel = 0; channel < getNumberOfChannels(); channel++) {
                if (this.channels[channel][position] != oldFrame.channels[channel][position]) {
                    chunkList.add(getChunk(position, chunkLength));
                    position += chunkLength - 1;
                    break;
                }
            }
        }
        return chunkList;
    }

    public static class Chunk {

        public int position;
        public short[][] leds;

        public Chunk(int position, short[][] leds) {
            this.position = position;
            this.leds = leds;
        }
    }
}
