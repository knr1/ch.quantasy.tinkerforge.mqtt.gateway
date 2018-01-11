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
package ch.quantasy.gateway.service.tinkerforge.hallEffect;

import ch.quantasy.gateway.message.hallEffect.EdgeCountEvent;
import ch.quantasy.gateway.message.hallEffect.HallEffectIntent;
import ch.quantasy.gateway.message.hallEffect.EdgeCountCallbackPeriodStatus;
import ch.quantasy.gateway.message.hallEffect.EdgeCountConfigurationStatus;
import ch.quantasy.gateway.message.hallEffect.EdgeCountInterruptStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class HallEffectServiceContract extends DeviceServiceContract {

    public final String CALLBACK_PERIOD;
    public final String INTERRUPT;
    public final String RESET;

    public final String EDGE_COUNT;
    public final String STATUS_EDGE_COUNT;
    public final String EVENT_EDGE_COUNT;

    public final String STATUS_EDGE_COUNT_CALLBACK_PERIOD;

    public final String EVENT_EDGE_COUNT_RESET;

    public final String STATUS_EDGE_COUNT_INTERRUPT;

    public final String CONFIGURATION;
    public final String STATUS_CONFIGURATION;

    public HallEffectServiceContract(HallEffectDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public HallEffectServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.HallEffect.toString());
    }

    public HallEffectServiceContract(String id, String device) {
        super(id, device, HallEffectIntent.class);

        EDGE_COUNT = "edgeCount";
        STATUS_EDGE_COUNT = STATUS + "/" + EDGE_COUNT;
        EVENT_EDGE_COUNT = EVENT + "/" + EDGE_COUNT;

        CALLBACK_PERIOD = "callbackPeriod";
        STATUS_EDGE_COUNT_CALLBACK_PERIOD = STATUS_EDGE_COUNT + "/" + CALLBACK_PERIOD;

        INTERRUPT = "interrupt";
        STATUS_EDGE_COUNT_INTERRUPT = STATUS_EDGE_COUNT + "/" + INTERRUPT;

        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;

        RESET = "reset";
        EVENT_EDGE_COUNT_RESET = EVENT_EDGE_COUNT + "/" + RESET;
        addMessageTopic(EVENT_EDGE_COUNT, EdgeCountEvent.class);
        addMessageTopic(EVENT_EDGE_COUNT_RESET, EdgeCountEvent.class);
        addMessageTopic(STATUS_EDGE_COUNT_INTERRUPT, EdgeCountInterruptStatus.class);
        addMessageTopic(STATUS_EDGE_COUNT_CALLBACK_PERIOD, EdgeCountCallbackPeriodStatus.class);
        addMessageTopic(STATUS_CONFIGURATION, EdgeCountConfigurationStatus.class);
    }

   
}
