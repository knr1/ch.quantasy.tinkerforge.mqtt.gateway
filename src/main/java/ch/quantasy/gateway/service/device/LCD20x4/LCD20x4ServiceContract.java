/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service.device.LCD20x4;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.LCD20x4.LCD20x4Device;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;

/**
 *
 * @author reto
 */
public class LCD20x4ServiceContract extends DeviceServiceContract {

    public final String BACKLIGHT;
    public final String INTENT_BACKLIGHT;
    public final String STATUS_BACKLIGHT;
    public final String CLEAR_DISPLAY;
    public final String INTENT_CLEAR_DISPLAY;
    public final String INTENT_CONFIG_PARAMETERS;
    public final String CONFIG_PARAMETERS;
    public final String STATUS_CONFIG_PARAMETERS;
    public final String INTENT_CUSTOM_CHARACTERS;
    public final String STATUS_CUSTOM_CHARACTERS;
    public final String CUSTOM_CHARACTERS;
    public final String DEFAULT_TEXTS;
    public final String INTENT_DEFAULT_TEXTS;
    public final String STATUS_DEFAULT_TEXTS;
    public final String DEFAULT_TEXT_COUNTER;
    public final String INTENT_DEFAULT_TEXT_COUNTER;
    public final String STATUS_DEFAULT_TEXT_COUNTER;
    public final String WRITE_LINES;
    public final String INTENT_WRITE_LINES;
    public final String BUTTON;
    public final String PRESSED;
    public final String RELEASED;
    public final String EVENT_BUTTON_RELEASED;
    public final String EVENT_BUTTON_PRESSED;

    public LCD20x4ServiceContract(LCD20x4Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LCD20x4ServiceContract(String id, String device) {
        super(id, device);

        BACKLIGHT = "backlight";
        INTENT_BACKLIGHT = INTENT + "/" + BACKLIGHT;
        STATUS_BACKLIGHT = STATUS + "/" + BACKLIGHT;

        CLEAR_DISPLAY = "clearDisplay";
        INTENT_CLEAR_DISPLAY = INTENT + "/" + CLEAR_DISPLAY;

        CONFIG_PARAMETERS = "configParameters";
        INTENT_CONFIG_PARAMETERS = INTENT + "/" + CONFIG_PARAMETERS;
        STATUS_CONFIG_PARAMETERS = STATUS + "/" + CONFIG_PARAMETERS;

        CUSTOM_CHARACTERS = "customCharacters";
        INTENT_CUSTOM_CHARACTERS = INTENT + "/" + CUSTOM_CHARACTERS;
        STATUS_CUSTOM_CHARACTERS = STATUS + "/" + CUSTOM_CHARACTERS;

        DEFAULT_TEXTS = "defaultTexts";
        INTENT_DEFAULT_TEXTS = INTENT + "/" + DEFAULT_TEXTS;
        STATUS_DEFAULT_TEXTS = STATUS + "/" + DEFAULT_TEXTS;

        DEFAULT_TEXT_COUNTER = "defaultTextCounter";
        INTENT_DEFAULT_TEXT_COUNTER = INTENT + "/" + DEFAULT_TEXT_COUNTER;
        STATUS_DEFAULT_TEXT_COUNTER = STATUS + "/" + DEFAULT_TEXT_COUNTER;

        WRITE_LINES = "writeLines";
        INTENT_WRITE_LINES = INTENT + "/" + WRITE_LINES;
        
        BUTTON="button";
        PRESSED="pressed";
        RELEASED="released";
        EVENT_BUTTON_RELEASED=EVENT+"/"+BUTTON+"/"+RELEASED;
        EVENT_BUTTON_PRESSED=EVENT+"/"+BUTTON+"/"+PRESSED;
    }
}
