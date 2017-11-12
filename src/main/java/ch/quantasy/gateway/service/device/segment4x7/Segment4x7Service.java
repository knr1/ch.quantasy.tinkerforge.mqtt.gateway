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
package ch.quantasy.gateway.service.device.segment4x7;

import ch.quantasy.gateway.message.event.segment4x7.CounterEvent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.gateway.message.intent.segment4x7.DeviceCounterParameters;
import ch.quantasy.gateway.message.intent.segment4x7.DeviceSegments;
import ch.quantasy.tinkerforge.device.segment4x7.Segment4x7Device;
import ch.quantasy.tinkerforge.device.segment4x7.Segment4x7DeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.net.URI;

/**
 *
 * @author reto
 */
public class Segment4x7Service extends AbstractDeviceService<Segment4x7Device, Segment4x7ServiceContract> implements Segment4x7DeviceCallback {

    public Segment4x7Service(Segment4x7Device device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new Segment4x7ServiceContract(device));

    }

    @Override
    public void counterStarted(DeviceCounterParameters counterParameters) {
        readyToPublishEvent(getContract().EVENT_COUNTER_STARTED, new CounterEvent(counterParameters,false) );
    }

    @Override
    public void segmentsChanged(DeviceSegments segments) {
        publishStatus(getContract().STATUS_SEGMENTS, segments);
    }

    @Override
    public void counterFinished() {
        readyToPublishEvent(getContract().EVENT_COUNTER_FINISHED, new CounterEvent(true));
    }

}
