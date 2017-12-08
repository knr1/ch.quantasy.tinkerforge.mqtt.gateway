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
package ch.quantasy.gateway.service.device.laserRangeFinder;

import ch.quantasy.gateway.message.laserRangeFinder.VelocityEvent;
import ch.quantasy.gateway.message.laserRangeFinder.DistanceEvent;
import ch.quantasy.gateway.message.laserRangeFinder.LaserRangeFinderIntent;
import ch.quantasy.gateway.message.laserRangeFinder.ConfigurationStatus;
import ch.quantasy.gateway.message.laserRangeFinder.DebouncePeriodStatus;
import ch.quantasy.gateway.message.laserRangeFinder.DistanceCallbackPeriodStatus;
import ch.quantasy.gateway.message.laserRangeFinder.DistanceCallbackThresholdStatus;
import ch.quantasy.gateway.message.laserRangeFinder.LaserEnabledStatus;
import ch.quantasy.gateway.message.laserRangeFinder.ModeStatus;
import ch.quantasy.gateway.message.laserRangeFinder.MovingAverageStatus;
import ch.quantasy.gateway.message.laserRangeFinder.SensorHardwareStatus;
import ch.quantasy.gateway.message.laserRangeFinder.VelocityCallbackPeriodStatus;
import ch.quantasy.gateway.message.laserRangeFinder.VelocityCallbackThresholdStatus;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class LaserRangeFinderServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String DISTANCE;
    public final String STATUS_DISTANCE_VALUE;
    public final String STATUS_DISTANCE_THRESHOLD;
    public final String STATUS_DISTANCE_CALLBACK_PERIOD;
    public final String EVENT_DISTANCE;
    public final String EVENT_DISTANCE_REACHED;

    public final String VELOCITY;
    public final String STATUS_VELOCITY;
    public final String STATUS_VELOCITY_THRESHOLD;
    public final String STATUS_VELOCITY_CALLBACK_PERIOD;
    public final String EVENT_VELOCITY;
    public final String EVENT_VELOCITY_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String LASER;
    public final String STATUS_LASER;

    public final String MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;

    public final String DEVICE_MODE;
    public final String STATUS_DEVICE_MODE;

    public final String DEVICE_CONFIGURATION;
    public final String STATUS_DEVICE_CONFIGURATION;

    public final String SENSOR_HARDWARE;
    public final String STATUS_SENSOR_HARDWARE;

    public LaserRangeFinderServiceContract(LaserRangeFinderDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LaserRangeFinderServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.LaserRangeFinder.toString());
    }

    public LaserRangeFinderServiceContract(String id, String device) {
        super(id, device, LaserRangeFinderIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        DISTANCE = "distance";
        STATUS_DISTANCE_VALUE = STATUS + "/" + DISTANCE;
        STATUS_DISTANCE_THRESHOLD = STATUS_DISTANCE_VALUE + "/" + THRESHOLD;
        STATUS_DISTANCE_CALLBACK_PERIOD = STATUS_DISTANCE_VALUE + "/" + CALLBACK_PERIOD;
        EVENT_DISTANCE = EVENT + "/" + DISTANCE;
        EVENT_DISTANCE_REACHED = EVENT_DISTANCE + "/" + REACHED;

        VELOCITY = "velocity";
        STATUS_VELOCITY = STATUS + "/" + VELOCITY;
        STATUS_VELOCITY_THRESHOLD = STATUS_VELOCITY + "/" + THRESHOLD;
        STATUS_VELOCITY_CALLBACK_PERIOD = STATUS_VELOCITY + "/" + CALLBACK_PERIOD;
        EVENT_VELOCITY = EVENT + "/" + VELOCITY;
        EVENT_VELOCITY_REACHED = EVENT_VELOCITY + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;

        LASER = "laser";
        STATUS_LASER = STATUS + "/" + LASER;

        MOVING_AVERAGE = "movingAverage";
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;

        DEVICE_MODE = "deviceMode";
        STATUS_DEVICE_MODE = STATUS + "/" + DEVICE_MODE;

        DEVICE_CONFIGURATION = "deviceConfiguration";
        STATUS_DEVICE_CONFIGURATION = STATUS + "/" + DEVICE_CONFIGURATION;

        SENSOR_HARDWARE = "sensorHardware";
        STATUS_SENSOR_HARDWARE = STATUS + "/" + SENSOR_HARDWARE;
        addMessageTopic(EVENT_DISTANCE, DistanceEvent.class);
        addMessageTopic(EVENT_VELOCITY, VelocityEvent.class);
        addMessageTopic(EVENT_DISTANCE_REACHED, DistanceEvent.class);
        addMessageTopic(EVENT_VELOCITY_REACHED, VelocityEvent.class);
        addMessageTopic(STATUS_DISTANCE_CALLBACK_PERIOD, DistanceCallbackPeriodStatus.class);
        addMessageTopic(STATUS_VELOCITY_CALLBACK_PERIOD, VelocityCallbackPeriodStatus.class);
        addMessageTopic(STATUS_DISTANCE_THRESHOLD, DistanceCallbackThresholdStatus.class);
        addMessageTopic(STATUS_VELOCITY_THRESHOLD, VelocityCallbackThresholdStatus.class);
        addMessageTopic(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
        addMessageTopic(STATUS_DEVICE_MODE, ModeStatus.class);
        addMessageTopic(STATUS_DEVICE_CONFIGURATION, ConfigurationStatus.class);
        addMessageTopic(STATUS_SENSOR_HARDWARE, SensorHardwareStatus.class);
        addMessageTopic(STATUS_LASER, LaserEnabledStatus.class);
        addMessageTopic(STATUS_MOVING_AVERAGE, MovingAverageStatus.class);
    }

  
}
