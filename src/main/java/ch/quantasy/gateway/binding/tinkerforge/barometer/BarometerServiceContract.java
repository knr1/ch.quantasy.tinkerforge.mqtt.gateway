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
package ch.quantasy.gateway.binding.tinkerforge.barometer;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.barometer.AirPressureEvent;
import ch.quantasy.gateway.binding.tinkerforge.barometer.AltitudeEvent;
import ch.quantasy.gateway.binding.tinkerforge.barometer.BarometerIntent;
import ch.quantasy.gateway.binding.tinkerforge.barometer.AirPressureCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.barometer.AirPressureCallbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.barometer.AltitudeCallbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.barometer.AveragingStatus;
import ch.quantasy.gateway.binding.tinkerforge.barometer.DebouncePeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.barometer.ReferenceAirPressureStatus;
import ch.quantasy.gateway.binding.tinkerforge.barometer.AltitudeCallbackPeriodStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class BarometerServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String AIR_PRESSURE;
    public final String STATUS_AIR_PRESSURE;
    public final String STATUS_AIR_PRESSURE_THRESHOLD;
    public final String STATUS_AIR_PRESSURE_CALLBACK_PERIOD;
    public final String EVENT_AIR_PRESSURE;
    public final String EVENT_AIR_PRESSURE_REACHED;

    public final String ALTITUDE;
    public final String STATUS_ALTITUDE;
    public final String STATUS_ALTITUDE_THRESHOLD;
    public final String STATUS_ALTITUDE_CALLBACK_PERIOD;
    public final String EVENT_ALTITUDE;
    public final String EVENT_ALTITUDE_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String AVERAGING;
    public final String STATUS_AVERAGING;

    public final String REFERENCE_AIR_PRESSURE;
    public final String STATUS_REFERENCE_AIR_PRESSURE;

    public BarometerServiceContract(BarometerDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public BarometerServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Barometer.toString());
    }

    public BarometerServiceContract(String id, String device) {
        super(id, device, BarometerIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        AIR_PRESSURE = "airPressure";
        STATUS_AIR_PRESSURE = STATUS + "/" + AIR_PRESSURE;
        STATUS_AIR_PRESSURE_THRESHOLD = STATUS_AIR_PRESSURE + "/" + THRESHOLD;
        STATUS_AIR_PRESSURE_CALLBACK_PERIOD = STATUS_AIR_PRESSURE + "/" + CALLBACK_PERIOD;
        EVENT_AIR_PRESSURE = EVENT + "/" + AIR_PRESSURE;
        EVENT_AIR_PRESSURE_REACHED = EVENT_AIR_PRESSURE + "/" + REACHED;

        ALTITUDE = "altitude";
        STATUS_ALTITUDE = STATUS + "/" + ALTITUDE;
        STATUS_ALTITUDE_THRESHOLD = STATUS_ALTITUDE + "/" + THRESHOLD;
        STATUS_ALTITUDE_CALLBACK_PERIOD = STATUS_ALTITUDE + "/" + CALLBACK_PERIOD;
        EVENT_ALTITUDE = EVENT + "/" + ALTITUDE;
        EVENT_ALTITUDE_REACHED = EVENT_ALTITUDE + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;

        AVERAGING = "averaging";
        STATUS_AVERAGING = STATUS + "/" + AVERAGING;

        REFERENCE_AIR_PRESSURE = "referenceAirPressure";
        STATUS_REFERENCE_AIR_PRESSURE = STATUS + "/" + REFERENCE_AIR_PRESSURE;

    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_AIR_PRESSURE, AirPressureEvent.class);
        messageTopicMap.put(EVENT_ALTITUDE, AltitudeEvent.class);
        messageTopicMap.put(EVENT_AIR_PRESSURE_REACHED, AirPressureEvent.class);
        messageTopicMap.put(EVENT_ALTITUDE_REACHED, AltitudeEvent.class);
        messageTopicMap.put(STATUS_AIR_PRESSURE_CALLBACK_PERIOD, AirPressureCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ALTITUDE_CALLBACK_PERIOD, AltitudeCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_AIR_PRESSURE_THRESHOLD, AirPressureCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_ALTITUDE_THRESHOLD, AltitudeCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
        messageTopicMap.put(STATUS_AVERAGING, AveragingStatus.class);
        messageTopicMap.put(STATUS_REFERENCE_AIR_PRESSURE, ReferenceAirPressureStatus.class);
    }

}
