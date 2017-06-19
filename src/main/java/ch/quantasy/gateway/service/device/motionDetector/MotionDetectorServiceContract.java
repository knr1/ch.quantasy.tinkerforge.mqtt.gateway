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

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class MotionDetectorServiceContract extends DeviceServiceContract {

    public final String DETECTION_CYCLE_ENDED;
    public final String EVENT_DETECTION_CYCLE_ENDED;

    public final String MOTION_DETECTED;
    public final String EVENT_MOTION_DETECTED;

    public MotionDetectorServiceContract(MotionDetectorDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public MotionDetectorServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.MotionDetector.toString());
    }

    public MotionDetectorServiceContract(String id, String device) {
        super(id, device);

        DETECTION_CYCLE_ENDED = "eventDetectionCycleEnded";
        EVENT_DETECTION_CYCLE_ENDED = EVENT + "/" + DETECTION_CYCLE_ENDED;

        MOTION_DETECTED = "motionDetected";
        EVENT_MOTION_DETECTED = EVENT + "/" + MOTION_DETECTED;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {

        descriptions.put(EVENT_DETECTION_CYCLE_ENDED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: true");
        descriptions.put(EVENT_MOTION_DETECTED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: true");
    }
}
