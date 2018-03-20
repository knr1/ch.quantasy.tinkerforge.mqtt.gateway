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

import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectorV2ServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.Indicator;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.IndicatorStatus;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectionCycleEndedEvent;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectionDetected;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.SensitivityStatus;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.motionDetectorV2.MotionDetectorV2Device;
import ch.quantasy.tinkerforge.device.motionDetectorV2.MotionDetectorV2DeviceCallback;
import java.net.URI;

/**
 *
 * @author reto
 */
public class MotionDetectorV2Service extends AbstractDeviceService<MotionDetectorV2Device, MotionDetectorV2ServiceContract> implements MotionDetectorV2DeviceCallback {

    public MotionDetectorV2Service(MotionDetectorV2Device device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new MotionDetectorV2ServiceContract(device));
    }

    @Override
    public void detectionCycleEnded() {
        readyToPublishEvent(getContract().EVENT_DETECTION_CYCLE_ENDED, new MotionDetectionCycleEndedEvent());

    }

    @Override
    public void motionDetected() {
        readyToPublishEvent(getContract().EVENT_MOTION_DETECTED, new MotionDetectionDetected());
    }

    @Override
    public void sensitivityChanged(int sensitivity) {
        readyToPublish(getContract().STATUS_SENSITIVITY, new SensitivityStatus(sensitivity));
    }

    @Override
    public void indicatorChanged(Indicator indicator) {
        readyToPublish(getContract().STATUS_INDICATOR, new IndicatorStatus(indicator));
    }
}
