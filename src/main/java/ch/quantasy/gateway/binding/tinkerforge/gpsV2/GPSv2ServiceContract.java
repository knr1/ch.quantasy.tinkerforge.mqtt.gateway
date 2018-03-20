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
package ch.quantasy.gateway.binding.tinkerforge.gpsV2;

import ch.quantasy.gateway.binding.tinkerforge.gps.GPSServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.gps.GPSServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.gps.AltitudeEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.DateTimeEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.MotionEvent;
import ch.quantasy.gateway.binding.tinkerforge.gpsV2.CoordinatesEvent;
import ch.quantasy.gateway.binding.tinkerforge.gpsV2.StatusEvent;
import ch.quantasy.gateway.binding.tinkerforge.gpsV2.GPSv2Intent;
import ch.quantasy.gateway.binding.tinkerforge.gps.AltitudeCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.CoordinatesCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.DateTimeCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.MotionCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gpsV2.FixLEDConfigStatus;
import ch.quantasy.gateway.binding.tinkerforge.gpsV2.StatusLEDConfigStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.gpsV2.GPSv2Device;
import java.util.Map;

/**
 *
 * @author reto
 */
public class GPSv2ServiceContract extends GPSServiceContract {

    private final String LED_CONFIG;
    public final String STATUS_FIX_LED_CONFIG;
    public final String STATUS_STATE_LED_CONFIG;

    public GPSv2ServiceContract(GPSv2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public GPSv2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.GPSv2.toString());
    }

    public GPSv2ServiceContract(String id, String device) {
        super(id, device, GPSv2Intent.class);

        LED_CONFIG = "led";
        STATUS_STATE_LED_CONFIG = STATUS + "/" + STATE + "/" + LED_CONFIG;
        STATUS_FIX_LED_CONFIG = STATUS + "/" + "fix" + "/" + LED_CONFIG;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_ALTITUDE, AltitudeEvent.class);
        messageTopicMap.put(EVENT_DATE_TIME, DateTimeEvent.class);
        messageTopicMap.put(EVENT_MOTION, MotionEvent.class);
        messageTopicMap.put(EVENT_STATE, StatusEvent.class);
        messageTopicMap.put(EVENT_COORDINATES, CoordinatesEvent.class);
        messageTopicMap.put(STATUS_DATE_TIME_CALLBACK_PERIOD, DateTimeCallbackPeriodStatus.class);

        messageTopicMap.put(STATUS_MOTION_CALLBACK_PERIOD, MotionCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ALTITUDE_CALLBACK_PERIOD, AltitudeCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_COORDINATES_CALLBACK_PERIOD, CoordinatesCallbackPeriodStatus.class);

        messageTopicMap.put(STATUS_STATE_LED_CONFIG, StatusLEDConfigStatus.class);

        messageTopicMap.put(STATUS_FIX_LED_CONFIG, FixLEDConfigStatus.class);
    }

}
