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
package ch.quantasy.gateway.service.device.ptc;

import ch.quantasy.gateway.intent.ptc.PTCIntent;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.ptc.PTCDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class PTCServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String TEMPERATURE;
    public final String STATUS_TEMPERATURE;
    public final String STATUS_TEMPERATURE_THRESHOLD;
    public final String STATUS_TEMPERATURE_CALLBACK_PERIOD;
    public final String EVENT_TEMPERATURE;
    public final String EVENT_TEMPERATURE_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String NOISE_REDUCTION_FILTER;
    public final String STATUS_NOISE_REDUCTION_FILTER;

    private final String RESISTANCE;
    public final String STATUS_RESISTANCE;
    public final String STATUS_RESISTANCE_THRESHOLD;
    public final String STATUS_RESISTANCE_CALLBACK_PERIOD;
    public final String EVENT_RESISTANCE;
    public final String EVENT_RESISTANCE_REACHED;
    public final String WIRE_MODE;
    public final String STATUS_WIRE_MODE;

    public PTCServiceContract(PTCDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public PTCServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.PTC.toString());
    }

    public PTCServiceContract(String id, String device) {
        super(id, device, PTCIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        TEMPERATURE = "temperature";
        STATUS_TEMPERATURE = STATUS + "/" + TEMPERATURE;
        STATUS_TEMPERATURE_THRESHOLD = STATUS_TEMPERATURE + "/" + THRESHOLD;
        STATUS_TEMPERATURE_CALLBACK_PERIOD = STATUS_TEMPERATURE + "/" + CALLBACK_PERIOD;
        EVENT_TEMPERATURE = EVENT + "/" + TEMPERATURE;
        EVENT_TEMPERATURE_REACHED = EVENT_TEMPERATURE + "/" + REACHED;

        RESISTANCE = "resistance";
        STATUS_RESISTANCE = STATUS + "/" + RESISTANCE;
        STATUS_RESISTANCE_THRESHOLD = STATUS_RESISTANCE + "/" + THRESHOLD;
        STATUS_RESISTANCE_CALLBACK_PERIOD = STATUS_RESISTANCE + "/" + CALLBACK_PERIOD;
        EVENT_RESISTANCE = EVENT + "/" + RESISTANCE;
        EVENT_RESISTANCE_REACHED = EVENT_RESISTANCE + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;

        NOISE_REDUCTION_FILTER = "noiseReductionFilter";
        STATUS_NOISE_REDUCTION_FILTER = STATUS + "/" + NOISE_REDUCTION_FILTER;

        WIRE_MODE = "wireMode";
        STATUS_WIRE_MODE = STATUS + "/" + WIRE_MODE;

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(EVENT_TEMPERATURE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-24600..84900]");
        descriptions.put(EVENT_TEMPERATURE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-24600..84900]");
        descriptions.put(EVENT_RESISTANCE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0.." + Short.MAX_VALUE + "]");
        descriptions.put(EVENT_RESISTANCE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0.." + Short.MAX_VALUE + "]");
        descriptions.put(STATUS_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-24600..84900]\n max: [-24600..84900]");
        descriptions.put(STATUS_RESISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_RESISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0.." + Short.MAX_VALUE + "]\n max: [0.." + Short.MAX_VALUE + "]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_NOISE_REDUCTION_FILTER, "filter: [Hz_50|Hz_60]");
        descriptions.put(STATUS_WIRE_MODE, "[2|3|4]");
    }
}
