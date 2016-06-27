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
package ch.quantasy.tinkerforge.device.ptc;

import com.tinkerforge.BrickletPTC;


/**
 *
 * @author reto
 */
public class DeviceNoiseReductionFilter {
    public static enum Filter {
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
    }
    private Filter filter;

    public DeviceNoiseReductionFilter() {
    }

    public DeviceNoiseReductionFilter(String filter) {
        this(Filter.valueOf(filter));
    }

    public DeviceNoiseReductionFilter(Filter filter) {
        this.filter=filter;
    }

    public DeviceNoiseReductionFilter(short filter) throws IllegalArgumentException {
        this(Filter.getFilterFor(filter));
    }

    public Filter getFilter() {
        return filter;
    }
    
    

}
