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
package ch.quantasy.gateway.message.color;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.NonNull;
import ch.quantasy.mqtt.gateway.client.message.Validator;
import com.tinkerforge.BrickletColor;

/**
 *
 * @author reto
 */
public class DeviceConfiguration extends AValidator {

    public static enum Gain implements Validator {
        x1((short) 0), x4((short) 1), x16((short) 2), Hz60((short) 3);
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
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    public static enum IntegrationTime implements Validator {
        ms2_4((short) 0), ms24((short) 1), ms101((short) 2), ms154((short) 3), ms700((short) 4);
        private short value;

        private IntegrationTime(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static IntegrationTime getIntegrationTimeFor(short s) throws IllegalArgumentException {
            for (IntegrationTime range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isValid() {
            try {
                getIntegrationTimeFor(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    @NonNull
    private Gain gain;
    @NonNull
    private IntegrationTime integrationTime;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(String illuminanceRange, String integrationTime) {
        this(Gain.valueOf(illuminanceRange), IntegrationTime.valueOf(integrationTime));
    }

    public DeviceConfiguration(Gain illuminanceRange, IntegrationTime integrationTime) {
        this.gain = illuminanceRange;
        this.integrationTime = integrationTime;
    }

    public DeviceConfiguration(short dataRate, short fullScale) throws IllegalArgumentException {
        this(Gain.getGainFor(dataRate), IntegrationTime.getIntegrationTimeFor(fullScale));
    }

    public DeviceConfiguration(BrickletColor.Config configuration) {
        this(configuration.gain, configuration.integrationTime);
    }

    public Gain getGain() {
        return gain;
    }

    public IntegrationTime getIntegrationTime() {
        return integrationTime;
    }

}
