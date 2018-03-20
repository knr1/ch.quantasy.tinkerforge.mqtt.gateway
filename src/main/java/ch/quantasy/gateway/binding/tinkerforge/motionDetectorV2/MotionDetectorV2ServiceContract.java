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
package ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.IndicatorStatus;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectionCycleEndedEvent;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectionDetected;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectorV2Intent;
import ch.quantasy.gateway.binding.tinkerforge.multiTouch.SensitivityStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.motionDetectorV2.MotionDetectorV2Device;
import java.util.Map;

/**
 *
 * @author reto
 */
public class MotionDetectorV2ServiceContract extends DeviceServiceContract {

    public final String DETECTION_CYCLE_ENDED;
    public final String EVENT_DETECTION_CYCLE_ENDED;

    public final String MOTION_DETECTED;
    public final String EVENT_MOTION_DETECTED;
    public final String STATUS_INDICATOR;
    public final String STATUS_SENSITIVITY;

    public MotionDetectorV2ServiceContract(MotionDetectorV2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public MotionDetectorV2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.MotionDetectorV2.toString());
    }

    public MotionDetectorV2ServiceContract(String id, String device) {
        super(id, device, MotionDetectorV2Intent.class);

        DETECTION_CYCLE_ENDED = "eventDetectionCycleEnded";
        EVENT_DETECTION_CYCLE_ENDED = EVENT + "/" + DETECTION_CYCLE_ENDED;

        MOTION_DETECTED = "motionDetected";
        EVENT_MOTION_DETECTED = EVENT + "/" + MOTION_DETECTED;
        STATUS_INDICATOR=STATUS+"/"+"indicator";
        STATUS_SENSITIVITY=STATUS+"/"+"sensitivity";
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(EVENT_DETECTION_CYCLE_ENDED, MotionDetectionCycleEndedEvent.class);
        messageTopicMap.put(EVENT_MOTION_DETECTED, MotionDetectionDetected.class);
        messageTopicMap.put(STATUS_SENSITIVITY, SensitivityStatus.class);
        messageTopicMap.put(STATUS_INDICATOR, IndicatorStatus.class);
 
    }

    
}
