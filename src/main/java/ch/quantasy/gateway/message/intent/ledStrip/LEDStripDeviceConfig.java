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
package ch.quantasy.gateway.message.intent.ledStrip;

import ch.quantasy.gateway.message.annotations.AValidator;
import ch.quantasy.gateway.message.annotations.NonNull;
import ch.quantasy.gateway.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinkerforge.BrickletLEDStrip;
import java.util.Objects;

/**
 *
 * @author reto
 */
public class LEDStripDeviceConfig extends AValidator {

    private transient final static int NUMBER_OF_LEDS_PER_WRITE = 48;

    public static enum ChipType implements Validator {
        WS2801(BrickletLEDStrip.CHIP_TYPE_WS2801, 3), WS2811(BrickletLEDStrip.CHIP_TYPE_WS2811, 3), WS2812(BrickletLEDStrip.CHIP_TYPE_WS2812, 3), WS2812RGBW(BrickletLEDStrip.CHIP_TYPE_WS2812, 4), LPD8806(BrickletLEDStrip.CHIP_TYPE_LPD8806, 3), APA102(BrickletLEDStrip.CHIP_TYPE_APA102, 3);
        public final int type;
        public final int numberOfChannels;

        private ChipType(int type, int numberOfChannels) {
            this.type = type;
            this.numberOfChannels = numberOfChannels;
        }

        public int getType() {
            return type;
        }

        public int getNumberOfChannels() {
            return numberOfChannels;
        }

        public static ChipType getChipTypeFor(int numericChipType, int numberOfChannels) throws IllegalArgumentException {
            for (ChipType chipType : values()) {
                if (chipType.type == numericChipType && chipType.numberOfChannels == numberOfChannels) {
                    return chipType;
                }
            }
            throw new IllegalArgumentException("Not supported: " + numericChipType);
        }

