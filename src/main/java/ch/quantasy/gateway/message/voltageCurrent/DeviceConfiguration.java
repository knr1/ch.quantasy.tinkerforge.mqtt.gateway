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
package ch.quantasy.gateway.message.voltageCurrent;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.NonNull;
import ch.quantasy.mqtt.gateway.client.message.Validator;
import com.tinkerforge.BrickletVoltageCurrent;

/**
 *
 * @author reto
 */
public class DeviceConfiguration extends AValidator {

    public static enum Averaging implements Validator {
        AVERAGING_1((short) 0), AVERAGING_4((short) 1), AVERAGING_16((short) 2), AVERAGING_64((short) 3), AVERAGING_128((short) 4), AVERAGING_256((short) 5), AVERAGING_512((short) 6), AVERAGING_1024((short) 7),;
        private short value;

        private Averaging(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static Averaging getAveragingFor(short s) throws IllegalArgumentException {
            for (Averaging range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }

        @Override
        public boolean isValid() {
            try {
                getAveragingFor(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }

    }

    public static enum Conversion implements Validator {
        CONVERSION_140us((short) 0), CONVERSION_204us((short) 1), CONVERSION_332us((short) 2), CONVERSION_588us((short) 3), CONVERSION_1100us((short) 4), CONVERSION_2116us((short) 5), CONVERSION_4156us((short) 6), CONVERSION_8244us((short) 7);
        private short value;

        private Conversion(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static Conversion getConversionFor(short s) throws IllegalArgumentException {
            for (Conversion range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }

        @Override
        public boolean isValid() {
            try {
                getConversionFor(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    @NonNull
    private Averaging averaging;
    @NonNull
    private Conversion currentConversionTime;
    @NonNull
    private Conversion voltageConversionTime;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(String averaging, String voltageConversionTime, String currentConversionTime) {
        this(Averaging.valueOf(averaging), Conversion.valueOf(voltageConversionTime), Conversion.valueOf(currentConversionTime));
    }

    public DeviceConfiguration(Averaging gain, Conversion voltage, Conversion current) {
        this.averaging = gain;
        this.voltageConversionTime = voltage;
        this.currentConversionTime = current;
    }

    public DeviceConfiguration(short average, short voltage, short current) throws IllegalArgumentException {
        this(Averaging.getAveragingFor(average), Conversion.getConversionFor(voltage), Conversion.getConversionFor(current));
    }

    public DeviceConfiguration(BrickletVoltageCurrent.Configuration configuration) {
        this(configuration.averaging, configuration.voltageConversionTime, configuration.currentConversionTime);
    }

    public Averaging getAveraging() {
        return averaging;
    }

    public Conversion getCurrentConversionTime() {
        return currentConversionTime;
    }

    public Conversion getVoltageConversionTime() {
        return voltageConversionTime;
    }

}
