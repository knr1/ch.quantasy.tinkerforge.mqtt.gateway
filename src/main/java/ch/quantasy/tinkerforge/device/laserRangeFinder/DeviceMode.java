/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.laserRangeFinder;

import com.tinkerforge.BrickletLaserRangeFinder;

/**
 *
 * @author reto
 */
public class DeviceMode {
    public static enum Mode {
        distance((short) 0), velocity_12_7((short) 1), velocity_31_75((short) 2), velocity_63_5((short) 3), velocity_127((short) 4);
        private short value;

        private Mode(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static Mode getModeFor(short s) throws IllegalArgumentException{
            for (Mode range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: "+s);
        }
    }
    
    private Mode mode;
    
    public DeviceMode() {
    }

    public DeviceMode(String mode){
        this(Mode.valueOf(mode));
    }
    public DeviceMode(Mode mode) {
        this.mode = mode;
    }

    public DeviceMode(short value) throws IllegalArgumentException {
        this(Mode.getModeFor(value));
    }

    public Mode getMode() {
        return mode;
    }

}
