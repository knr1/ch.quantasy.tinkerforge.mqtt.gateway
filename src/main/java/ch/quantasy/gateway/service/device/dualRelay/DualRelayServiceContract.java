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
package ch.quantasy.gateway.service.device.dualRelay;

import ch.quantasy.gateway.intent.dualRelay.DualRelayIntent;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class DualRelayServiceContract extends DeviceServiceContract {

    public final String STATE;
    public final String STATUS_STATE;

    public final String SELECTED_STATE;

    public final String MONOFLOP_DONE;
    public final String EVENT_MONOFLOP_DONE;

    public final String MONOFLOP;

    public DualRelayServiceContract(DualRelayDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public DualRelayServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.DualRelay.toString());
    }

    public DualRelayServiceContract(String id, String device) {
        super(id, device,DualRelayIntent.class);
        MONOFLOP_DONE = "monoflopDone";
        EVENT_MONOFLOP_DONE = EVENT + "/" + MONOFLOP_DONE;

        MONOFLOP = "monoflop";

        STATE = "state";
        STATUS_STATE = STATUS + "/" + STATE;

        SELECTED_STATE = "selectedState";

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(EVENT_MONOFLOP_DONE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value:\n    relay: [1|2]\n    state: [true|false]");
        descriptions.put(STATUS_STATE, "relay1: [true|false]\n relay2: [true|false]\n");
    }
}
