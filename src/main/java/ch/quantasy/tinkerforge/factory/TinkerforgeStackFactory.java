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
package ch.quantasy.tinkerforge.factory;

import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TinkerforgeStackFactory {

    private static TinkerforgeStackFactory instance;
    private final HashMap<TinkerforgeStackAddress, TinkerforgeStack> tinkerforgeStacks;
    
    static {
        instance = new TinkerforgeStackFactory();
    }

    private TinkerforgeStackFactory() {
        this.tinkerforgeStacks = new HashMap<>();
       
    }

    public static TinkerforgeStackFactory getInstance() {
        return TinkerforgeStackFactory.instance;
    }

    

    public boolean addStack(final TinkerforgeStackAddress address) {
        if (this.tinkerforgeStacks.containsKey(this)) {
            return false;
        }
        this.tinkerforgeStacks.put(address, new TinkerforgeStack(address));
        return true;
    }

    public TinkerforgeStack getStack(final TinkerforgeStackAddress address) {
        return this.tinkerforgeStacks.get(address);
    }

    public TinkerforgeStack removeStack(final TinkerforgeStackAddress address) {
        if (!this.tinkerforgeStacks.containsKey(address)) {
            return null;
        }
        TinkerforgeStack stack = this.tinkerforgeStacks.remove(address);
        return stack;
    }

    public boolean containsStack(final TinkerforgeStackAddress address) {
        return (this.tinkerforgeStacks.containsKey(address));
    }

    public Set<TinkerforgeStackAddress> getTinkerforgeStackAddress() {
        return new HashSet<>(this.tinkerforgeStacks.keySet());
    }

    public Set<TinkerforgeStack> getTinkerforgeStacks() {
        return new HashSet<>(this.tinkerforgeStacks.values());
    }

}