        @Override
        public boolean isValid() {
            try {
                getChipTypeFor(numberOfChannels, numberOfChannels);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

    }

    public static enum ChannelMapping implements Validator {
        BGR(BrickletLEDStrip.CHANNEL_MAPPING_BGR), BGRW(BrickletLEDStrip.CHANNEL_MAPPING_BGRW), BGWR(BrickletLEDStrip.CHANNEL_MAPPING_BGWR),
        BRG(BrickletLEDStrip.CHANNEL_MAPPING_BRG), BRGW(BrickletLEDStrip.CHANNEL_MAPPING_BRGW), BRWG(BrickletLEDStrip.CHANNEL_MAPPING_BRWG), BWGR(BrickletLEDStrip.CHANNEL_MAPPING_BWGR),
        BWRG(BrickletLEDStrip.CHANNEL_MAPPING_BWRG), GBR(BrickletLEDStrip.CHANNEL_MAPPING_GBR), GBRW(BrickletLEDStrip.CHANNEL_MAPPING_GBRW), GBWR(BrickletLEDStrip.CHANNEL_MAPPING_GBWR),
        GRB(BrickletLEDStrip.CHANNEL_MAPPING_GRB), GRBW(BrickletLEDStrip.CHANNEL_MAPPING_GRBW), GRWB(BrickletLEDStrip.CHANNEL_MAPPING_GRWB), GWBR(BrickletLEDStrip.CHANNEL_MAPPING_GWBR),
        GWRB(BrickletLEDStrip.CHANNEL_MAPPING_GWRB), RBG(BrickletLEDStrip.CHANNEL_MAPPING_RBG), RBGW(BrickletLEDStrip.CHANNEL_MAPPING_RBGW), RBWG(BrickletLEDStrip.CHANNEL_MAPPING_RBWG),
        RGB(BrickletLEDStrip.CHANNEL_MAPPING_RGB), RGBW(BrickletLEDStrip.CHANNEL_MAPPING_RGBW), RGWB(BrickletLEDStrip.CHANNEL_MAPPING_RGWB), RWBG(BrickletLEDStrip.CHANNEL_MAPPING_RWBG),
        RWGB(BrickletLEDStrip.CHANNEL_MAPPING_RWGB);

        public final short mapping;

        private ChannelMapping(short mapping) {
            this.mapping = mapping;
        }

        public short getMapping() {
            return mapping;
        }

        public static ChannelMapping getChannelMappingFor(int i) throws IllegalArgumentException {
            for (ChannelMapping channelMapping : values()) {
                if (channelMapping.mapping == i) {
                    return channelMapping;
                }
            }
            throw new IllegalArgumentException("Not supported: " + i);
        }

        @Override
        public boolean isValid() {
            try {
                getChannelMappingFor(mapping);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
    @NonNull
    private ChipType chipType;
    @Range(from = 0)
    private int frameDurationInMilliseconds;
    @Range(from = 10000, to = 2000000)
    private long clockFrequencyOfICsInHz;
    @Range(from = 0, to = 320)
    private int numberOfLEDs;
    @NonNull
    private ChannelMapping channelMapping;

    private LEDStripDeviceConfig() {
    }

    public int getNumberOfLEDsPerWrite() {
        return NUMBER_OF_LEDS_PER_WRITE / chipType.getNumberOfChannels();
    }

    public LEDStripDeviceConfig(String chipType, long clockFrequencyOfICsInHz, int frameDurationInMilliseconds, int numberOfLEDs, String channelMapping) throws IllegalArgumentException {
        this(ChipType.valueOf(chipType), clockFrequencyOfICsInHz, frameDurationInMilliseconds, numberOfLEDs, ChannelMapping.valueOf(channelMapping));
    }

    public LEDStripDeviceConfig(ChipType chipType, long clockFrequencyOfICsInHz, int frameDurationInMilliseconds, int numberOfLEDs, ChannelMapping channelMapping) throws IllegalArgumentException {
        if (frameDurationInMilliseconds < 1) {
            throw new IllegalArgumentException();
        }
        if (clockFrequencyOfICsInHz < 10000 || clockFrequencyOfICsInHz > 2000000) {
            throw new IllegalArgumentException();
        }
        if (numberOfLEDs < 1 || numberOfLEDs > 320) {
            throw new IllegalArgumentException();
        }
        this.chipType = chipType;
        this.frameDurationInMilliseconds = frameDurationInMilliseconds;
        this.clockFrequencyOfICsInHz = clockFrequencyOfICsInHz;
        this.numberOfLEDs = numberOfLEDs;
        this.channelMapping = channelMapping;
    }

    public ChipType getChipType() {
        return chipType;
    }

    public int getFrameDurationInMilliseconds() {
        return frameDurationInMilliseconds;
    }

    public long getClockFrequencyOfICsInHz() {
        return clockFrequencyOfICsInHz;
    }

    public int getNumberOfLEDs() {
        return numberOfLEDs;
    }

    public ChannelMapping getChannelMapping() {
        return channelMapping;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.chipType);
        hash = 97 * hash + this.frameDurationInMilliseconds;
        hash = 97 * hash + (int) (this.clockFrequencyOfICsInHz ^ (this.clockFrequencyOfICsInHz >>> 32));
        hash = 97 * hash + this.numberOfLEDs;
        hash = 97 * hash + Objects.hashCode(this.channelMapping);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LEDStripDeviceConfig other = (LEDStripDeviceConfig) obj;
        if (this.frameDurationInMilliseconds != other.frameDurationInMilliseconds) {
            return false;
        }
        if (this.clockFrequencyOfICsInHz != other.clockFrequencyOfICsInHz) {
            return false;
        }
        if (this.numberOfLEDs != other.numberOfLEDs) {
            return false;
        }
        if (this.chipType != other.chipType) {
            return false;
        }
        if (this.channelMapping != other.channelMapping) {
            return false;
        }
        return true;
    }

    /**
     * Returns a new 2-dimensional array of shorts representing all the
     * different channels of the RGB(W)-LEDs of the strip.
     *
     * @return n-channel-array of shorts
     */
    @JsonIgnore
    public short[][] getFreshRGBLEDs() {
        return new short[chipType.getNumberOfChannels()][numberOfLEDs];
    }

}
