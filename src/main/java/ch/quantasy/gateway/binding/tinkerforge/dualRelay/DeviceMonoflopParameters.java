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
package ch.quantasy.gateway.binding.tinkerforge.dualRelay;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import com.tinkerforge.BrickletDualRelay;

/**
 *
 * @author reto
 */
public class DeviceMonoflopParameters extends AValidator {

    @Choice(values = {"1", "2"})
    public short relay;
    public boolean state;
    @Period
    public long period;

    private DeviceMonoflopParameters() {
    }

    public DeviceMonoflopParameters(short relay, boolean state, long period) throws IllegalArgumentException {
        if (relay != 1 && relay != 2) {
            throw new IllegalArgumentException("Relay should be 1 or 2 but requested: " + relay);
        }
        if (period < 0) {
            throw new IllegalArgumentException("Period must be positive or 0 but requested: " + period);
        }
        this.relay = relay;
        this.state = state;
        this.period = period;
    }

    public DeviceMonoflopParameters(short relay, BrickletDualRelay.Monoflop monoflop) throws IllegalArgumentException {
        this(relay, monoflop.state, monoflop.timeRemaining);
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.relay;
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
        if (this.relay != other.relay) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeviceMonoflopParameters{" + "relay=" + relay + ", state=" + state + ", period=" + period + '}';
    }

}
