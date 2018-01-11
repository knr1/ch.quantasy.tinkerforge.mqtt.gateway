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
package ch.quantasy.gateway.service.tinkerforge.moisture;

import ch.quantasy.gateway.message.moisture.MoistureEvent;
import ch.quantasy.gateway.message.moisture.MoistureIntent;
import ch.quantasy.gateway.message.moisture.DebouncePeriodStatus;
import ch.quantasy.gateway.message.moisture.MoistureCallbackPeriodStatus;
import ch.quantasy.gateway.message.moisture.MoistureCallbackThresholdStatus;
import ch.quantasy.gateway.message.moisture.MovingAverageStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
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

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;

    public MoistureServiceContract(MoistureDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public MoistureServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Moisture.toString());
    }

    public MoistureServiceContract(String id, String device) {
        super(id, device, MoistureIntent.class);
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

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;

        MOVING_AVERAGE = "movingAverage";
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;

        addMessageTopic(EVENT_MOISTURE, MoistureEvent.class);
        addMessageTopic(EVENT_MOISTURE_REACHED, MoistureEvent.class);
        addMessageTopic(STATUS_MOISTURE_CALLBACK_PERIOD, MoistureCallbackPeriodStatus.class);
        addMessageTopic(STATUS_MOISTURE_THRESHOLD, MoistureCallbackThresholdStatus.class);
        addMessageTopic(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
        addMessageTopic(STATUS_MOVING_AVERAGE, MovingAverageStatus.class);
    }

    
}
