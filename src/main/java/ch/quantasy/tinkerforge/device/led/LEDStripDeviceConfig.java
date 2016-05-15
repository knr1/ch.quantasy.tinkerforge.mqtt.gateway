/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.led;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinkerforge.BrickletLEDStrip;
import java.util.Objects;

/**
 *
 * @author reto
 */
public class LEDStripDeviceConfig {

    public static enum ChipType {
        WS2801(BrickletLEDStrip.CHIP_TYPE_WS2801), WS2811(BrickletLEDStrip.CHIP_TYPE_WS2811), WS2812(BrickletLEDStrip.CHIP_TYPE_WS2812);
        public final int type;

        private ChipType(int value) {
            this.type = value;
        }

        public int getType() {
            return type;
        }
    }

    public static enum ChannelMapping {
        RGB(0, 1, 2), RBG(0, 2, 1), GBR(1, 2, 0), GRB(1, 0, 2), BGR(2, 1, 0), BRG(2, 0, 1);
        public final int[] mapping;

        private ChannelMapping(int... mapping) {
            this.mapping = mapping;
        }

        public int[] getMapping() {
            return mapping;
        }
        public int mapTo(int rgbChannel){
            return mapping[rgbChannel];
        }
    }
    private ChipType chipType;
    private int frameDurationInMilliseconds;
    private long clockFrequencyOfICsInHz;
    private int numberOfLEDs;
    private ChannelMapping channelMapping;

    private LEDStripDeviceConfig()  {
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
        this.channelMapping=channelMapping;
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
     * Returns a new 2-dimensional array of shorts representing all the RGB-LEDs
     * of the strip.
     *
     * @return 3-channel-array of shorts
     */
    @JsonIgnore
    public short[][] getFreshRGBLEDs() {
        return new short[LEDStripDevice.NUMBER_OF_COLOR_CHANNELS][numberOfLEDs];
    }

}
