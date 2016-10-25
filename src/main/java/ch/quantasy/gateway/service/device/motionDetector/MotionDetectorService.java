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
package ch.quantasy.gateway.service.device.motionDetector;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDeviceCallback;
import java.net.URI;

/**
 *
 * @author reto
 */
public class MotionDetectorService extends AbstractDeviceService<MotionDetectorDevice, MotionDetectorServiceContract> implements MotionDetectorDeviceCallback {

    public MotionDetectorService(MotionDetectorDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new MotionDetectorServiceContract(device));

        addDescription(getContract().EVENT_DETECTION_CYCLE_ENDED, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().EVENT_MOTION_DETECTED, "[0.." + Long.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {
        //There are no intents that can be handled
    }

    @Override
    public void detectionCycleEnded() {
        addEvent(getContract().EVENT_DETECTION_CYCLE_ENDED, System.currentTimeMillis());

    }

    @Override
    public void motionDetected() {
        addEvent(getContract().EVENT_MOTION_DETECTED, System.currentTimeMillis());
    }
}
