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
package ch.quantasy.gateway.service.tinkerforge.multiTouch;

import ch.quantasy.gateway.message.multiTouch.RecalibratedEvent;
import ch.quantasy.gateway.message.multiTouch.TouchStateEvent;
import ch.quantasy.gateway.message.multiTouch.MultiTouchIntent;
import ch.quantasy.gateway.message.multiTouch.ElectrodeConfigStatus;
import ch.quantasy.gateway.message.multiTouch.SensitivityStatus;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.multiTouch.MultiTouchDevice;
import ch.quantasy.tinkerforge.device.multiTouch.MultiTouchDeviceCallback;

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class MultiTouchService extends AbstractDeviceService<MultiTouchDevice, MultiTouchServiceContract> implements MultiTouchDeviceCallback {

    public MultiTouchService(MultiTouchDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new MultiTouchServiceContract(device));
    }

    @Override
    public void electrodeConfigChanged(Integer electrodeConfig) {
        readyToPublishStatus(getContract().STATUS_ELECTRODE_CONFIG, new ElectrodeConfigStatus(electrodeConfig));
    }

    @Override
    public void electrodeSensitivityChanged(Short electrodeSensitivity) {
        readyToPublishStatus(getContract().STATUS_ELECTRODE_SENSITIVITY, new SensitivityStatus(electrodeSensitivity));
    }

    @Override
    public void recalibrated() {
        readyToPublishEvent(getContract().EVENT_RECALIBRATED, new RecalibratedEvent());
    }

    @Override
    public void touchState(int i) {
        readyToPublishEvent(getContract().EVENT_TOUCH_STATE, new TouchStateEvent(i));
    }

}
