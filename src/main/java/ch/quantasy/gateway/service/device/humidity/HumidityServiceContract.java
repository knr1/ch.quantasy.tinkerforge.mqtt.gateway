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
package ch.quantasy.gateway.service.device.humidity;

import ch.quantasy.gateway.message.humidity.AnalogValueEvent;
import ch.quantasy.gateway.message.humidity.HumidityEvent;
import ch.quantasy.gateway.message.humidity.HumidityIntent;
import ch.quantasy.gateway.message.humidity.AnalogCallbackPeriodStatus;
import ch.quantasy.gateway.message.humidity.AnalogValueCallbackThresholdStatus;
import ch.quantasy.gateway.message.humidity.DebouncePeriodStatus;
import ch.quantasy.gateway.message.humidity.HumidityCallbackPeriodStatus;
import ch.quantasy.gateway.message.humidity.HumidityCallbackThresholdStatus;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class HumidityServiceContract extends DeviceServiceContract {

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

    public final String HUMIDITY;
    public final String STATUS_HUMIDITY;
    public final String STATUS_HUMIDITY_THRESHOLD;
    public final String STATUS_HUMIDITY_CALLBACK_PERIOD;
    public final String EVENT_HUMIDITY;
    public final String EVENT_HUMIDITY_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public HumidityServiceContract(HumidityDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public HumidityServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Humidity.toString());
    }

    public HumidityServiceContract(String id, String device) {
        super(id, device, HumidityIntent.class);
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

        HUMIDITY = "humidity";
        STATUS_HUMIDITY = STATUS + "/" + HUMIDITY;
        STATUS_HUMIDITY_THRESHOLD = STATUS_HUMIDITY + "/" + THRESHOLD;
        STATUS_HUMIDITY_CALLBACK_PERIOD = STATUS_HUMIDITY + "/" + CALLBACK_PERIOD;
        EVENT_HUMIDITY = EVENT + "/" + HUMIDITY;
        EVENT_HUMIDITY_REACHED = EVENT_HUMIDITY + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        addMessageTopic(EVENT_ANALOG_VALUE, AnalogValueEvent.class);
        addMessageTopic(EVENT_HUMIDITY, HumidityEvent.class);
        addMessageTopic(EVENT_ANALOG_VALUE_REACHED, AnalogValueEvent.class);
        addMessageTopic(EVENT_HUMIDITY_REACHED, HumidityEvent.class);
         addMessageTopic(STATUS_ANALOG_VALUE_CALLBACK_PERIOD, AnalogCallbackPeriodStatus.class);
        addMessageTopic(STATUS_HUMIDITY_CALLBACK_PERIOD, HumidityCallbackPeriodStatus.class);
        addMessageTopic(STATUS_ANALOG_VALUE_THRESHOLD, AnalogValueCallbackThresholdStatus.class);
        addMessageTopic(STATUS_HUMIDITY_THRESHOLD, HumidityCallbackThresholdStatus.class);
        addMessageTopic(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);

    }

    
}
