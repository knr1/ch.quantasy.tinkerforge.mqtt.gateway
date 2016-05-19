/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service.device.laserRangeFinder;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDevice;

/**
 *
 * @author reto
 */
public class LaserRangeFinderServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String ANALOG_VALUE;
    public final String STATUS_ANALOG_VALUE;
    public final String STATUS_DISTANCE_THRESHOLD;
    public final String STATUS_DISTANCE_CALLBACK_PERIOD;
    public final String EVENT_DISTANCE;
    public final String EVENT_DISTANCE_REACHED;
    public final String INTENT_ANALOG_VALUE;
    public final String INTENT_DISTANCE_THRESHOLD;
    public final String INTENT_DISTANCE_CALLBACK_PERIOD;

    public final String ILLUMINANCE;
    public final String STATUS_ILLUMINANCE;
    public final String STATUS_VELOCITY_THRESHOLD;
    public final String STATUS_VELOCITY_CALLBACK_PERIOD;
    public final String EVENT_VELOCITY;
    public final String EVENT_VELOCITY_REACHED;
    public final String INTENT_ILLUMINANCE;
    public final String INTENT_VELOCITY_THRESHOLD;
    public final String INTENT_VELOCITY_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
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

    public LaserRangeFinderServiceContract(LaserRangeFinderDevice device) {
        super(device);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        ANALOG_VALUE = "analogValue";
        STATUS_ANALOG_VALUE = STATUS + "/" + ANALOG_VALUE;
        STATUS_DISTANCE_THRESHOLD = STATUS_ANALOG_VALUE + "/" + THRESHOLD;
        STATUS_DISTANCE_CALLBACK_PERIOD = STATUS_ANALOG_VALUE + "/" + CALLBACK_PERIOD;
        EVENT_DISTANCE = EVENT + "/" + ANALOG_VALUE;
        EVENT_DISTANCE_REACHED = EVENT_DISTANCE + "/" + REACHED;
        INTENT_ANALOG_VALUE = INTENT + "/" + ANALOG_VALUE;
        INTENT_DISTANCE_THRESHOLD = INTENT_ANALOG_VALUE + "/" + THRESHOLD;
        INTENT_DISTANCE_CALLBACK_PERIOD = INTENT_ANALOG_VALUE + "/" + CALLBACK_PERIOD;

        ILLUMINANCE = "illuminance";
        STATUS_ILLUMINANCE = STATUS + "/" + ILLUMINANCE;
        STATUS_VELOCITY_THRESHOLD = STATUS_ILLUMINANCE + "/" + THRESHOLD;
        STATUS_VELOCITY_CALLBACK_PERIOD = STATUS_ILLUMINANCE + "/" + CALLBACK_PERIOD;
        EVENT_VELOCITY = EVENT + "/" + ILLUMINANCE;
        EVENT_VELOCITY_REACHED = EVENT_VELOCITY + "/" + REACHED;
        INTENT_ILLUMINANCE = INTENT + "/" + ILLUMINANCE;
        INTENT_VELOCITY_THRESHOLD = INTENT_ILLUMINANCE + "/" + THRESHOLD;
        INTENT_VELOCITY_CALLBACK_PERIOD = INTENT_ILLUMINANCE + "/" + CALLBACK_PERIOD;

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
    }
}
