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
package ch.quantasy.gateway.binding.tinkerforge.io16;

import com.tinkerforge.BrickletIO16;

/**
 *
 * @author reto
 */
public class DeviceMonoflopParameters implements Comparable<DeviceMonoflopParameters> {

    private char port;
    private short pin;
    private short value;
    private long period;
    private long timeRemaining;

    private DeviceMonoflopParameters() {
    }

    public DeviceMonoflopParameters(char port, short pin, short value, long period) {
        this.port = port;
        this.pin = pin;
        this.value = value;
        this.period = period;
    }

    public DeviceMonoflopParameters(char port, short pin, BrickletIO16.PortMonoflop monoflop) {
        this.port = port;
        this.pin = pin;
        this.value = monoflop.value;
        this.period = monoflop.time;
        this.timeRemaining = monoflop.timeRemaining;
    }

    public String getPortPin() {
        return "" + port + "" + pin;
    }

    public char getPort() {
        return port;
    }

    public long getPeriod() {
        return period;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public short getPin() {
        return pin;
    }

    public short getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.port;
        hash = 89 * hash + this.pin;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeviceMonoflopParameters other = (DeviceMonoflopParameters) obj;
        if (this.port != other.port) {
            return false;
        }
        if (this.pin != other.pin) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(DeviceMonoflopParameters o) {
        return ((port << 4) + pin) - ((o.port << 4) + pin);
    }

}
