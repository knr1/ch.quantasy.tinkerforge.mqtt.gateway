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
package ch.quantasy.gateway.binding.tinkerforge.IMUV2;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.IMU.AccelerationEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMU.AngularVelocityEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMU.GravityVectorEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMU.LinearAccelerationEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMU.MagneticFieldEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMU.OrientationEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMU.QuaternionEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMU.TemperatureEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMUV2.AllDataEvent;
import ch.quantasy.gateway.binding.tinkerforge.IMUV2.IMUV2Intent;
import ch.quantasy.gateway.binding.tinkerforge.IMU.AccelerationCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMU.AllDataCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMU.AngularVelocityCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMU.LedsStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMU.MagneticFieldCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMU.OrientationCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMU.QuaternionCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMU.StatusLEDStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMUV2.GravityVectorCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMUV2.LinearAccelerationCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMUV2.SensorFusionModeStatus;
import ch.quantasy.gateway.binding.tinkerforge.IMUV2.TemperatureCallbackPeriodStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
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
    public final String ALL_DATA;
    public final String ANGULAR_VELOCITY;
    public final String GRAVITY_VECTOR;
    public final String LINEAR_ACCELERATION;
    public final String MAGNETIC_FIELD;
    public final String ORIENTATION;
    public final String QUATERNION;
    public final String TEMPERATURE;
    public final String LEDS;
    public final String STATUS_LED;
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
        super(id, device, IMUV2Intent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";

        ACCELRATION = "acceleration";
        STATUS_ACCELERATION_CALLBACK_PERIOD = STATUS + "/" + ACCELRATION + "/" + CALLBACK_PERIOD;
        EVENT_ACCELERATION = EVENT + "/" + ACCELRATION;

        ALL_DATA = "allData";
        STATUS_ALL_DATA_CALLBACK_PERIOD = STATUS + "/" + ALL_DATA + "/" + CALLBACK_PERIOD;
        EVENT_ALL_DATA = EVENT + "/" + ALL_DATA;

        ANGULAR_VELOCITY = "angularVelocity";
        STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD = STATUS + "/" + ANGULAR_VELOCITY + "/" + CALLBACK_PERIOD;
        EVENT_ANGULAR_VELOCITY = EVENT + "/" + ANGULAR_VELOCITY;

        GRAVITY_VECTOR = "gravityVector";
        STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD = STATUS + "/" + GRAVITY_VECTOR + "/" + CALLBACK_PERIOD;
        EVENT_GRAVITY_VECTOR = EVENT + "/" + GRAVITY_VECTOR;

        LINEAR_ACCELERATION = "linearAcceleration";
        STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD = STATUS + "/" + LINEAR_ACCELERATION + "/" + CALLBACK_PERIOD;
        EVENT_LINEAR_ACCELERATION = EVENT + "/" + LINEAR_ACCELERATION;

        MAGNETIC_FIELD = "magneticField";
        STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD = STATUS + "/" + MAGNETIC_FIELD + "/" + CALLBACK_PERIOD;
        EVENT_MAGNETIC_FIELD = EVENT + "/" + MAGNETIC_FIELD;

        ORIENTATION = "orientation";
        STATUS_ORIENTATION_CALLBACK_PERIOD = STATUS + "/" + ORIENTATION + "/" + CALLBACK_PERIOD;
        EVENT_ORIENTATION = EVENT + "/" + ORIENTATION;

        QUATERNION = "quaternion";
        STATUS_QUATERNION_CALLBACK_PERIOD = STATUS + "/" + QUATERNION + "/" + CALLBACK_PERIOD;
        EVENT_QUATERNION = EVENT + "/" + QUATERNION;

        TEMPERATURE = "temperature";
        STATUS_TEMPERATURE_CALLBACK_PERIOD = STATUS + "/" + TEMPERATURE + "/" + CALLBACK_PERIOD;
        EVENT_TEMPERATURE = EVENT + "/" + TEMPERATURE;

        LEDS = "LEDs";
        STATUS_LEDS = STATUS + "/" + LEDS + "/" + CALLBACK_PERIOD;

        STATUS_LED = "statusLED";
        STATUS_STATUS_LED = STATUS + "/" + STATUS_LED;

        SENSOR_FUSION_MODE = "sensorFusionMode";
        STATUS_SENSOR_FUSION_MODE = STATUS + "/" + SENSOR_FUSION_MODE;

    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_ACCELERATION, AccelerationEvent.class);
        messageTopicMap.put(EVENT_ANGULAR_VELOCITY, AngularVelocityEvent.class);
        messageTopicMap.put(EVENT_GRAVITY_VECTOR, GravityVectorEvent.class);
        messageTopicMap.put(EVENT_LINEAR_ACCELERATION, LinearAccelerationEvent.class);
        messageTopicMap.put(EVENT_MAGNETIC_FIELD, MagneticFieldEvent.class);
        messageTopicMap.put(EVENT_ORIENTATION, OrientationEvent.class);
        messageTopicMap.put(EVENT_QUATERNION, QuaternionEvent.class);
        messageTopicMap.put(EVENT_TEMPERATURE, TemperatureEvent.class);
        messageTopicMap.put(EVENT_ALL_DATA, AllDataEvent.class);
        messageTopicMap.put(STATUS_ACCELERATION_CALLBACK_PERIOD, AccelerationCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ALL_DATA_CALLBACK_PERIOD, AllDataCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, AngularVelocityCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, MagneticFieldCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ORIENTATION_CALLBACK_PERIOD, OrientationCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_QUATERNION_CALLBACK_PERIOD, QuaternionCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_STATUS_LED, StatusLEDStatus.class);
        messageTopicMap.put(STATUS_LEDS, LedsStatus.class);
        messageTopicMap.put(STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD, GravityVectorCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD, LinearAccelerationCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_TEMPERATURE_CALLBACK_PERIOD, TemperatureCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_SENSOR_FUSION_MODE, SensorFusionModeStatus.class);
    }

}
