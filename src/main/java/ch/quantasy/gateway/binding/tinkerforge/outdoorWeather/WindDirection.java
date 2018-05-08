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
package ch.quantasy.gateway.binding.tinkerforge.outdoorWeather;

import ch.quantasy.mqtt.gateway.client.message.Validator;
import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.NonNull;
import com.tinkerforge.BrickletOutdoorWeather;
import static com.tinkerforge.BrickletOutdoorWeather.WIND_DIRECTION_N;
import com.tinkerforge.BrickletOutdoorWeatherProvider;
import static java.util.concurrent.TimeUnit.values;

/**
 *
 * @author reto
 */
public class WindDirection extends AValidator {

    public static enum Direction implements Validator {

        E(BrickletOutdoorWeather.WIND_DIRECTION_E), ENE(BrickletOutdoorWeather.WIND_DIRECTION_ENE), ERROR(BrickletOutdoorWeather.WIND_DIRECTION_ERROR), ESE(BrickletOutdoorWeather.WIND_DIRECTION_ESE), N(BrickletOutdoorWeather.WIND_DIRECTION_N),
        NE(BrickletOutdoorWeather.WIND_DIRECTION_NE), NNE(BrickletOutdoorWeather.WIND_DIRECTION_NNE), NNW(BrickletOutdoorWeather.WIND_DIRECTION_NNW), NW(BrickletOutdoorWeather.WIND_DIRECTION_NW),
        S(BrickletOutdoorWeather.WIND_DIRECTION_S), SE(BrickletOutdoorWeather.WIND_DIRECTION_SE), SSE(BrickletOutdoorWeather.WIND_DIRECTION_SSE), SSW(BrickletOutdoorWeather.WIND_DIRECTION_SSW),
        SW(BrickletOutdoorWeather.WIND_DIRECTION_SW), W(BrickletOutdoorWeather.WIND_DIRECTION_W), WNW(BrickletOutdoorWeather.WIND_DIRECTION_WNW), WSW(BrickletOutdoorWeather.WIND_DIRECTION_WSW);
        private int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Direction getDirectionFor(int s) throws IllegalArgumentException {
            for (Direction range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }

        @Override
        public boolean isValid() {
            try {
                getDirectionFor(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    @NonNull
    public Direction direction;

    private WindDirection() {
    }

    public WindDirection(String direction) {
        this(Direction.valueOf(direction));
    }

    public WindDirection(Direction direction) {
        this.direction = direction;
    }

    public WindDirection(int direction) throws IllegalArgumentException {
        this(Direction.getDirectionFor(direction));
    }

}
