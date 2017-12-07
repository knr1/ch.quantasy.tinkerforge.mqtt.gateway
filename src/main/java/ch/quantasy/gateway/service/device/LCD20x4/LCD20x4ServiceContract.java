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
package ch.quantasy.gateway.service.device.LCD20x4;

import ch.quantasy.gateway.message.event.LCD20x4.ButtonEvent;
import ch.quantasy.gateway.message.intent.LCD20x4.LCD20x4Intent;
import ch.quantasy.gateway.message.status.LCD20x4.BacklightStatus;
import ch.quantasy.gateway.message.status.LCD20x4.CustomCharactersStatus;
import ch.quantasy.gateway.message.status.LCD20x4.DefaultTextCounterStatus;
import ch.quantasy.gateway.message.status.LCD20x4.DefaultTextsStatus;
import ch.quantasy.gateway.message.status.LCD20x4.ParametersStatus;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.LCD20x4.LCD20x4Device;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import java.util.Map;

/**
 *
 * @author reto
 */
public class LCD20x4ServiceContract extends DeviceServiceContract {

    public final String BACKLIGHT;
    public final String STATUS_BACKLIGHT;
    public final String CLEAR_DISPLAY;
    public final String CONFIG_PARAMETERS;
    public final String STATUS_CONFIG_PARAMETERS;
    public final String STATUS_CUSTOM_CHARACTERS;
    public final String CUSTOM_CHARACTERS;
    public final String DEFAULT_TEXT;
    public final String STATUS_DEFAULT_TEXT_TEXTS;
    public final String COUNTER;
    public final String STATUS_DEFAULT_TEXT_COUNTER;
    public final String WRITE_LINES;
    public final String BUTTON;
    public final String EVENT_BUTTON;
    public final String TEXT;

    public LCD20x4ServiceContract(LCD20x4Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LCD20x4ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.LCD20x4.toString());
    }

    public LCD20x4ServiceContract(String id, String device) {
        super(id, device, LCD20x4Intent.class);

        BACKLIGHT = "backlight";
        STATUS_BACKLIGHT = STATUS + "/" + BACKLIGHT;

        CLEAR_DISPLAY = "clearDisplay";

        CONFIG_PARAMETERS = "configParameters";
        STATUS_CONFIG_PARAMETERS = STATUS + "/" + CONFIG_PARAMETERS;

        CUSTOM_CHARACTERS = "customCharacters";
        STATUS_CUSTOM_CHARACTERS = STATUS + "/" + CUSTOM_CHARACTERS;

        DEFAULT_TEXT = "defaultText";
        TEXT = "texts";
        STATUS_DEFAULT_TEXT_TEXTS = STATUS + "/" + DEFAULT_TEXT + "/" + TEXT;

        COUNTER = "counter";
        STATUS_DEFAULT_TEXT_COUNTER = STATUS + "/" + DEFAULT_TEXT + "/" + COUNTER;

        WRITE_LINES = "writeLines";

        BUTTON = "button";

        EVENT_BUTTON = EVENT + "/" + BUTTON;
        
        addMessageTopic(EVENT_BUTTON, ButtonEvent.class);
        addMessageTopic(STATUS_BACKLIGHT, BacklightStatus.class);
        addMessageTopic(STATUS_CONFIG_PARAMETERS, ParametersStatus.class);
        addMessageTopic(STATUS_CUSTOM_CHARACTERS, CustomCharactersStatus.class);
        addMessageTopic(STATUS_DEFAULT_TEXT_TEXTS, DefaultTextsStatus.class);
        addMessageTopic(STATUS_DEFAULT_TEXT_COUNTER, DefaultTextCounterStatus.class);
    }

   
}
