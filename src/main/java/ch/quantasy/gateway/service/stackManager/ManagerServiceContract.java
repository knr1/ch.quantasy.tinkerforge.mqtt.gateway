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
package ch.quantasy.gateway.service.stackManager;

import ch.quantasy.gateway.service.TinkerForgeServiceContract;
import java.util.Map;

/**
 *
 * @author reto
 */
public class ManagerServiceContract extends TinkerForgeServiceContract {

    public final String STACK;

    public final String STATUS_STACK;
    public final String EVENT_STACK;
    public final String INTENT_STACK;

    public final String ADDRESS;
    public final String STATUS_STACK_ADDRESS;

    public final String INTENT_STACK_ADDRESS;
    public final String ADD;
    public final String INTENT_STACK_ADDRESS_ADD;
    public final String REMOVE;
    public final String INTENT_STACK_ADDRESS_REMOVE;

    public final String EVENT_STACK_ADDRESS;
    public final String ADDED;
    public final String EVENT_STACK_ADDRESS_ADDED;
    public final String REMOVED;
    public final String EVENT_STACK_ADDRESS_REMOVED;

    public final String DEVICE;
    public final String STATUS_DEVICE;
    public final String EVENT_DEVICE;
    public final String CONNECTED;

    public String EVENT_ADDRESS_CONNECTED;
    public String DISCONNECTED;

    public String EVENT_ADDRESS_DISCONNECTED;

    public ManagerServiceContract(String base) {
        super(base, null);

        STACK = "stack";

        STATUS_STACK = STATUS + "/" + STACK;
        EVENT_STACK = EVENT + "/" + STACK;
        INTENT_STACK = INTENT + "/" + STACK;

        ADDRESS = "address";
        STATUS_STACK_ADDRESS = STATUS_STACK + "/" + ADDRESS;
        INTENT_STACK_ADDRESS = INTENT_STACK + "/" + ADDRESS;
        ADD = "add";
        INTENT_STACK_ADDRESS_ADD = INTENT_STACK_ADDRESS + "/" + ADD;
        REMOVE = "remove";
        INTENT_STACK_ADDRESS_REMOVE = INTENT_STACK_ADDRESS + "/" + REMOVE;

        EVENT_STACK_ADDRESS = EVENT_STACK + "/" + ADDRESS;
        ADDED = "added";
        EVENT_STACK_ADDRESS_ADDED = EVENT_STACK_ADDRESS + "/" + ADDED;
        REMOVED = "removed";
        EVENT_STACK_ADDRESS_REMOVED = EVENT_STACK_ADDRESS + "/" + REMOVED;

        DEVICE = "device";
        STATUS_DEVICE = STATUS + "/" + DEVICE;
        EVENT_DEVICE = EVENT + "/" + DEVICE;
        CONNECTED = "connected";

        EVENT_ADDRESS_CONNECTED = EVENT_DEVICE + "/" + CONNECTED;
        DISCONNECTED = "disconnected";

        EVENT_ADDRESS_DISCONNECTED = EVENT_DEVICE + "/" + DISCONNECTED;

    }

    @Override
    protected void describe(Map<String, String> descriptions) {
        descriptions.put(INTENT_STACK_ADDRESS_ADD, "hostName: <String>\n port: [0..4223..65536]");
        descriptions.put(INTENT_STACK_ADDRESS_REMOVE, "hostName: <String>\n port: [0..4223..65536]");
        descriptions.put(EVENT_ADDRESS_CONNECTED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value:\n    hostName: <String>\n    port: [0..4223..65536]");
        descriptions.put(EVENT_ADDRESS_DISCONNECTED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value:\n    hostName: <String>\n    port: [0..4223..65536]");
        descriptions.put(EVENT_STACK_ADDRESS_ADDED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value:\n    hostName: <String>\n    port: [0..4223..65536]");
        descriptions.put(EVENT_STACK_ADDRESS_REMOVED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value:\n    hostName: <String>\n    port: [0..4223..65536]");
        descriptions.put(STATUS_STACK_ADDRESS + "/<address>/connected", "[true|false]");

    }

}
