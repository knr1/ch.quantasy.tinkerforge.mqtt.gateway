/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dualButton;

/**
 *
 * @author reto
 */
public enum LEDState {
        AutoToggleOn((short) 0), AutoToggleOff((short) 1), On((short) 2), Off((short) 3);
        private short value;

        private LEDState(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static LEDState getLEDStateFor(short s) {
            for (LEDState range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported " + s);
        }
    }
