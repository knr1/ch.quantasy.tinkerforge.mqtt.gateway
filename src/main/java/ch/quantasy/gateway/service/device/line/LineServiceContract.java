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
package ch.quantasy.gateway.service.device.line;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.line.LineDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class LineServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String REFLECTIVITY;
    public final String STATUS_REFLECTIVITY;
    public final String STATUS_REFLECTIVITY_THRESHOLD;
    public final String STATUS_REFLECTIVITY_CALLBACK_PERIOD;
    public final String EVENT_REFLECTIVITY;
    public final String EVENT_REFLECTIVITY_REACHED;
    private final String INTENT_REFLECTIVITY;
    public final String INTENT_REFLECTIVITY_THRESHOLD;
    public final String INTENT_REFLECTIVITY_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    private final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public LineServiceContract(LineDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LineServiceContract(String id, String device) {
        super(id, device);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        REFLECTIVITY = "reflectivity";
        STATUS_REFLECTIVITY = STATUS + "/" + REFLECTIVITY;
        STATUS_REFLECTIVITY_THRESHOLD = STATUS_REFLECTIVITY + "/" + THRESHOLD;
        STATUS_REFLECTIVITY_CALLBACK_PERIOD = STATUS_REFLECTIVITY + "/" + CALLBACK_PERIOD;
        EVENT_REFLECTIVITY = EVENT + "/" + REFLECTIVITY;
        EVENT_REFLECTIVITY_REACHED = EVENT_REFLECTIVITY + "/" + REACHED;
        INTENT_REFLECTIVITY = INTENT + "/" + REFLECTIVITY;
        INTENT_REFLECTIVITY_THRESHOLD = INTENT_REFLECTIVITY + "/" + THRESHOLD;
        INTENT_REFLECTIVITY_CALLBACK_PERIOD = INTENT_REFLECTIVITY + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_REFLECTIVITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_REFLECTIVITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");

        descriptions.put(EVENT_REFLECTIVITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [[0..4095]\n");
        descriptions.put(EVENT_REFLECTIVITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        descriptions.put(STATUS_REFLECTIVITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_REFLECTIVITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");

    }
}
