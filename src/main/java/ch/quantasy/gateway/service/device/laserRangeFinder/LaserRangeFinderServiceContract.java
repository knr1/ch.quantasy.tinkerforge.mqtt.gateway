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
    public final String INTENT_DISTANCE_VALUE;
    public final String INTENT_DISTANCE_THRESHOLD;
    public final String INTENT_DISTANCE_CALLBACK_PERIOD;

    public final String VELOCITY;
    public final String STATUS_VELOCITY;
    public final String STATUS_VELOCITY_THRESHOLD;
    public final String STATUS_VELOCITY_CALLBACK_PERIOD;
    public final String EVENT_VELOCITY;
    public final String EVENT_VELOCITY_REACHED;
    private final String INTENT_VELOCITY;
    public final String INTENT_VELOCITY_THRESHOLD;
    public final String INTENT_VELOCITY_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    private final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String LASER;
    public final String INTENT_LASER;
    public final String STATUS_LASER;

    public final String MOVING_AVERAGE;
    public final String INTENT_MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;

    public final String DEVICE_MODE;
    public final String INTENT_DEVICE_MODE;
    public final String STATUS_DEVICE_MODE;

    public final String DEVICE_CONFIGURATION;
    public final String INTENT_DEVICE_CONFIGURATION;
    public final String STATUS_DEVICE_CONFIGURATION;

    public final String SENSOR_HARDWARE_VERSION;
    public final String STATUS_SENSOR_HARDWARE_VERSION;

    public LaserRangeFinderServiceContract(LaserRangeFinderDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LaserRangeFinderServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.LaserRangeFinder.toString());
    }

    public LaserRangeFinderServiceContract(String id, String device) {
        super(id, device);

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
        INTENT_DISTANCE_VALUE = INTENT + "/" + DISTANCE;
        INTENT_DISTANCE_THRESHOLD = INTENT_DISTANCE_VALUE + "/" + THRESHOLD;
        INTENT_DISTANCE_CALLBACK_PERIOD = INTENT_DISTANCE_VALUE + "/" + CALLBACK_PERIOD;

        VELOCITY = "velocity";
        STATUS_VELOCITY = STATUS + "/" + VELOCITY;
        STATUS_VELOCITY_THRESHOLD = STATUS_VELOCITY + "/" + THRESHOLD;
        STATUS_VELOCITY_CALLBACK_PERIOD = STATUS_VELOCITY + "/" + CALLBACK_PERIOD;
        EVENT_VELOCITY = EVENT + "/" + VELOCITY;
        EVENT_VELOCITY_REACHED = EVENT_VELOCITY + "/" + REACHED;
        INTENT_VELOCITY = INTENT + "/" + VELOCITY;
        INTENT_VELOCITY_THRESHOLD = INTENT_VELOCITY + "/" + THRESHOLD;
        INTENT_VELOCITY_CALLBACK_PERIOD = INTENT_VELOCITY + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        LASER = "laser";
        INTENT_LASER = INTENT + "/" + LASER;
        STATUS_LASER = STATUS + "/" + LASER;

        MOVING_AVERAGE = "movingAverage";
        INTENT_MOVING_AVERAGE = INTENT + "/" + MOVING_AVERAGE;
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;

        DEVICE_MODE = "deviceMode";
        INTENT_DEVICE_MODE = INTENT + "/" + DEVICE_MODE;
        STATUS_DEVICE_MODE = STATUS + "/" + DEVICE_MODE;

        DEVICE_CONFIGURATION = "deviceConfiguration";
        INTENT_DEVICE_CONFIGURATION = INTENT + "/" + DEVICE_CONFIGURATION;
        STATUS_DEVICE_CONFIGURATION = STATUS + "/" + DEVICE_CONFIGURATION;

        SENSOR_HARDWARE_VERSION = "sensorHardwareVersion";
        STATUS_SENSOR_HARDWARE_VERSION = STATUS + "/" + SENSOR_HARDWARE_VERSION;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_DISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_DISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4000]\n max: [0..4000]");
        descriptions.put(INTENT_VELOCITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [-127..127]\n max: [-127..127]");
        descriptions.put(INTENT_DEVICE_MODE, "mode: [distance|velocity_12_7|velocity_31_75|velocity_63_5|velocity_127]");
        descriptions.put(INTENT_DEVICE_CONFIGURATION, "acquisition: [1..255]\n quickTermination: [true|false]\n thresholdValue: [0..255]\n measurementFrequency: [0|10..500]");

        descriptions.put(INTENT_LASER, "[true|false]");
        descriptions.put(INTENT_MOVING_AVERAGE, "averagingDistance:[0..30]\n averagingVelocity:[0..30]");

        descriptions.put(EVENT_DISTANCE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0..4000]");
        descriptions.put(EVENT_VELOCITY, "- timestamp: [-127.." + Long.MAX_VALUE + "]\n  value: [0..127]");
        descriptions.put(EVENT_DISTANCE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0..4000]");
        descriptions.put(EVENT_VELOCITY_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-127..127]");
        descriptions.put(STATUS_DISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_DISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4000]\n max: [0..4000]");
        descriptions.put(STATUS_VELOCITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [-127..-127]\n max: [-127..127]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_DEVICE_MODE, "mode: [distance|velocity_12_7|velocity_31_75|velocity_63_5|velocity_127]");
        descriptions.put(STATUS_DEVICE_CONFIGURATION, "acquisition: [1..255]\n quickTermination: [true|false]\n thresholdValue: [0..255]\n measurementFrequency: [0|10..500]");
        descriptions.put(STATUS_SENSOR_HARDWARE_VERSION, "[v1|v3]");
        descriptions.put(STATUS_LASER, "[true|false]");
        descriptions.put(STATUS_MOVING_AVERAGE, "averagingDistance:[0..30]\n averagingVelocity:[0..30]");
    }
}
