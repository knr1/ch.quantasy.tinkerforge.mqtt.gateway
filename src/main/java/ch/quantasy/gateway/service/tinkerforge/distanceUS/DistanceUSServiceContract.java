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
package ch.quantasy.gateway.service.tinkerforge.distanceUS;

import ch.quantasy.gateway.message.distanceUS.DistanceEvent;
import ch.quantasy.gateway.message.distanceUS.DistanceUSIntent;
import ch.quantasy.gateway.message.distanceUS.DebouncePeriodStatus;
import ch.quantasy.gateway.message.distanceUS.DistanceCallbackPeriodStatus;
import ch.quantasy.gateway.message.distanceUS.DistanceCallbackThresholdStatus;
import ch.quantasy.gateway.message.distanceUS.MovingAverageStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.distanceUS.DistanceUSDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class DistanceUSServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String DISTANCE;
    public final String STATUS_DISTANCE;
    public final String STATUS_DISTANCE_THRESHOLD;
    public final String STATUS_DISTANCE_CALLBACK_PERIOD;
    public final String EVENT_DISTANCE;
    public final String EVENT_DISTANCE_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;

    public DistanceUSServiceContract(DistanceUSDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public DistanceUSServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.DistanceUS.toString());
    }

    public DistanceUSServiceContract(String id, String device) {
        super(id, device, DistanceUSIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        DISTANCE = "distance";
        STATUS_DISTANCE = STATUS + "/" + DISTANCE;
        STATUS_DISTANCE_THRESHOLD = STATUS_DISTANCE + "/" + THRESHOLD;
        STATUS_DISTANCE_CALLBACK_PERIOD = STATUS_DISTANCE + "/" + CALLBACK_PERIOD;
        EVENT_DISTANCE = EVENT + "/" + DISTANCE;
        EVENT_DISTANCE_REACHED = EVENT_DISTANCE + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;

        MOVING_AVERAGE = "movingAverage";
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_DISTANCE, DistanceEvent.class);
        messageTopicMap.put(EVENT_DISTANCE_REACHED, DistanceEvent.class);
        messageTopicMap.put(STATUS_DISTANCE_CALLBACK_PERIOD, DistanceCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_DISTANCE_THRESHOLD, DistanceCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
        messageTopicMap.put(STATUS_MOVING_AVERAGE, MovingAverageStatus.class);
    }

   
}
