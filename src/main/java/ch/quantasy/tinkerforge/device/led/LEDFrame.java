/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
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
package ch.quantasy.tinkerforge.device.led;

/**
 *
 * @author reto
 */
public class LEDFrame {

    private short[] red;
    private short[] green;
    private short[] blue;

    public LEDFrame() {
    }

    public LEDFrame(int amountOfLEDs) {
        red = new short[amountOfLEDs];
        green = new short[amountOfLEDs];
        blue = new short[amountOfLEDs];
    }

    public LEDFrame(LEDFrame frame) {
        this(frame.red, frame.green, frame.blue);
    }

    public LEDFrame(short[] red, short[] green, short[] blue) {
        this.red = red.clone();
        this.green = green.clone();
        this.blue = blue.clone();
    }

    public short[] getBlue() {
        return blue;
    }

    public short[] getGreen() {
        return green;
    }

    public short[] getRed() {
        return red;
    }

    public short[] getColorChannel(int channel) {
        switch (channel) {
            case 0:
                return red;
            case 1:
                return green;
            case 2:
                return blue;
            default:
                return null;
        }
    }

}
