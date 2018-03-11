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
package ch.quantasy.gateway.service.tinkerforge.analogInV2;

import ch.quantasy.gateway.message.analogInV2.AnalogValueEvent;
import ch.quantasy.gateway.message.analogInV2.VoltageEvent;
import ch.quantasy.gateway.message.analogInV2.AnalogInV2Intent;
import ch.quantasy.gateway.message.analogInV2.AnalogValueCallbackPeriodStatus;
import ch.quantasy.gateway.message.analogInV2.DebouncePeriodStatus;
import ch.quantasy.gateway.message.analogInV2.AnalogValueCallbackThresholdStatus;
import ch.quantasy.gateway.message.analogInV2.VoltageCallbackPeriodStatus;
import ch.quantasy.gateway.message.analogInV2.VoltageCallbackThresholdStatus;
import ch.quantasy.gateway.message.loadCell.MovingAverageStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.analogInV2.AnalogInV2Device;
import java.util.Map;

/**
 *
 * @author reto
 */
public class AnalogInV2ServiceContract extends DeviceServiceContract {

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

    public final String VOLTAGE;
    public final String STATUS_VOLTAGE;
    public final String STATUS_VOLTAGE_THRESHOLD;
    public final String STATUS_VOLTAGE_CALLBACK_PERIOD;
    public final String EVENT_VOLTAGE;
    public final String EVENT_VOLTAGE_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;

    public AnalogInV2ServiceContract(AnalogInV2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public AnalogInV2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.AnalogInV2.toString());
    }

    public AnalogInV2ServiceContract(String id, String device) {
        super(id, device, AnalogInV2Intent.class);

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

        VOLTAGE = "voltage";
        STATUS_VOLTAGE = STATUS + "/" + VOLTAGE;
        STATUS_VOLTAGE_THRESHOLD = STATUS_VOLTAGE + "/" + THRESHOLD;
        STATUS_VOLTAGE_CALLBACK_PERIOD = STATUS_VOLTAGE + "/" + CALLBACK_PERIOD;
        EVENT_VOLTAGE = EVENT + "/" + VOLTAGE;
        EVENT_VOLTAGE_REACHED = EVENT_VOLTAGE + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;

        MOVING_AVERAGE = "movingAverage";
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_ANALOG_VALUE, AnalogValueEvent.class);
        messageTopicMap.put(EVENT_VOLTAGE, VoltageEvent.class);
        messageTopicMap.put(EVENT_ANALOG_VALUE_REACHED, AnalogValueEvent.class);
        messageTopicMap.put(EVENT_VOLTAGE_REACHED, VoltageEvent.class);
        messageTopicMap.put(STATUS_ANALOG_VALUE_CALLBACK_PERIOD, AnalogValueCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_VOLTAGE_CALLBACK_PERIOD, VoltageCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ANALOG_VALUE_THRESHOLD, AnalogValueCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_VOLTAGE_THRESHOLD, VoltageCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
        messageTopicMap.put(STATUS_MOVING_AVERAGE, MovingAverageStatus.class);
    }

}
