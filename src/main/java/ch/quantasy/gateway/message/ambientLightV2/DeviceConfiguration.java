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
package ch.quantasy.gateway.message.ambientLightV2;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.NonNull;
import ch.quantasy.mqtt.gateway.client.message.Validator;
import com.tinkerforge.BrickletAmbientLightV2;

/**
 *
 * @author reto
 */
public class DeviceConfiguration extends AValidator {

    public static enum IlluminanceRange implements Validator {
        lx_unlimitted((short) 6), lx_64000((short) 0), lx_32000((short) 1), lx_16000((short) 2), lx_8000((short) 3), lx_1300((short) 4), lx_600((short) 5);
        private short value;

        private IlluminanceRange(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static IlluminanceRange getIlluminanceRangeFor(short s) throws IllegalArgumentException {
            for (IlluminanceRange range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }

        @Override
        public boolean isValid() {
            try {
                getIlluminanceRangeFor(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    public static enum IntegrationTime implements Validator {
        ms_50((short) 0), ms_100((short) 1), ms_150((short) 2), ms_200((short) 3), ms_250((short) 4), ms_300((short) 5), ms_350((short) 6), ms_400((short) 7);
        private short value;

        private IntegrationTime(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static IntegrationTime getIntegrationTimeFor(short s) {
            for (IntegrationTime range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            return null;
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
    private IlluminanceRange illuminanceRange;
    @NonNull
    private IntegrationTime integrationTime;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(String illuminanceRange, String integrationTime) {
        this(IlluminanceRange.valueOf(illuminanceRange), IntegrationTime.valueOf(integrationTime));
    }

    public DeviceConfiguration(IlluminanceRange illuminanceRange, IntegrationTime integrationTime) {
        this.illuminanceRange = illuminanceRange;
        this.integrationTime = integrationTime;
    }

    public DeviceConfiguration(short illuminanceRange, short integrationTime) throws IllegalArgumentException {
        this(IlluminanceRange.getIlluminanceRangeFor(illuminanceRange), IntegrationTime.getIntegrationTimeFor(integrationTime));
    }

    public DeviceConfiguration(BrickletAmbientLightV2.Configuration configuration) {
        this(configuration.illuminanceRange, configuration.integrationTime);
    }

    public IlluminanceRange getIlluminanceRange() {
        return illuminanceRange;
    }

    public IntegrationTime getIntegrationTime() {
        return integrationTime;
    }

}
