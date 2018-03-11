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
package ch.quantasy.gateway.service.tinkerforge.line;

import ch.quantasy.gateway.message.line.ReflectivityEvent;
import ch.quantasy.gateway.message.line.LineIntent;
import ch.quantasy.gateway.message.line.DebouncePeriodStatus;
import ch.quantasy.gateway.message.line.ReflectivityCallbackPeriodStatus;
import ch.quantasy.gateway.message.line.ReflectivityCallbackThresholdStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
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

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public LineServiceContract(LineDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public LineServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Line.toString());
    }

    public LineServiceContract(String id, String device) {
        super(id, device, LineIntent.class);

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

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(EVENT_REFLECTIVITY, ReflectivityEvent.class);
        messageTopicMap.put(EVENT_REFLECTIVITY_REACHED, ReflectivityEvent.class);
        messageTopicMap.put(STATUS_REFLECTIVITY_CALLBACK_PERIOD, ReflectivityCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_REFLECTIVITY_THRESHOLD, ReflectivityCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
    }

    
}
