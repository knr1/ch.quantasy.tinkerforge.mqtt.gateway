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
package ch.quantasy.gateway.service.device.master;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.master.MasterDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class MasterServiceContract extends DeviceServiceContract {

    public final String STATUS_LED_ENABLED;
    public final String STATUS_STATUS_LED_ENABLED;
    public final String INTENT_STATUS_LED_ENABLED;

    public final String CALLBACK_THRESHOLD;
    public final String INTENT_STACK_CURRENT_CALLBACK_THRESHOLD;
    public final String STATUS_CURRENT_CALLBACK_THRESHOLD;
    private final String REACHED;

    public final String CALLBACK_PERIOD;

    public final String USB;
    public final String USB_VOLTAGE;
    public final String EVENT_USB_VOLTAGE;
    public final String EVENT_USB_VOLTAGE_REACHED;
    public final String INTENT_USB_VOLTAGE;
    public final String STATUS_USB_VOLTAGE;

    public final String INTENT_USB_VOLTAGE_CALLBACK_PERIOD;
    public final String STATUS_USB_VOLTAGE_CALLBACK_PERIOD;
    public final String INTENT_USB_VOLTAGE_CALLBACK_THRESHOLD;
    public final String STATUS_USB_VOLTAGE_CALLBACK_THRESHOLD;

    public final String STACK;
    public final String CURRENT;
    public final String STACK_CURRENT;
    public final String INTENT_STACK_CURRENT;
    public final String STATUS_STACK_CURRENT;
    public final String EVENT_STACK_CURRENT;
    public final String EVENT_STACK_CURRENT_REACHED;
    public final String INTENT_STACK_CURRENT_CALLBACK_PERIOD;
    public final String STATUS_STACK_CURRENT_CALLBACK_PERIOD;

    public final String VOLTAGE;
    public final String STACK_VOLTAGE;
    public final String EVENT_STACK_VOLTAGE;

    public final String EVENT_STACK_VOLTAGE_REACHED;

    public final String INTENT_STACK_VOLTAGE;
    public final String STATUS_STACK_VOLTAGE;

    public final String INTENT_STACK_VOLTAGE_CALLBACK_PERIOD;
    public final String STATUS_STACK_VOLTAGE_CALLBACK_PERIOD;

    private final String PERIOD;
    public final String DEBOUNCE_PERIOD;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String INTENT_STACK_VOLTAGE_CALLBACK_THRESHOLD;
    public final String STATUS_STACK_VOLTAGE_CALLBACK_THRESHOLD;

    public final String EVENT_RESET;

    public MasterServiceContract(MasterDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public MasterServiceContract(String id, String device) {
        super(id, device);
        EVENT_RESET = EVENT + "/" + "reset";
        STACK = "stack";
        PERIOD = "period";
        STATUS_LED_ENABLED = "statusLED/enabled";
        STATUS_STATUS_LED_ENABLED = STATUS + "/" + STATUS_LED_ENABLED;
        INTENT_STATUS_LED_ENABLED = INTENT + "/" + STATUS_LED_ENABLED;

        CALLBACK_PERIOD = "callbackPeriod";

        DEBOUNCE_PERIOD = "debounce/" + PERIOD;
        INTENT_DEBOUNCE_PERIOD = INTENT + "/" + DEBOUNCE_PERIOD;
        STATUS_DEBOUNCE_PERIOD = STATUS + "/" + DEBOUNCE_PERIOD;

        REACHED = "reached";
        CURRENT = "current";
        STACK_CURRENT = STACK + "/" + CURRENT;
        EVENT_STACK_CURRENT = EVENT + "/" + STACK_CURRENT;
        EVENT_STACK_CURRENT_REACHED = EVENT_STACK_CURRENT + "/" + REACHED;
        STATUS_STACK_CURRENT = STATUS + "/" + STACK_CURRENT;
        STATUS_STACK_CURRENT_CALLBACK_PERIOD = STATUS_STACK_CURRENT + "/" + CALLBACK_PERIOD;
        INTENT_STACK_CURRENT = INTENT + "/" + STACK_CURRENT;
        INTENT_STACK_CURRENT_CALLBACK_PERIOD = INTENT_STACK_CURRENT + "/" + CALLBACK_PERIOD;

        CALLBACK_THRESHOLD = "callbackThreshold";
        INTENT_STACK_CURRENT_CALLBACK_THRESHOLD = INTENT_STACK_CURRENT + "/" + CALLBACK_THRESHOLD;
        STATUS_CURRENT_CALLBACK_THRESHOLD = STATUS_STACK_CURRENT + "/" + CALLBACK_THRESHOLD;

        VOLTAGE = "voltage";
        STACK_VOLTAGE = STACK + "/" + VOLTAGE;
        EVENT_STACK_VOLTAGE = EVENT + "/" + STACK_VOLTAGE;
        EVENT_STACK_VOLTAGE_REACHED = EVENT_STACK_VOLTAGE + "/" + REACHED;

        INTENT_STACK_VOLTAGE = INTENT + "/" + STACK_VOLTAGE;
        STATUS_STACK_VOLTAGE = STATUS + "/" + STACK_VOLTAGE;
        STATUS_STACK_VOLTAGE_CALLBACK_PERIOD = STATUS_STACK_VOLTAGE + "/" + CALLBACK_PERIOD;
        STATUS_STACK_VOLTAGE_CALLBACK_THRESHOLD = STATUS_STACK_VOLTAGE + "/" + CALLBACK_THRESHOLD;

        INTENT_STACK_VOLTAGE_CALLBACK_PERIOD = INTENT_STACK_VOLTAGE + "/" + CALLBACK_PERIOD;
        INTENT_STACK_VOLTAGE_CALLBACK_THRESHOLD = INTENT_STACK_VOLTAGE + "/" + CALLBACK_THRESHOLD;

        USB = "USB";
        USB_VOLTAGE = USB + "/" + VOLTAGE;
        EVENT_USB_VOLTAGE = EVENT + "/" + USB_VOLTAGE;
        EVENT_USB_VOLTAGE_REACHED = EVENT_USB_VOLTAGE + "/" + REACHED;
        INTENT_USB_VOLTAGE = INTENT + "/" + USB_VOLTAGE;
        STATUS_USB_VOLTAGE = STATUS + "/" + USB_VOLTAGE;
        STATUS_USB_VOLTAGE_CALLBACK_PERIOD = STATUS_USB_VOLTAGE + "/" + CALLBACK_PERIOD;
        INTENT_USB_VOLTAGE_CALLBACK_PERIOD = INTENT_USB_VOLTAGE + "/" + CALLBACK_PERIOD;

        INTENT_USB_VOLTAGE_CALLBACK_THRESHOLD = INTENT_USB_VOLTAGE + "/" + CALLBACK_THRESHOLD;
        STATUS_USB_VOLTAGE_CALLBACK_THRESHOLD = STATUS_USB_VOLTAGE + "/" + CALLBACK_THRESHOLD;

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_STACK_CURRENT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_STATUS_LED_ENABLED, "[true|false]");
        descriptions.put(INTENT_STACK_CURRENT_CALLBACK_THRESHOLD, "[option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        descriptions.put(INTENT_STACK_VOLTAGE_CALLBACK_PERIOD, "[" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]");
        descriptions.put(INTENT_STACK_VOLTAGE_CALLBACK_THRESHOLD, "[option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        descriptions.put(INTENT_USB_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

        descriptions.put(EVENT_RESET, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(EVENT_STACK_VOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        descriptions.put(EVENT_STACK_VOLTAGE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        descriptions.put(EVENT_USB_VOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        descriptions.put(EVENT_USB_VOLTAGE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        descriptions.put(EVENT_STACK_CURRENT, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");
        descriptions.put(EVENT_STACK_CURRENT_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");

        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_STACK_CURRENT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_STATUS_LED_ENABLED, "[true|false]");
        descriptions.put(STATUS_CURRENT_CALLBACK_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        descriptions.put(STATUS_STACK_VOLTAGE_CALLBACK_PERIOD, "[" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_STACK_VOLTAGE_CALLBACK_THRESHOLD, "[option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");

        descriptions.put(STATUS_USB_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
    }
}
