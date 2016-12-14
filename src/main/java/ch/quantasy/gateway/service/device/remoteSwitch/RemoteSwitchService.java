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
package ch.quantasy.gateway.service.device.remoteSwitch;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.remoteSwitch.DimSocketBParameters;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDevice;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDeviceCallback;
import ch.quantasy.tinkerforge.device.remoteSwitch.SocketParameters;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketAParameters;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketBParameters;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketCParameters;
import java.net.URI;

/**
 *
 * @author reto
 */
public class RemoteSwitchService extends AbstractDeviceService<RemoteSwitchDevice, RemoteSwitchServiceContract> implements RemoteSwitchDeviceCallback {

    public RemoteSwitchService(RemoteSwitchDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new RemoteSwitchServiceContract(device));

        publishDescription(getContract().INTENT_REPEATS, "[0.." + Short.MAX_VALUE + "]");

        publishDescription(getContract().INTENT_SWITCH_SOCKET_A, "houseCode: [0..31]\n receiverCode: [0..31]\n switchingValue: [switchOn|switchOff]");
        publishDescription(getContract().INTENT_SWITCH_SOCKET_B, "address: [0..67108863]\n unit: [0..15]\n switchingValue: [switchOn|switchOff]");
        publishDescription(getContract().INTENT_SWITCH_SOCKET_C, "systemCode: ['A'..'P']\n deviceCode: [1..16]\n switchingValue: [switchOn|switchOff]");
        publishDescription(getContract().INTENT_DIM_SOCKET_B, "address: [0..67108863]\n unit: [0..15]\n dimValue: [0..15]");

        publishDescription(getContract().EVENT_SWITCHING_DONE, "[0.." + Long.MAX_VALUE + "]\n value: [houseCode: [0..31]\n receiverCode: [0..31]\n switchingValue: [ON|OFF] | address: [0..67108863]\n unit: [0..15]\n switchingValue: [ON|OFF] | systemCode: ['A'..'P']\n deviceCode: [1..16]\n switchingValue: [ON|OFF] | address: [0..67108863]\n unit: [0..15]\n dimValue: [0..15]]");
        publishDescription(getContract().STATUS_REPEATS, "[0.." + Short.MAX_VALUE + "]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DIM_SOCKET_B)) {
            DimSocketBParameters parameters = getMapper().readValue(payload, DimSocketBParameters.class);
            getDevice().dimSocketB(parameters);
        }
        if (string.startsWith(getContract().INTENT_SWITCH_SOCKET_A)) {
            SwitchSocketAParameters parameters = getMapper().readValue(payload, SwitchSocketAParameters.class);
            getDevice().switchSocketA(parameters);
        }
        if (string.startsWith(getContract().INTENT_SWITCH_SOCKET_B)) {
            SwitchSocketBParameters parameters = getMapper().readValue(payload, SwitchSocketBParameters.class);
            getDevice().switchSocketB(parameters);
        }
        if (string.startsWith(getContract().INTENT_SWITCH_SOCKET_C)) {

            SwitchSocketCParameters parameters = getMapper().readValue(payload, SwitchSocketCParameters.class);
            getDevice().switchSocketC(parameters);
        }
        if (string.startsWith(getContract().INTENT_REPEATS)) {
            short value = getMapper().readValue(payload, short.class);
            getDevice().setRepeats(value);
        }
    }

    @Override
    public void repeatsChanged(short period) {
        publishStatus(getContract().STATUS_REPEATS, period);
    }

    @Override
    public void switchingDone(SocketParameters socketParameters) {
        publishEvent(getContract().EVENT_SWITCHING_DONE, socketParameters);
    }

}
