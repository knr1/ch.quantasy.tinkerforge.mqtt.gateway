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
package ch.quantasy.gateway.service.device.moisture;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.moisture.MoistureDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class MoistureServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String REACHED_TOPIC;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String MOISTURE;
    public final String STATUS_MOISTURE;
    public final String STATUS_MOISTURE_THRESHOLD;
    public final String STATUS_MOISTURE_CALLBACK_PERIOD;
    public final String EVENT_MOISTURE;
    public final String EVENT_MOISTURE_REACHED;
    private final String INTENT_MOISTURE;
    public final String INTENT_MOISTURE_THRESHOLD;
    public final String INTENT_MOISTURE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    private final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;
    public final String INTENT_MOVING_AVERAGE;

    public MoistureServiceContract(MoistureDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public MoistureServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Moisture.toString());
    }

    public MoistureServiceContract(String id, String device) {
        super(id, device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        REACHED_TOPIC = "/" + REACHED;

        MOISTURE = "moisture";
        STATUS_MOISTURE = STATUS + "/" + MOISTURE;
        STATUS_MOISTURE_THRESHOLD = STATUS_MOISTURE + "/" + THRESHOLD;
        STATUS_MOISTURE_CALLBACK_PERIOD = STATUS_MOISTURE + "/" + CALLBACK_PERIOD;
        EVENT_MOISTURE = EVENT + "/" + MOISTURE;
        EVENT_MOISTURE_REACHED = EVENT_MOISTURE + REACHED_TOPIC;
        INTENT_MOISTURE = INTENT + "/" + MOISTURE;
        INTENT_MOISTURE_THRESHOLD = INTENT_MOISTURE + "/" + THRESHOLD;
        INTENT_MOISTURE_CALLBACK_PERIOD = INTENT_MOISTURE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        MOVING_AVERAGE = "movingAverage";
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;
        INTENT_MOVING_AVERAGE = INTENT + "/" + MOVING_AVERAGE;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_MOISTURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_MOISTURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        descriptions.put(INTENT_MOVING_AVERAGE, "[0..100]");

        descriptions.put(EVENT_MOISTURE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0..4095]");
        descriptions.put(EVENT_MOISTURE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0..4095]");
        descriptions.put(STATUS_MOISTURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_MOISTURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_MOVING_AVERAGE, "[0..100]");
    }
}
