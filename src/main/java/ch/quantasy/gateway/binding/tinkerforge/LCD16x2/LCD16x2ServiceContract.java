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
package ch.quantasy.gateway.binding.tinkerforge.LCD16x2;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.ButtonEvent;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.LCD16x2Intent;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.BacklightStatus;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.CustomCharactersStatus;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.ParametersStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.LCD16x2.LCD16x2Device;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import java.util.Map;

/**
 *
 * @author reto
 */
public class LCD16x2ServiceContract extends DeviceServiceContract {

    public final String BACKLIGHT;
    public final String STATUS_BACKLIGHT;
    public final String CLEAR_DISPLAY;
    public final String CONFIG_PARAMETERS;
    public final String STATUS_CONFIG_PARAMETERS;
    public final String STATUS_CUSTOM_CHARACTERS;
    public final String CUSTOM_CHARACTERS;
    public final String WRITE_LINES;
    public final String BUTTON;
    public final String PRESSED;
    public final String RELEASED;
    public final String EVENT_BUTTON_RELEASED;
    public final String EVENT_BUTTON_PRESSED;

    public LCD16x2ServiceContract(LCD16x2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LCD16x2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.LCD16x2.toString());
    }

    public LCD16x2ServiceContract(String id, String device) {
        super(id, device, LCD16x2Intent.class);

        BACKLIGHT = "backlight";
        STATUS_BACKLIGHT = STATUS + "/" + BACKLIGHT;

        CLEAR_DISPLAY = "clearDisplay";

        CONFIG_PARAMETERS = "configParameters";
        STATUS_CONFIG_PARAMETERS = STATUS + "/" + CONFIG_PARAMETERS;

        CUSTOM_CHARACTERS = "customCharacters";
        STATUS_CUSTOM_CHARACTERS = STATUS + "/" + CUSTOM_CHARACTERS;

        WRITE_LINES = "writeLines";

        BUTTON = "button";
        PRESSED = "pressed";
        RELEASED = "released";
        EVENT_BUTTON_RELEASED = EVENT + "/" + BUTTON + "/" + RELEASED;
        EVENT_BUTTON_PRESSED = EVENT + "/" + BUTTON + "/" + PRESSED;

    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_BUTTON_PRESSED, ButtonEvent.class);
        messageTopicMap.put(EVENT_BUTTON_RELEASED, ButtonEvent.class);
        messageTopicMap.put(STATUS_BACKLIGHT, BacklightStatus.class);
        messageTopicMap.put(STATUS_CONFIG_PARAMETERS, ParametersStatus.class);
        messageTopicMap.put(STATUS_CUSTOM_CHARACTERS, CustomCharactersStatus.class);
    }

}
