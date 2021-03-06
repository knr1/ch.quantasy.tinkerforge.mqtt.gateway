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
package ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RepeatsStatus;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitch.SwitchingEvent;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RemoteSwitchConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RemoteSwitchV2Intent;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.SwitchAEvent;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.SwitchBEvent;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.SwitchCEvent;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.remoteSwitchV2.RemoteSwitchV2Device;
import java.util.Map;

/**
 *
 * @author reto
 */
public class RemoteSwitchV2ServiceContract extends DeviceServiceContract {

    public final String SWITCHING_DONE;
    public final String EVENT_SWITCHING_DONE;

    public final String REPEATS;
    public final String STATUS_REPEATS;
    public final String STATUS_CONFIG;

    public final String SWITCH_SOCKET_A;

    public final String SWITCH_SOCKET_B;

    public final String SWITCH_SOCKET_C;

    public final String DIM_SOCKET_B;

    public final String EVENT_SWITCH_A;
    public final String EVENT_SWITCH_B;
    public final String EVENT_SWITCH_C;

    public RemoteSwitchV2ServiceContract(RemoteSwitchV2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public RemoteSwitchV2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.RemoteSwitchV2.toString());
    }

    public RemoteSwitchV2ServiceContract(String id, String device) {
        super(id, device, RemoteSwitchV2Intent.class);

        SWITCHING_DONE = "switchingDone";
        EVENT_SWITCHING_DONE = EVENT + "/" + SWITCHING_DONE;

        REPEATS = "repeats";
        STATUS_REPEATS = STATUS + "/" + REPEATS;

        SWITCH_SOCKET_A = "switchSocketA";
        SWITCH_SOCKET_B = "switchSocketB";
        SWITCH_SOCKET_C = "switchSocketC";
        DIM_SOCKET_B = "dimSocketB";
        EVENT_SWITCH_A = EVENT + "/" + "switch/A";
        EVENT_SWITCH_B = EVENT + "/" + "switch/B";
        EVENT_SWITCH_C = EVENT + "/" + "switch/C";
        STATUS_CONFIG=STATUS+"/"+"config";

    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_SWITCHING_DONE, SwitchingEvent.class);
        messageTopicMap.put(STATUS_REPEATS, RepeatsStatus.class);
        messageTopicMap.put(EVENT_SWITCH_A, SwitchAEvent.class);
        messageTopicMap.put(EVENT_SWITCH_B, SwitchBEvent.class);
        messageTopicMap.put(EVENT_SWITCH_C, SwitchCEvent.class);
        messageTopicMap.put(STATUS_CONFIG, RemoteSwitchConfigurationStatus.class);
    }

}
