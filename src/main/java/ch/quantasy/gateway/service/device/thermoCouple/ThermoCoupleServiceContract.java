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
package ch.quantasy.gateway.service.device.thermoCouple;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.thermoCouple.ThermoCoupleDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class ThermoCoupleServiceContract extends DeviceServiceContract {

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
    private final String INTENT_TEMPERATURE;
    public final String INTENT_TEMPERATURE_THRESHOLD;
    public final String INTENT_TEMPERATURE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    private final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String STATUS_CONFIGURATION;
    public final String INTENT_CONFIGURATION;

    public final String ERROR;
    public final String EVENT_ERROR;

    public ThermoCoupleServiceContract(ThermoCoupleDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public ThermoCoupleServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.ThermoCouple.toString());
    }

    public ThermoCoupleServiceContract(String id, String device) {
        super(id, device);

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
        INTENT_TEMPERATURE = INTENT + "/" + TEMPERATURE;
        INTENT_TEMPERATURE_THRESHOLD = INTENT_TEMPERATURE + "/" + THRESHOLD;
        INTENT_TEMPERATURE_CALLBACK_PERIOD = INTENT_TEMPERATURE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;
        INTENT_CONFIGURATION = INTENT + "/" + CONFIGURATION;

        ERROR = "error";
        EVENT_ERROR = EVENT + "/" + ERROR;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-21000..180000]\n max: [-21000..180000]");
        descriptions.put(INTENT_CONFIGURATION, "averaging:[sample_1|sample_2|sample_4|smaple_8|sample_16]\n type: [B|E|J|K|N|R|S|T|G8|G32]\n filter: [Hz_50|Hz_60]");

        descriptions.put(EVENT_TEMPERATURE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-21000..180000]");
        descriptions.put(EVENT_TEMPERATURE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-21000..180000]");
        descriptions.put(STATUS_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-21000..180000]\n max: [-21000..180000]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_CONFIGURATION, "averaging:[sample_1|sample_2|sample_4|smaple_8|sample_16]\n type: [B|E|J|K|N|R|S|T|G8|G32]\n filter: [Hz_50|Hz_60]");
    }
}
