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
package ch.quantasy.gateway.service.device.IMUV2;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.imuV2.IMUV2Device;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import java.util.Map;

/**
 *
 * @author reto
 */
public class IMUV2ServiceContract extends DeviceServiceContract {

    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String ACCELRATION;
    public final String INTENT_ACCELERATION_CALLBACK_PERIOD;
    public final String ALL_DATA;
    public final String INTENT_ALL_DATA_CALLBACK_PERIOD;
    public final String ANGULAR_VELOCITY;
    public final String INTENT_ANGULAR_VELOCITY_CALLBACK_PERIOD;
    public final String GRAVITY_VECTOR;
    public final String INTENT_GRAVITY_VECTOR_CALLBACK_PERIOD;
    public final String LINEAR_ACCELERATION;
    public final String INTENT_LINEAR_ACCELERATION_CALLBACK_PERIOD;
    public final String MAGNETIC_FIELD;
    public final String INTENT_MAGNETIC_FIELD_CALLBACK_PERIOD;
    public final String ORIENTATION;
    public final String INTENT_ORIENTATION_CALLBACK_PERIOD;
    public final String QUATERNION;
    public final String INTENT_QUATERNION_CALLBACK_PERIOD;
    public final String TEMPERATURE;
    public final String INTENT_TEMPERATURE_CALLBACK_PERIOD;
    public final String LEDS;
    public final String INTENT_LEDS;
    public final String STATUS_LED;
    public final String INTENT_STATUS_LED;
    public final String STATUS_STATUS_LED;
    public final String STATUS_LEDS;
    public final String STATUS_TEMPERATURE_CALLBACK_PERIOD;
    public final String STATUS_QUATERNION_CALLBACK_PERIOD;
    public final String STATUS_ORIENTATION_CALLBACK_PERIOD;
    public final String STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD;
    public final String STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD;
    public final String STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD;
    public final String STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD;
    public final String STATUS_ALL_DATA_CALLBACK_PERIOD;
    public final String STATUS_ACCELERATION_CALLBACK_PERIOD;
    public final String SENSOR_FUSION_MODE;
    public final String STATUS_SENSOR_FUSION_MODE;
    public final String INTENT_SENSOR_FUSION_MODE;
    public final String EVENT_ACCELERATION;
    public final String EVENT_ALL_DATA;
    public final String EVENT_ANGULAR_VELOCITY;
    public final String EVENT_GRAVITY_VECTOR;
    public final String EVENT_LINEAR_ACCELERATION;
    public final String EVENT_MAGNETIC_FIELD;
    public final String EVENT_ORIENTATION;
    public final String EVENT_QUATERNION;
    public final String EVENT_TEMPERATURE;

