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

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.LCD20x4.DeviceConfigParameters;
import ch.quantasy.tinkerforge.device.LCD20x4.DeviceCustomCharacter;
import ch.quantasy.tinkerforge.device.LCD20x4.DeviceDefaultText;
import ch.quantasy.tinkerforge.device.LCD20x4.DeviceWriteLine;
import ch.quantasy.tinkerforge.device.LCD20x4.LCD20x4Device;
import ch.quantasy.tinkerforge.device.LCD20x4.LCD20x4DeviceCallback;
import java.net.URI;
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class LCD20x4Service extends AbstractDeviceService<LCD20x4Device, LCD20x4ServiceContract> implements LCD20x4DeviceCallback {

    public LCD20x4Service(LCD20x4Device device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new LCD20x4ServiceContract(device));
        addDescription(getContract().INTENT_BACKLIGHT, "[true|false]");
        addDescription(getContract().STATUS_BACKLIGHT, "[true|false]");
        addDescription(getContract().INTENT_CLEAR_DISPLAY, "[true|false]");
        addDescription(getContract().INTENT_CONFIG_PARAMETERS, "cursor: [true|false]\n blinking: [true|false]");
        addDescription(getContract().STATUS_CONFIG_PARAMETERS, "cursor: [true|false]\n blinking: [true|false]");
        addDescription(getContract().INTENT_CUSTOM_CHARACTERS, "[index: [0..15]\n pixels: [[" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]]_[1..8]]");
        addDescription(getContract().STATUS_CUSTOM_CHARACTERS, "[index: [0..15]\n pixels: [[" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]]_[1..8]]");
        addDescription(getContract().INTENT_DEFAULT_TEXT_TEXTS, "[line: [0..3]\n text: [String]_[1..20]]");
        addDescription(getContract().STATUS_DEFAULT_TEXT_TEXTS, "[line: [0..3]\n text: [String]_[1..20]]");
        addDescription(getContract().INTENT_DEFAULT_TEXT_COUNTER, "[-1.." + Integer.MAX_VALUE + "]");
        addDescription(getContract().STATUS_DEFAULT_TEXT_COUNTER, "[-1.." + Integer.MAX_VALUE + "]");
        addDescription(getContract().INTENT_WRITE_LINES, "[line: [0..3]\n position: [0..18]\n text: [String]_[1..20]]");
    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_BACKLIGHT)) {
            Boolean backlight = getMapper().readValue(payload, Boolean.class);
            getDevice().setBacklight(backlight);
        }
        if (string.startsWith(getContract().INTENT_CLEAR_DISPLAY)) {
            Boolean clear = getMapper().readValue(payload, Boolean.class);
            getDevice().clearDisplay(clear);
        }
        if (string.startsWith(getContract().INTENT_CONFIG_PARAMETERS)) {
            DeviceConfigParameters parameters = getMapper().readValue(payload, DeviceConfigParameters.class);
            getDevice().setConfigParameters(parameters);
        }
        if (string.startsWith(getContract().INTENT_CUSTOM_CHARACTERS)) {
            DeviceCustomCharacter[] characters = getMapper().readValue(payload, DeviceCustomCharacter[].class);
            getDevice().setCustomCharacters(characters);
        }
        if (string.startsWith(getContract().INTENT_DEFAULT_TEXT_TEXTS)) {
            DeviceDefaultText[] texts = getMapper().readValue(payload, DeviceDefaultText[].class);
            getDevice().setDefaultText(texts);
        }
        if (string.startsWith(getContract().INTENT_DEFAULT_TEXT_COUNTER)) {
            Integer counter = getMapper().readValue(payload, Integer.class);
            getDevice().setDefaultTextCounter(counter);
        }
        if (string.startsWith(getContract().INTENT_WRITE_LINES)) {
            DeviceWriteLine[] lines = getMapper().readValue(payload, DeviceWriteLine[].class);
            getDevice().write(lines);
        }
    }

    @Override
    public void backlightChanged(Boolean isBacklightEnabled) {
        addStatus(getContract().STATUS_BACKLIGHT, isBacklightEnabled);
    }

    @Override
    public void configurationChanged(DeviceConfigParameters configParameters) {
        addStatus(getContract().STATUS_CONFIG_PARAMETERS, configParameters);
    }

    @Override
    public void customCharactersChanged(DeviceCustomCharacter... customCharacters) {
        Arrays.sort(customCharacters);
        addStatus(getContract().STATUS_CUSTOM_CHARACTERS, customCharacters);
    }

    @Override
    public void defaultTextsChanged(DeviceDefaultText... defaultTexts) {
        Arrays.sort(defaultTexts);
        addStatus(getContract().STATUS_DEFAULT_TEXT_TEXTS, defaultTexts);
    }

    @Override
    public void defaultTextCounterChanged(Integer defaultTextCounter) {
        addStatus(getContract().STATUS_DEFAULT_TEXT_COUNTER, defaultTextCounter);
    }

    @Override
    public void buttonPressed(short s) {
        addEvent(getContract().EVENT_BUTTON_PRESSED, new ButtonEvent(s));
    }

    @Override
    public void buttonReleased(short s) {
        addEvent(getContract().EVENT_BUTTON_RELEASED, new ButtonEvent(s));
    }

    public static class ButtonEvent {

        private long timestamp;
        private short value;

        public ButtonEvent(short value) {
            this(value, System.currentTimeMillis());
        }

        public ButtonEvent(short value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getValue() {
            return value;
        }

    }
}
