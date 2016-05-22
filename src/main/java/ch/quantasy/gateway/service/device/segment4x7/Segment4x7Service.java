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
package ch.quantasy.gateway.service.device.segment4x7;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.segment4x7.DeviceCounterParameters;
import ch.quantasy.tinkerforge.device.segment4x7.DeviceSegments;
import ch.quantasy.tinkerforge.device.segment4x7.Segment4x7Device;
import ch.quantasy.tinkerforge.device.segment4x7.Segment4x7DeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 */
public class Segment4x7Service extends AbstractDeviceService<Segment4x7Device, Segment4x7ServiceContract> implements Segment4x7DeviceCallback {

    public Segment4x7Service(Segment4x7Device device, URI mqttURI) throws MqttException {
        super(device, new Segment4x7ServiceContract(device), mqttURI);

        addDescription(getServiceContract().INTENT_COUNTER, "from: [-999..9999]\n to: [-999..9999]\n increment: [-999..9999]\n lenght: [0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_SEGMENTS, "bits:[[0..128][0..128][0..128][0..128]]\n brightness: [0..7]\n colon: [true|false]");

        addDescription(getServiceContract().EVENT_COUNTER_STARTED, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_COUNTER_FINISHED, "[0.." + Long.MAX_VALUE + "]");

        addDescription(getServiceContract().STATUS_SEGMENTS, "bits:[[0..128][0..128][0..128][0..128]]\n brightness: [0..7]\n colon: [true|false]");

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;

        }
        try {
            if (string.startsWith(getServiceContract().INTENT_SEGMENTS)) {
                DeviceSegments segments = getMapper().readValue(payload, DeviceSegments.class);
                getDevice().setSegments(segments);

            }
            if (string.startsWith(getServiceContract().INTENT_COUNTER)) {
                DeviceCounterParameters counter = getMapper().readValue(payload, DeviceCounterParameters.class);
                getDevice().startCounter(counter);

            }
        } catch (Exception ex) {
            Logger.getLogger(Segment4x7Service.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    @Override
    public void counterStarted(DeviceCounterParameters counterParameters) {
        addEvent(getServiceContract().EVENT_COUNTER_STARTED, System.currentTimeMillis());
    }

    @Override
    public void segmentsChanged(DeviceSegments segments) {
        addStatus(getServiceContract().STATUS_SEGMENTS, segments);
    }

    @Override
    public void counterFinished() {
        addEvent(getServiceContract().EVENT_COUNTER_FINISHED, System.currentTimeMillis());
    }

}
