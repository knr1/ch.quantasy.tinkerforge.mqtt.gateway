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
package ch.quantasy.gateway.service.tinkerforge;

import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.LCD16x2ServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.ButtonEvent;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.DeviceConfigParameters;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.DeviceCustomCharacter;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.BacklightStatus;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.CustomCharactersStatus;
import ch.quantasy.gateway.binding.tinkerforge.LCD16x2.ParametersStatus;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.LCD16x2.LCD16x2Device;
import ch.quantasy.tinkerforge.device.LCD16x2.LCD16x2DeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.util.Set;

/**
 *
 * @author reto
 */
public class LCD16x2Service extends AbstractDeviceService<LCD16x2Device, LCD16x2ServiceContract> implements LCD16x2DeviceCallback {

    public LCD16x2Service(LCD16x2Device device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new LCD16x2ServiceContract(device));
    }

    @Override
    public void backlightChanged(Boolean isBacklightEnabled) {
        readyToPublishStatus(getContract().STATUS_BACKLIGHT, new BacklightStatus(isBacklightEnabled));
    }

    @Override
    public void configurationChanged(DeviceConfigParameters configParameters) {
        readyToPublishStatus(getContract().STATUS_CONFIG_PARAMETERS, new ParametersStatus(configParameters));
    }

    @Override
    public void customCharactersChanged(Set<DeviceCustomCharacter> customCharacters) {
        readyToPublishStatus(getContract().STATUS_CUSTOM_CHARACTERS, new CustomCharactersStatus(customCharacters));
    }

    @Override
    public void buttonPressed(short s) {
        readyToPublishEvent(getContract().EVENT_BUTTON_PRESSED, new ButtonEvent(s,true));
    }

    @Override
    public void buttonReleased(short s) {
        readyToPublishEvent(getContract().EVENT_BUTTON_RELEASED, new ButtonEvent(s,false));
    }

}
