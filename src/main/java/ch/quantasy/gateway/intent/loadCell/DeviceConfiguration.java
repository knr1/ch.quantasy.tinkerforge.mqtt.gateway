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
package ch.quantasy.gateway.intent.loadCell;

import ch.quantasy.gateway.intent.annotations.AValidator;
import ch.quantasy.gateway.intent.annotations.Validator;
import com.tinkerforge.BrickletLoadCell;

/**
 *
 * @author reto
 */
public class DeviceConfiguration extends AValidator{

    public static enum Gain implements Validator{
        gain128X((short) 0), gain64X((short) 1), gain32X((short) 2);
        private short value;

        private Gain(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static Gain getGainFor(short s) throws IllegalArgumentException {
            for (Gain range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }
        @Override
        public boolean isValid() {
            try {
                getGainFor(value);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public static enum Rate implements Validator{
        rate10Hz((short) 0), rate80Hz((short) 1);
        private short value;

        private Rate(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static Rate getRateFor(short s) {
            for (Rate range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            return null;
        }

        @Override
        public boolean isValid() {
            try {
                getRateFor(value);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    private Gain gain;
    private Rate rate;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(String gain, String integrationTime) {
        this(Gain.valueOf(gain), Rate.valueOf(integrationTime));
    }

    public DeviceConfiguration(Gain gain, Rate rate) {
        this.gain = gain;
        this.rate = rate;
    }

    public DeviceConfiguration(short gain, short rate) throws IllegalArgumentException {
        this(Gain.getGainFor(gain), Rate.getRateFor(rate));
    }

    public DeviceConfiguration(BrickletLoadCell.Configuration configuration) {
        this(configuration.gain, configuration.rate);
    }

    public Gain getGain() {
        return gain;
    }

    public Rate getRate() {
        return rate;
    }

}
