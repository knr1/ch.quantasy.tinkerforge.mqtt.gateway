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
package ch.quantasy.gateway.service.device.segment4x7;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.segment4x7.Segment4x7Device;
import java.util.Map;

/**
 *
 * @author reto
 */
public class Segment4x7ServiceContract extends DeviceServiceContract {

    public final String COUNTER_STARTED;
    public final String EVENT_COUNTER_STARTED;

    public final String COUNTER_FINISHED;
    public final String EVENT_COUNTER_FINISHED;

    public final String COUNTER;
    public final String INTENT_COUNTER;

    public final String SEGMENTS;
    public final String INTENT_SEGMENTS;
    public final String STATUS_SEGMENTS;

    public Segment4x7ServiceContract(Segment4x7Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public Segment4x7ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.SegmentDisplay4x7.toString());
    }

    public Segment4x7ServiceContract(String id, String device) {
        super(id, device);

        SEGMENTS = "segments";
        INTENT_SEGMENTS = INTENT + "/" + SEGMENTS;
        STATUS_SEGMENTS = STATUS + "/" + SEGMENTS;

        COUNTER = "counter";
        INTENT_COUNTER = INTENT + "/" + COUNTER;

        COUNTER_STARTED = COUNTER + "Started";
        EVENT_COUNTER_STARTED = EVENT + "/" + COUNTER_STARTED;

        COUNTER_FINISHED = COUNTER + "Finished";
        EVENT_COUNTER_FINISHED = EVENT + "/" + COUNTER_FINISHED;

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {

        descriptions.put(INTENT_COUNTER, "from: [-999..9999]\n to: [-999..9999]\n increment: [-999..9999]\n lenght: [0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_SEGMENTS, "bits:[[0..128][0..128][0..128][0..128]]\n brightness: [0..7]\n colon: [true|false]");

        descriptions.put(EVENT_COUNTER_STARTED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: true");
        descriptions.put(EVENT_COUNTER_FINISHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: true");

        descriptions.put(STATUS_SEGMENTS, "bits:[[0..128][0..128][0..128][0..128]]\n brightness: [0..7]\n colon: [true|false]");
    }
}
