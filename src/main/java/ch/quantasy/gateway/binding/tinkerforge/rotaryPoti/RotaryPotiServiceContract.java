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
package ch.quantasy.gateway.binding.tinkerforge.rotaryPoti;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.AnalogValueEvent;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.PositionEvent;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.RotaryPotiIntent;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.AnalogValueCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.AnalogValueCallbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.DebouncePeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.PositionCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.rotaryPoti.PositionCallbackThresholdStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.rotaryPoti.RotaryPotiDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class RotaryPotiServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String ANALOG_VALUE;
    public final String STATUS_ANALOG_VALUE;
    public final String STATUS_ANALOG_VALUE_THRESHOLD;
    public final String STATUS_ANALOG_VALUE_CALLBACK_PERIOD;
    public final String EVENT_ANALOG_VALUE;
    public final String EVENT_ANALOG_VALUE_REACHED;

    public final String POSITION;
    public final String STATUS_POSITION;
    public final String STATUS_POSITION_THRESHOLD;
    public final String STATUS_POSITION_CALLBACK_PERIOD;
    public final String EVENT_POSITION;
    public final String EVENT_POSITION_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public RotaryPotiServiceContract(RotaryPotiDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public RotaryPotiServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.RotaryPoti.toString());
    }

    public RotaryPotiServiceContract(String id, String device) {
        super(id, device, RotaryPotiIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        ANALOG_VALUE = "analogValue";
        STATUS_ANALOG_VALUE = STATUS + "/" + ANALOG_VALUE;
        STATUS_ANALOG_VALUE_THRESHOLD = STATUS_ANALOG_VALUE + "/" + THRESHOLD;
        STATUS_ANALOG_VALUE_CALLBACK_PERIOD = STATUS_ANALOG_VALUE + "/" + CALLBACK_PERIOD;
        EVENT_ANALOG_VALUE = EVENT + "/" + ANALOG_VALUE;
        EVENT_ANALOG_VALUE_REACHED = EVENT_ANALOG_VALUE + "/" + REACHED;

        POSITION = "position";
        STATUS_POSITION = STATUS + "/" + POSITION;
        STATUS_POSITION_THRESHOLD = STATUS_POSITION + "/" + THRESHOLD;
        STATUS_POSITION_CALLBACK_PERIOD = STATUS_POSITION + "/" + CALLBACK_PERIOD;
        EVENT_POSITION = EVENT + "/" + POSITION;
        EVENT_POSITION_REACHED = EVENT_POSITION + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(EVENT_ANALOG_VALUE, AnalogValueEvent.class);
        messageTopicMap.put(EVENT_POSITION, PositionEvent.class);
        messageTopicMap.put(EVENT_ANALOG_VALUE_REACHED, AnalogValueEvent.class);
        messageTopicMap.put(EVENT_POSITION_REACHED, PositionEvent.class);
        messageTopicMap.put(STATUS_ANALOG_VALUE_CALLBACK_PERIOD, AnalogValueCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_POSITION_CALLBACK_PERIOD, PositionCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ANALOG_VALUE_THRESHOLD, AnalogValueCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_POSITION_THRESHOLD, PositionCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
    }

    
}
