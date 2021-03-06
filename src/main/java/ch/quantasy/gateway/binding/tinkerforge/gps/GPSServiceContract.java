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
package ch.quantasy.gateway.binding.tinkerforge.gps;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.gps.AltitudeEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.CoordinatesEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.DateTimeEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.MotionEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.StatusEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.GPSIntent;
import ch.quantasy.gateway.binding.tinkerforge.gps.AltitudeCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.CoordinatesCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.DateTimeCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.MotionCallbackPeriodStatus;
import ch.quantasy.mqtt.gateway.client.message.Intent;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.gps.GPSDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class GPSServiceContract extends DeviceServiceContract {

    public final String CALLBACK_PERIOD;
    public final String ALTITUDE;
    public final String RESTART;

    public final String STATUS_COORDINATES_CALLBACK_PERIOD;

    public final String EVENT_ALTITUDE;
    public final String STATUS_ALTITUDE_CALLBACK_PERIOD;

    public final String COORDINATES;
    public final String DATE_TIME;
    public final String STATUS_DATE_TIME_CALLBACK_PERIOD;
    public final String EVENT_DATE_TIME;
    public final String EVENT_RESTART;
    public final String MOTION;
    public final String STATUS_MOTION_CALLBACK_PERIOD;
    public final String EVENT_MOTION;
    public final String STATE;
    public final String STATUS_STATE_CALLBACK_PERIOD;
    public final String EVENT_COORDINATES;
    public final String EVENT_STATE;

    public GPSServiceContract(GPSDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public GPSServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.GPS.toString());
    }

    public GPSServiceContract(String id, String device) {
        this(id, device, GPSIntent.class);
    }

    public GPSServiceContract(String instance, String baseClass, Class<? extends Intent> intentClass) {
        super(instance, baseClass, intentClass);

        CALLBACK_PERIOD = "callbackPeriod";

        COORDINATES = "coordinates";
        STATUS_COORDINATES_CALLBACK_PERIOD = STATUS + "/" + COORDINATES + "/" + CALLBACK_PERIOD;
        EVENT_COORDINATES = EVENT + "/" + COORDINATES;

        ALTITUDE = "altitude";
        STATUS_ALTITUDE_CALLBACK_PERIOD = STATUS + "/" + ALTITUDE + "/" + CALLBACK_PERIOD;
        EVENT_ALTITUDE = EVENT + "/" + ALTITUDE;

        RESTART = "restart";
        EVENT_RESTART = EVENT + "/" + RESTART;

        DATE_TIME = "dateTime";
        STATUS_DATE_TIME_CALLBACK_PERIOD = STATUS + "/" + DATE_TIME + "/" + CALLBACK_PERIOD;
        EVENT_DATE_TIME = EVENT + "/" + DATE_TIME;

        MOTION = "motion";
        STATUS_MOTION_CALLBACK_PERIOD = STATUS + "/" + MOTION + "/" + CALLBACK_PERIOD;
        EVENT_MOTION = EVENT + "/" + MOTION;

        STATE = "status";
        STATUS_STATE_CALLBACK_PERIOD = STATUS + "/" + STATE + "/" + CALLBACK_PERIOD;
        EVENT_STATE = EVENT + "/" + STATE;
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
    }

}
