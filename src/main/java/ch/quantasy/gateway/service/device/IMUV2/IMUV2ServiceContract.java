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

import ch.quantasy.gateway.message.event.IMU.AccelerationEvent;
import ch.quantasy.gateway.message.event.IMU.AngularVelocityEvent;
import ch.quantasy.gateway.message.event.IMU.GravityVectorEvent;
import ch.quantasy.gateway.message.event.IMU.LinearAccelerationEvent;
import ch.quantasy.gateway.message.event.IMU.MagneticFieldEvent;
import ch.quantasy.gateway.message.event.IMU.OrientationEvent;
import ch.quantasy.gateway.message.event.IMU.QuaternionEvent;
import ch.quantasy.gateway.message.event.IMU.TemperatureEvent;
import ch.quantasy.gateway.message.event.IMUV2.AllDataEvent;
import ch.quantasy.gateway.message.intent.IMUV2.IMUV2Intent;
import ch.quantasy.gateway.message.status.IMU.AccelerationCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.IMU.AllDataCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.IMU.AngularVelocityCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.IMU.LedsStatus;
import ch.quantasy.gateway.message.status.IMU.MagneticFieldCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.IMU.OrientationCalculationStatus;
import ch.quantasy.gateway.message.status.IMU.OrientationCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.IMU.QuaternionCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.IMU.StatusLEDStatus;
import ch.quantasy.gateway.message.status.IMUV2.GravityVectorCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.IMUV2.LinearAccelerationCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.IMUV2.SensorFusionModeStatus;
import ch.quantasy.gateway.message.status.IMUV2.TemperatureCallbackPeriodStatus;
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

        addMessageTopic(EVENT_ACCELERATION, AccelerationEvent.class);
        addMessageTopic(EVENT_ANGULAR_VELOCITY, AngularVelocityEvent.class);
        addMessageTopic(EVENT_GRAVITY_VECTOR, GravityVectorEvent.class);
        addMessageTopic(EVENT_LINEAR_ACCELERATION, LinearAccelerationEvent.class);
        addMessageTopic(EVENT_MAGNETIC_FIELD, MagneticFieldEvent.class);
        addMessageTopic(EVENT_ORIENTATION, OrientationEvent.class);
        addMessageTopic(EVENT_QUATERNION, QuaternionEvent.class);
        addMessageTopic(EVENT_TEMPERATURE, TemperatureEvent.class);
        addMessageTopic(EVENT_ALL_DATA, AllDataEvent.class);
        addMessageTopic(STATUS_ACCELERATION_CALLBACK_PERIOD, AccelerationCallbackPeriodStatus.class);
        addMessageTopic(STATUS_ALL_DATA_CALLBACK_PERIOD, AllDataCallbackPeriodStatus.class);
        addMessageTopic(STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, AngularVelocityCallbackPeriodStatus.class);
        addMessageTopic(STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, MagneticFieldCallbackPeriodStatus.class);
        addMessageTopic(STATUS_ORIENTATION_CALLBACK_PERIOD, OrientationCallbackPeriodStatus.class);
        addMessageTopic(STATUS_QUATERNION_CALLBACK_PERIOD, QuaternionCallbackPeriodStatus.class);
        addMessageTopic(STATUS_STATUS_LED, StatusLEDStatus.class);
        addMessageTopic(STATUS_LEDS, LedsStatus.class);
        addMessageTopic(STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD, GravityVectorCallbackPeriodStatus.class);
        addMessageTopic(STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD, LinearAccelerationCallbackPeriodStatus.class);
        addMessageTopic(STATUS_TEMPERATURE_CALLBACK_PERIOD, TemperatureCallbackPeriodStatus.class);
        addMessageTopic(STATUS_SENSOR_FUSION_MODE, SensorFusionModeStatus.class);

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        
    }
}
