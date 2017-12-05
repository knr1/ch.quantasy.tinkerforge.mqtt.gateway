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
package ch.quantasy.gateway.message.intent.thermoCouple;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.NonNull;
import ch.quantasy.mqtt.gateway.client.message.Validator;
import com.tinkerforge.BrickletThermocouple;

/**
 *
 * @author reto
 */
public class DeviceConfiguration extends AValidator {

    public static enum Averaging implements Validator {
        sample_1((short) 1), sample_2((short) 2), sample_4((short) 4), sample_8((short) 8), sample_16((short) 16);
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

    public static enum Type implements Validator {
        B((short) 0), E((short) 1), J((short) 2), K((short) 3), N((short) 4), R((short) 5), S((short) 6), T((short) 7), G8((short) 8), G32((short) 9);
        private short value;

        private Type(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static Type getTypeFor(short s) {
            for (Type range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            return null;
        }

        @Override
        public boolean isValid() {
            try {
                getTypeFor(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    public static enum Filter implements Validator {
        Hz_50((short) 0), Hz_60((short) 1);
        private short value;

        private Filter(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static Filter getFilterFor(short s) {
            for (Filter range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            return null;
        }

        @Override
        public boolean isValid() {
            try {
                getFilterFor(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }
    @NonNull
    private Averaging averaging;
    @NonNull
    private Type type;
    @NonNull
    private Filter filter;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(String averaging, String type, String filter) {
        this(Averaging.valueOf(averaging), Type.valueOf(type), Filter.valueOf(filter));
    }

    public DeviceConfiguration(Averaging averaging, Type type, Filter filter) {
        this.averaging = averaging;
        this.type = type;
        this.filter = filter;
    }

    public DeviceConfiguration(short averaging, short type, short filter) throws IllegalArgumentException {
        this(Averaging.getAveragingFor(averaging), Type.getTypeFor(type), Filter.getFilterFor(filter));
    }

    public DeviceConfiguration(BrickletThermocouple.Configuration configuration) {
        this(configuration.averaging, configuration.thermocoupleType, configuration.filter);
    }

    public Averaging getAveraging() {
        return averaging;
    }

    public Type getType() {
        return type;
    }

    public Filter getFilter() {
        return filter;
    }

}
