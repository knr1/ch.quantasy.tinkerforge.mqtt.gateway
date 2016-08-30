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
package ch.quantasy.tinkerforge.device.hallEffect;

import com.tinkerforge.BrickletHallEffect;


/**
 *
 * @author reto
 */
public class DeviceConfiguration {

    public static enum EdgeType {
        RISING((short) 0), FALLING((short) 1), BOTH((short) 2);
        private short value;

        private EdgeType(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static EdgeType getEdgeTypeFor(short s) throws IllegalArgumentException{
            for (EdgeType range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: "+s);
        }
    }

    
    private EdgeType edgeType;
    private short debounce;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(EdgeType edgeType, Short debounce) {
        this.edgeType = edgeType;
        this.debounce = debounce;
    }

    public DeviceConfiguration(short edgeTypeValue, short debounce) throws IllegalArgumentException {
        this(EdgeType.getEdgeTypeFor(edgeTypeValue), debounce);
    }

    public DeviceConfiguration(BrickletHallEffect.EdgeCountConfig configuration) {
        this(configuration.edgeType, configuration.debounce);
    }

    public short getDebounce() {
        return debounce;
    }

    public EdgeType getEdgeType() {
        return edgeType;
    }
    

}