    public IMUV2ServiceContract(IMUV2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public IMUV2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.IMUV2.toString());
    }

    public IMUV2ServiceContract(String id, String device) {
        super(id, device);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";

        ACCELRATION = "acceleration";
        INTENT_ACCELERATION_CALLBACK_PERIOD = INTENT + "/" + ACCELRATION + "/" + CALLBACK_PERIOD;
        STATUS_ACCELERATION_CALLBACK_PERIOD = STATUS + "/" + ACCELRATION + "/" + CALLBACK_PERIOD;
        EVENT_ACCELERATION = EVENT + "/" + ACCELRATION;

        ALL_DATA = "allData";
        INTENT_ALL_DATA_CALLBACK_PERIOD = INTENT + "/" + ALL_DATA + "/" + CALLBACK_PERIOD;
        STATUS_ALL_DATA_CALLBACK_PERIOD = STATUS + "/" + ALL_DATA + "/" + CALLBACK_PERIOD;
        EVENT_ALL_DATA = EVENT + "/" + ALL_DATA;

        ANGULAR_VELOCITY = "angularVelocity";
        INTENT_ANGULAR_VELOCITY_CALLBACK_PERIOD = INTENT + "/" + ANGULAR_VELOCITY + "/" + CALLBACK_PERIOD;
        STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD = STATUS + "/" + ANGULAR_VELOCITY + "/" + CALLBACK_PERIOD;
        EVENT_ANGULAR_VELOCITY = EVENT + "/" + ANGULAR_VELOCITY;

        GRAVITY_VECTOR = "gravityVector";
        INTENT_GRAVITY_VECTOR_CALLBACK_PERIOD = INTENT + "/" + GRAVITY_VECTOR + "/" + CALLBACK_PERIOD;
        STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD = STATUS + "/" + GRAVITY_VECTOR + "/" + CALLBACK_PERIOD;
        EVENT_GRAVITY_VECTOR = EVENT + "/" + GRAVITY_VECTOR;

        LINEAR_ACCELERATION = "linearAcceleration";
        INTENT_LINEAR_ACCELERATION_CALLBACK_PERIOD = INTENT + "/" + LINEAR_ACCELERATION + "/" + CALLBACK_PERIOD;
        STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD = STATUS + "/" + LINEAR_ACCELERATION + "/" + CALLBACK_PERIOD;
        EVENT_LINEAR_ACCELERATION = EVENT + "/" + LINEAR_ACCELERATION;

        MAGNETIC_FIELD = "magneticField";
        INTENT_MAGNETIC_FIELD_CALLBACK_PERIOD = INTENT + "/" + MAGNETIC_FIELD + "/" + CALLBACK_PERIOD;
        STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD = STATUS + "/" + MAGNETIC_FIELD + "/" + CALLBACK_PERIOD;
        EVENT_MAGNETIC_FIELD = EVENT + "/" + MAGNETIC_FIELD;

        ORIENTATION = "orientation";
        INTENT_ORIENTATION_CALLBACK_PERIOD = INTENT + "/" + ORIENTATION + "/" + CALLBACK_PERIOD;
        STATUS_ORIENTATION_CALLBACK_PERIOD = STATUS + "/" + ORIENTATION + "/" + CALLBACK_PERIOD;
        EVENT_ORIENTATION = EVENT + "/" + ORIENTATION;

        QUATERNION = "quaternion";
        INTENT_QUATERNION_CALLBACK_PERIOD = INTENT + "/" + QUATERNION + "/" + CALLBACK_PERIOD;
        STATUS_QUATERNION_CALLBACK_PERIOD = STATUS + "/" + QUATERNION + "/" + CALLBACK_PERIOD;
        EVENT_QUATERNION = EVENT + "/" + QUATERNION;

        TEMPERATURE = "temperature";
        INTENT_TEMPERATURE_CALLBACK_PERIOD = INTENT + "/" + TEMPERATURE + "/" + CALLBACK_PERIOD;
        STATUS_TEMPERATURE_CALLBACK_PERIOD = STATUS + "/" + TEMPERATURE + "/" + CALLBACK_PERIOD;
        EVENT_TEMPERATURE = EVENT + "/" + TEMPERATURE;

        LEDS = "LEDs";
        INTENT_LEDS = INTENT + "/" + LEDS + "/" + CALLBACK_PERIOD;
        STATUS_LEDS = STATUS + "/" + LEDS + "/" + CALLBACK_PERIOD;

        STATUS_LED = "statusLED";
        INTENT_STATUS_LED = INTENT + "/" + STATUS_LED;
        STATUS_STATUS_LED = STATUS + "/" + STATUS_LED;

        SENSOR_FUSION_MODE = "sensorFusionMode";
        INTENT_SENSOR_FUSION_MODE = INTENT + "/" + SENSOR_FUSION_MODE;
        STATUS_SENSOR_FUSION_MODE = STATUS + "/" + SENSOR_FUSION_MODE;

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_ALL_DATA_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_ANGULAR_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_GRAVITY_VECTOR_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_LINEAR_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_MAGNETIC_FIELD_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_ORIENTATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_QUATERNION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_LEDS, "true|false]");
        descriptions.put(INTENT_STATUS_LED, "true|false]");
        descriptions.put(INTENT_SENSOR_FUSION_MODE, "[0..2]");

        descriptions.put(STATUS_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_ALL_DATA_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_ORIENTATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_QUATERNION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_STATUS_LED, "[true|false]");
        descriptions.put(STATUS_LEDS, "[true|false]");
        descriptions.put(STATUS_SENSOR_FUSION_MODE, "[0..2]");

        descriptions.put(EVENT_ACCELERATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_ANGULAR_VELOCITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_GRAVITY_VECTOR, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_LINEAR_ACCELERATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_MAGNETIC_FIELD, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_ORIENTATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n heading: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n roll: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n pitch: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_QUATERNION, "timestamp: [0.." + Long.MAX_VALUE + "]\n w: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_TEMPERATURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Byte.MIN_VALUE + ".." + Byte.MAX_VALUE + "]");
        descriptions.put(EVENT_ALL_DATA, "timestamp: [0.." + Long.MAX_VALUE + "]\n @acceleration \n @magneticField \n @angularVelocity \n @orientation \n @quaternion \n @linearAcceleration \n @gravityVector \n @temperature \n calibrationStatus: [0..255]");
    }
}
