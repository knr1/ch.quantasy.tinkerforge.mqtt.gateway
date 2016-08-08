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
package ch.quantasy.gateway.service.device.LCD16x2;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.LCD16x2.DeviceConfigParameters;
import ch.quantasy.tinkerforge.device.LCD16x2.DeviceCustomCharacter;
import ch.quantasy.tinkerforge.device.LCD16x2.LCD16x2Device;
import ch.quantasy.tinkerforge.device.LCD16x2.LCD16x2DeviceCallback;
import java.net.URI;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class LCD16x2Service extends AbstractDeviceService<LCD16x2Device, LCD16x2ServiceContract> implements LCD16x2DeviceCallback {

    public LCD16x2Service(LCD16x2Device device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new LCD16x2ServiceContract(device));
        addDescription(getServiceContract().INTENT_BACKLIGHT, "[true|false]");
        addDescription(getServiceContract().STATUS_BACKLIGHT, "[true|false]");
        addDescription(getServiceContract().INTENT_CLEAR_DISPLAY, "[true|false]");
        addDescription(getServiceContract().INTENT_CONFIG_PARAMETERS, "cursor: [true|false]\n blinking: [true|false]");
        addDescription(getServiceContract().STATUS_CONFIG_PARAMETERS, "cursor: [true|false]\n blinking: [true|false]");
        addDescription(getServiceContract().INTENT_CUSTOM_CHARACTERS, "[index: [0..15]\n pixels: [[" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]]_[1..8]]");
        addDescription(getServiceContract().STATUS_CUSTOM_CHARACTERS, "[index: [0..15]\n pixels: [[" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]]_[1..8]]");
        addDescription(getServiceContract().INTENT_WRITE_LINES, "[line: [0..1]\n position: [0..15]\n text: [String]_[1..16]]");
    }

        public void messageArrived(String string, byte[] payload) throws Exception {

            if (string.startsWith(getServiceContract().INTENT_BACKLIGHT)) {
                Boolean backlight = getMapper().readValue(payload, Boolean.class);
                getDevice().setBacklight(backlight);
            }
            if (string.startsWith(getServiceContract().INTENT_CLEAR_DISPLAY)) {
                Boolean clear = getMapper().readValue(payload, Boolean.class);
                getDevice().clearDisplay(clear);
            }
       
    }

    @Override
    public void backlightChanged(Boolean isBacklightEnabled) {
        addStatus(getServiceContract().STATUS_BACKLIGHT, isBacklightEnabled);
    }

    @Override
    public void configurationChanged(DeviceConfigParameters configParameters) {
        addStatus(getServiceContract().STATUS_CONFIG_PARAMETERS, configParameters);
    }

    @Override
    public void customCharactersChanged(DeviceCustomCharacter... customCharacters) {
        Arrays.sort(customCharacters);
        addStatus(getServiceContract().STATUS_CUSTOM_CHARACTERS, customCharacters);
    }

    @Override
    public void buttonPressed(short s) {
        addEvent(getServiceContract().EVENT_BUTTON_PRESSED, new ButtonEvent(s));
    }

    @Override
    public void buttonReleased(short s) {
        addEvent(getServiceContract().EVENT_BUTTON_RELEASED, new ButtonEvent(s));
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
