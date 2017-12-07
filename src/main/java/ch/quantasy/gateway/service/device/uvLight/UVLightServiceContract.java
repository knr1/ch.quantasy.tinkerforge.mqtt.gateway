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
package ch.quantasy.gateway.service.device.uvLight;

import ch.quantasy.gateway.message.event.uvLight.UVLightEvent;
import ch.quantasy.gateway.message.intent.uvLight.UVLightIntent;
import ch.quantasy.gateway.message.status.uvLight.DebouncePeriodStatus;
import ch.quantasy.gateway.message.status.uvLight.UvLightCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.uvLight.UvLightCallbackThresholdStatus;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class UVLightServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String UV_LIGHT;
    public final String STATUS_UV_LIGHT;
    public final String STATUS_UV_LIGHT_THRESHOLD;
    public final String STATUS_UV_LIGHT_CALLBACK_PERIOD;
    public final String EVENT_UV_LIGHT;
    public final String EVENT_UV_LIGHT_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public UVLightServiceContract(UVLightDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public UVLightServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.UVLight.toString());
    }

    public UVLightServiceContract(String id, String device) {
        super(id, device, UVLightIntent.class);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        UV_LIGHT = "uvLight";
        STATUS_UV_LIGHT = STATUS + "/" + UV_LIGHT;
        STATUS_UV_LIGHT_THRESHOLD = STATUS_UV_LIGHT + "/" + THRESHOLD;
        STATUS_UV_LIGHT_CALLBACK_PERIOD = STATUS_UV_LIGHT + "/" + CALLBACK_PERIOD;
        EVENT_UV_LIGHT = EVENT + "/" + UV_LIGHT;
        EVENT_UV_LIGHT_REACHED = EVENT_UV_LIGHT + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        addMessageTopic(EVENT_UV_LIGHT, UVLightEvent.class);
        addMessageTopic(EVENT_UV_LIGHT_REACHED, UVLightEvent.class);
        addMessageTopic(STATUS_UV_LIGHT_CALLBACK_PERIOD, UvLightCallbackPeriodStatus.class);
        addMessageTopic(STATUS_UV_LIGHT_THRESHOLD, UvLightCallbackThresholdStatus.class);
        addMessageTopic(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
    }

    
}
