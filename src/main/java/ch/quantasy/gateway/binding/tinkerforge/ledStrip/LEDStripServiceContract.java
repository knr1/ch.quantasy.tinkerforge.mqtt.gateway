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
package ch.quantasy.gateway.binding.tinkerforge.ledStrip;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.LagingEvent;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.RenderedEvent;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.LedStripIntent;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.ConfigStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.ledStrip.LEDStripDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class LEDStripServiceContract extends DeviceServiceContract {

    public final String FRAME;
    private final String EVENT_LEDs;
    public final String FRAMES;

    public final String CONFIG;
    public final String STATUS_CONFIG;
    public final String EVENT_CONFIG;
    public final String RENDERED;
    public final String EVENT_LEDs_RENDERED;
    public final String LAGING;
    public final String EVENT_LAGING;

    public LEDStripServiceContract(LEDStripDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LEDStripServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.LEDStrip.toString());
    }

    public LEDStripServiceContract(String id, String device) {
        super(id, device, LedStripIntent.class);
        FRAME = "frame";
        EVENT_LEDs = EVENT + "/" + FRAME;

        FRAMES = "multiFrames";

        CONFIG = "config";
        STATUS_CONFIG = STATUS + "/" + CONFIG;
        EVENT_CONFIG = EVENT + "/" + CONFIG;

        RENDERED = "rendered";
        EVENT_LEDs_RENDERED = EVENT_LEDs + "/" + RENDERED;

        LAGING = "laging";
        EVENT_LAGING = EVENT + "/" + LAGING;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(EVENT_LEDs_RENDERED, RenderedEvent.class);
        messageTopicMap.put(EVENT_LAGING, LagingEvent.class);
        messageTopicMap.put(STATUS_CONFIG, ConfigStatus.class);

    }

   
}
