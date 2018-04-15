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
package ch.quantasy.gateway.binding.tinkerforge.IMU;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.imu.IMUDevice;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import java.util.Map;

/**
 *
 * @author reto
 */
public class IMUServiceContract extends DeviceServiceContract {

    private final String PERIOD;
    private final String CALLBACK_PERIOD;
    private final String ACCELRATION;
    private final String ALL_DATA;
    private final String ANGULAR_VELOCITY;
    private final String MAGNETIC_FIELD;
    private final String ORIENTATION;
    private final String ORIENTATION_CALCULATION;
    private final String QUATERNION;
    private final String LEDS;
    public final String STATUS_ORIENTATION_CALCULATION;
    public final String STATUS_LED;
    public final String STATUS_STATUS_LED;
    public final String STATUS_LEDS;
    public final String STATUS_QUATERNION_CALLBACK_PERIOD;
    public final String STATUS_ORIENTATION_CALLBACK_PERIOD;
    public final String STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD;
    public final String STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD;
    public final String STATUS_ALL_DATA_CALLBACK_PERIOD;
    public final String STATUS_ACCELERATION_CALLBACK_PERIOD;
    public final String EVENT_ACCELERATION;
    public final String EVENT_ALL_DATA;
    public final String EVENT_ANGULAR_VELOCITY;
    public final String EVENT_MAGNETIC_FIELD;
    public final String EVENT_ORIENTATION;
    public final String EVENT_QUATERNION;

    public IMUServiceContract(IMUDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public IMUServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.IMU.toString());
    }

    public IMUServiceContract(String id, String device) {
        super(id, device, IMUIntent.class);

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

        MAGNETIC_FIELD = "magneticField";
        STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD = STATUS + "/" + MAGNETIC_FIELD + "/" + CALLBACK_PERIOD;
        EVENT_MAGNETIC_FIELD = EVENT + "/" + MAGNETIC_FIELD;

        ORIENTATION = "orientation";
        STATUS_ORIENTATION_CALLBACK_PERIOD = STATUS + "/" + ORIENTATION + "/" + CALLBACK_PERIOD;
        EVENT_ORIENTATION = EVENT + "/" + ORIENTATION;

        QUATERNION = "quaternion";
        STATUS_QUATERNION_CALLBACK_PERIOD = STATUS + "/" + QUATERNION + "/" + CALLBACK_PERIOD;
        EVENT_QUATERNION = EVENT + "/" + QUATERNION;

        LEDS = "LEDs";
        STATUS_LEDS = STATUS + "/" + LEDS + "/" + CALLBACK_PERIOD;

        STATUS_LED = "statusLED";
        STATUS_STATUS_LED = STATUS + "/" + STATUS_LED + "/" + CALLBACK_PERIOD;

        ORIENTATION_CALCULATION = ORIENTATION + "/" + "calculation";
        STATUS_ORIENTATION_CALCULATION = STATUS + "/" + ORIENTATION_CALCULATION;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
  messageTopicMap.put(EVENT_ACCELERATION, AccelerationEvent.class);
        messageTopicMap.put(EVENT_ANGULAR_VELOCITY, AngularVelocityEvent.class);
        messageTopicMap.put(EVENT_MAGNETIC_FIELD, MagneticFieldEvent.class);
        messageTopicMap.put(EVENT_ORIENTATION, OrientationEvent.class);
        messageTopicMap.put(EVENT_QUATERNION, QuaternionEvent.class);
        messageTopicMap.put(EVENT_ALL_DATA, AllDataEvent.class);
        messageTopicMap.put(STATUS_ACCELERATION_CALLBACK_PERIOD, AccelerationCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ALL_DATA_CALLBACK_PERIOD, AllDataCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, AngularVelocityCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, MagneticFieldCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ORIENTATION_CALLBACK_PERIOD, OrientationCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_QUATERNION_CALLBACK_PERIOD, QuaternionCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_STATUS_LED, StatusLEDStatus.class);
        messageTopicMap.put(STATUS_LEDS, LedsStatus.class);
        messageTopicMap.put(STATUS_ORIENTATION_CALCULATION, OrientationCalculationStatus.class);
    }

    
    
}
