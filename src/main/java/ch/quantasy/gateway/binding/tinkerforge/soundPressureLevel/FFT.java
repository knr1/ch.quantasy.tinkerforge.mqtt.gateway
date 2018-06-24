/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
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
package ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel;

import ch.quantasy.mqtt.gateway.client.message.Validator;
import com.tinkerforge.BrickletSoundPressureLevel;

/**
 *
 * @author reto
 */
public enum FFT implements Validator {
    Size128(BrickletSoundPressureLevel.FFT_SIZE_128),
    Size256(BrickletSoundPressureLevel.FFT_SIZE_256),
    Size512(BrickletSoundPressureLevel.FFT_SIZE_512),
    Size1024(BrickletSoundPressureLevel.FFT_SIZE_1024);

    private int value;

    private FFT(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FFT getValueFor(int s) throws IllegalArgumentException {
        for (FFT range : values()) {
            if (range.value == s) {
                return range;
            }
        }
        throw new IllegalArgumentException("Not supported: " + s);
    }

    @Override
    public boolean isValid() {
        try {
            getValueFor(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}

