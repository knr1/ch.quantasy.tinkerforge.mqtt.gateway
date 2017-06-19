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
package ch.quantasy.gateway.service.device.barometer;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
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
    private final String INTENT_AIR_PRESSURE;
    public final String INTENT_AIR_PRESSURE_THRESHOLD;
    public final String INTENT_AIR_PRESSURE_CALLBACK_PERIOD;

    public final String ALTITUDE;
    public final String STATUS_ALTITUDE;
    public final String STATUS_ALTITUDE_THRESHOLD;
    public final String STATUS_ALTITUDE_CALLBACK_PERIOD;
    public final String EVENT_ALTITUDE;
    public final String EVENT_ALTITUDE_REACHED;
    private final String INTENT_ALTITUDE;
    public final String INTENT_ALTITUDE_THRESHOLD;
    public final String INTENT_ALTITUDE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    private final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String AVERAGING;
    public final String STATUS_AVERAGING;
    public final String INTENT_AVERAGING;

    public final String REFERENCE_AIR_PRESSURE;
    public final String STATUS_REFERENCE_AIR_PRESSURE;
    public final String INTENT_REFERENCE_AIR_PRESSURE;

    public BarometerServiceContract(BarometerDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public BarometerServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Barometer.toString());
    }

    public BarometerServiceContract(String id, String device) {
        super(id, device);

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
        INTENT_AIR_PRESSURE = INTENT + "/" + AIR_PRESSURE;
        INTENT_AIR_PRESSURE_THRESHOLD = INTENT_AIR_PRESSURE + "/" + THRESHOLD;
        INTENT_AIR_PRESSURE_CALLBACK_PERIOD = INTENT_AIR_PRESSURE + "/" + CALLBACK_PERIOD;

        ALTITUDE = "altitude";
        STATUS_ALTITUDE = STATUS + "/" + ALTITUDE;
        STATUS_ALTITUDE_THRESHOLD = STATUS_ALTITUDE + "/" + THRESHOLD;
        STATUS_ALTITUDE_CALLBACK_PERIOD = STATUS_ALTITUDE + "/" + CALLBACK_PERIOD;
        EVENT_ALTITUDE = EVENT + "/" + ALTITUDE;
        EVENT_ALTITUDE_REACHED = EVENT_ALTITUDE + "/" + REACHED;
        INTENT_ALTITUDE = INTENT + "/" + ALTITUDE;
        INTENT_ALTITUDE_THRESHOLD = INTENT_ALTITUDE + "/" + THRESHOLD;
        INTENT_ALTITUDE_CALLBACK_PERIOD = INTENT_ALTITUDE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        AVERAGING = "averaging";
        STATUS_AVERAGING = STATUS + "/" + AVERAGING;
        INTENT_AVERAGING = INTENT + "/" + AVERAGING;

        REFERENCE_AIR_PRESSURE = "referenceAirPressure";
        INTENT_REFERENCE_AIR_PRESSURE = INTENT + "/" + REFERENCE_AIR_PRESSURE;
        STATUS_REFERENCE_AIR_PRESSURE = STATUS + "/" + REFERENCE_AIR_PRESSURE;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_AIR_PRESSURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_AIR_PRESSURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [10000..1200000]\n max: [10000..1200000]");
        descriptions.put(INTENT_ALTITUDE_THRESHOLD, "option: [x|o|i|<|>]\n min: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n max: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        descriptions.put(INTENT_AVERAGING, "averagingPressure: [0..10]\n averagingTemperature: [0..255]\n movingAveragePressure: [0..25]");
        descriptions.put(INTENT_REFERENCE_AIR_PRESSURE, "[" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        descriptions.put(EVENT_AIR_PRESSURE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [10000..1200000]\n");
        descriptions.put(EVENT_ALTITUDE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        descriptions.put(EVENT_AIR_PRESSURE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [10000..1200000]\n");
        descriptions.put(EVENT_ALTITUDE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        descriptions.put(STATUS_AIR_PRESSURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_AIR_PRESSURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [10000..1200000]\n max: [10000..1200000]");
        descriptions.put(STATUS_ALTITUDE_THRESHOLD, "option: [x|o|i|<|>]\n min: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n max: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_AVERAGING, "averagingPressure: [0..10]\n averagingTemperature: [0..255]\n movingAveragePressure: [0..25]");
        descriptions.put(STATUS_REFERENCE_AIR_PRESSURE, "[" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
    }
}
