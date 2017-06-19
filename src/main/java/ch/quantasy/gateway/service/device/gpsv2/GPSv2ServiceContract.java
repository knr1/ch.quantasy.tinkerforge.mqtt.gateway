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
package ch.quantasy.gateway.service.device.gpsv2;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.gpsV2.GPSv2Device;
import java.util.Map;

/**
 *
 * @author reto
 */
public class GPSv2ServiceContract extends DeviceServiceContract {

    public final String CALLBACK_PERIOD;
    public final String ALTITUDE;
    public final String RESTART;

    public final String INTENT_COORDINATES_CALLBACK_PERIOD;
    public final String STATUS_COORDINATES_CALLBACK_PERIOD;

    public final String INTENT_DATE_TIME_CALLBACK_PERIOD;

    public final String INTENT_ALTITUDE_CALLBACK_PERIOD;
    public final String EVENT_ALTITUDE;
    public final String STATUS_ALTITUDE_CALLBACK_PERIOD;

    public final String INTENT_MOTION_CALLBACK_PERIOD;
    public String INTENT_RESTART;
    public final String COORDINATES;
    public final String DATE_TIME;
    public final String STATUS_DATE_TIME_CALLBACK_PERIOD;
    public final String EVENT_DATE_TIME;
    public final String EVENT_RESTART;
    public final String MOTION;
    public final String STATUS_MOTION_CALLBACK_PERIOD;
    public final String EVENT_MOTION;
    public final String STATE;
    public final String INTENT_STATE_CALLBACK_PERIOD;
    public final String STATUS_STATE_CALLBACK_PERIOD;
    public final String EVENT_COORDINATES;
    public final String EVENT_STATE;
    private final String LED_CONFIG;
    public final String INTENT_FIX_LED_CONFIG;
    public final String STATUS_FIX_LED_CONFIG;
    public final String INTENT_STATE_LED_CONFIG;
    public final String STATUS_STATE_LED_CONFIG;

    public GPSv2ServiceContract(GPSv2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public GPSv2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.GPSv2.toString());
    }

    public GPSv2ServiceContract(String id, String device) {
        super(id, device);

        CALLBACK_PERIOD = "callbackPeriod";

        COORDINATES = "coordinates";
        STATUS_COORDINATES_CALLBACK_PERIOD = STATUS + "/" + COORDINATES + "/" + CALLBACK_PERIOD;
        INTENT_COORDINATES_CALLBACK_PERIOD = INTENT + "/" + COORDINATES + "/" + CALLBACK_PERIOD;
        EVENT_COORDINATES = EVENT + "/" + COORDINATES;

        ALTITUDE = "altitude";
        STATUS_ALTITUDE_CALLBACK_PERIOD = STATUS + "/" + ALTITUDE + "/" + CALLBACK_PERIOD;
        INTENT_ALTITUDE_CALLBACK_PERIOD = INTENT + "/" + ALTITUDE + "/" + CALLBACK_PERIOD;
        EVENT_ALTITUDE = EVENT + "/" + ALTITUDE;

        RESTART = "restart";
        INTENT_RESTART = INTENT + "/" + RESTART;
        EVENT_RESTART = EVENT + "/" + RESTART;

        DATE_TIME = "dateTime";
        INTENT_DATE_TIME_CALLBACK_PERIOD = INTENT + "/" + DATE_TIME + "/" + CALLBACK_PERIOD;
        STATUS_DATE_TIME_CALLBACK_PERIOD = STATUS + "/" + DATE_TIME + "/" + CALLBACK_PERIOD;
        EVENT_DATE_TIME = EVENT + "/" + DATE_TIME;

        MOTION = "motion";
        INTENT_MOTION_CALLBACK_PERIOD = INTENT + "/" + MOTION + "/" + CALLBACK_PERIOD;
        STATUS_MOTION_CALLBACK_PERIOD = STATUS + "/" + MOTION + "/" + CALLBACK_PERIOD;
        EVENT_MOTION = EVENT + "/" + MOTION;

        STATE = "status";
        INTENT_STATE_CALLBACK_PERIOD = INTENT + "/" + STATE + "/" + CALLBACK_PERIOD;
        STATUS_STATE_CALLBACK_PERIOD = STATUS + "/" + STATE + "/" + CALLBACK_PERIOD;
        EVENT_STATE = EVENT + "/" + STATE;

        LED_CONFIG = "led";
        INTENT_STATE_LED_CONFIG = INTENT + "/" + STATE + "/" + LED_CONFIG;
        STATUS_STATE_LED_CONFIG = STATUS + "/" + STATE + "/" + LED_CONFIG;
        INTENT_FIX_LED_CONFIG = INTENT + "/" + "fix" + "/" + LED_CONFIG;
        STATUS_FIX_LED_CONFIG = STATUS + "/" + "fix" + "/" + LED_CONFIG;

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_COORDINATES_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_DATE_TIME_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_DATE_TIME_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

        descriptions.put(INTENT_MOTION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_MOTION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_COORDINATES_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

        descriptions.put(INTENT_STATE_LED_CONFIG, "[OFF|ON|HEARTBEAT|STATUS]");
        descriptions.put(STATUS_STATE_LED_CONFIG, "[OFF|ON|HEARTBEAT|STATUS]");

        descriptions.put(INTENT_FIX_LED_CONFIG, "[OFF|ON|HEARTBEAT|FIX|PPS]");
        descriptions.put(STATUS_FIX_LED_CONFIG, "[OFF|ON|HEARTBEAT|FIX|PPS]");

        descriptions.put(EVENT_ALTITUDE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n   altitude: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n   geoidalSeparation: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        descriptions.put(EVENT_DATE_TIME, "- timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n  date: [[d|dd]mmyy]\n  time: [hhmmssxxx]");
        descriptions.put(EVENT_MOTION, "- timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n  course: [0..36000]\n  speed: [0.." + Long.MAX_VALUE + "]");
        descriptions.put(EVENT_STATE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n  fix: [true|false]]\n  satellitesView: [0.." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_COORDINATES, "- timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n  latitude: [" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]\n   ns: ['N'|'S']\n   longitude: [" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]\n ew: ['E'|'W']");

    }
}
