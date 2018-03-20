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

import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RemoteSwitchV2ServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitch.SwitchingEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitch.SocketParameters;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RepeatsStatus;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RemoteSwitchConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RemoteSwitchConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.SwitchAEvent;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.SwitchBEvent;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.SwitchCEvent;
import ch.quantasy.tinkerforge.device.remoteSwitchV2.RemoteSwitchDeviceV2Callback;
import ch.quantasy.tinkerforge.device.remoteSwitchV2.RemoteSwitchV2Device;
import java.net.URI;

/**
 *
 * @author reto
 */
public class RemoteSwitchV2Service extends AbstractDeviceService<RemoteSwitchV2Device, RemoteSwitchV2ServiceContract> implements RemoteSwitchDeviceV2Callback {

    public RemoteSwitchV2Service(RemoteSwitchV2Device device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new RemoteSwitchV2ServiceContract(device));

    }

  
    @Override
    public void switchingDone(SocketParameters socketParameters) {
        readyToPublishEvent(getContract().EVENT_SWITCHING_DONE, new SwitchingEvent(socketParameters));
    }

    @Override
    public void repeatsChanged(int repeats) {
        readyToPublishStatus(getContract().STATUS_REPEATS, new RepeatsStatus(repeats));
    }

    @Override
    public void remoteSwitchConfigChanged(RemoteSwitchConfiguration remoteSwitchConfiguration) {
        readyToPublishStatus(getContract().STATUS_CONFIG, new RemoteSwitchConfigurationStatus(remoteSwitchConfiguration));
    }

    @Override
    public void remoteStatusA(int houseCode, int receiverCode, int switchTo, int repeats) {
        readyToPublishEvent(getContract().EVENT_SWITCH_A, new SwitchAEvent(houseCode, receiverCode, switchTo, repeats));
    }

    @Override
    public void remoteStatusB(long address, int unit, int switchTo, int dimValue, int repeats) {
        readyToPublishEvent(getContract().EVENT_SWITCH_B, new SwitchBEvent(address, unit, switchTo, repeats));
    }

    @Override
    public void remoteStatusC(char systemCode, int deviceCode, int switchTo, int repeats) {
        readyToPublishEvent(getContract().EVENT_SWITCH_C, new SwitchCEvent(systemCode, deviceCode, switchTo, repeats));
    }
}
