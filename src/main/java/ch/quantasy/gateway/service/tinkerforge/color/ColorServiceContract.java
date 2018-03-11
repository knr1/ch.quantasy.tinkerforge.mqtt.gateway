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
package ch.quantasy.gateway.service.tinkerforge.color;

import ch.quantasy.gateway.message.color.ColorEvent;
import ch.quantasy.gateway.message.color.ColorTemperatureEvent;
import ch.quantasy.gateway.message.color.IlluminanceEvent;
import ch.quantasy.gateway.message.color.ColorIntent;
import ch.quantasy.gateway.message.color.ColorCallbackThresholdStatus;
import ch.quantasy.gateway.message.color.ColorTemperatureCallbackPeriodStatus;
import ch.quantasy.gateway.message.color.DebouncePeriodStatus;
import ch.quantasy.gateway.message.color.IlluminanceCallbackPeriodStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.color.ColorDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class ColorServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String COLOR;
    public final String STATUS_COLOR;
    public final String STATUS_COLOR_THRESHOLD;
    public final String STATUS_COLOR_TEMPERATURE_CALLBACK_PERIOD;
    public final String EVENT_COLOR;
    public final String EVENT_COLOR_REACHED;

    public final String ILLUMINANCE;
    public final String STATUS_ILLUMINANCE;
    public final String STATUS_ILLUMINANCE_CALLBACK_PERIOD;
    public final String EVENT_ILLUMINANCE;
    public final String EVENT_COLOR_TEMPERATURE_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String STATUS_CONFIGURATION;

    public final String LED_STATE;
    public final String STATUS_LIGHT_STATE;

    public ColorServiceContract(ColorDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public ColorServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Color.toString());
    }

    public ColorServiceContract(String id, String device) {
        super(id, device, ColorIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        COLOR = "color";
        STATUS_COLOR = STATUS + "/" + COLOR;
        STATUS_COLOR_THRESHOLD = STATUS_COLOR + "/" + THRESHOLD;
        STATUS_COLOR_TEMPERATURE_CALLBACK_PERIOD = STATUS_COLOR + "/" + CALLBACK_PERIOD;
        EVENT_COLOR = EVENT + "/" + COLOR;
        EVENT_COLOR_REACHED = EVENT_COLOR + "/" + REACHED;

        ILLUMINANCE = "illuminance";
        STATUS_ILLUMINANCE = STATUS + "/" + ILLUMINANCE;
        STATUS_ILLUMINANCE_CALLBACK_PERIOD = STATUS_ILLUMINANCE + "/" + CALLBACK_PERIOD;
        EVENT_ILLUMINANCE = EVENT + "/" + ILLUMINANCE;
        EVENT_COLOR_TEMPERATURE_REACHED = EVENT_ILLUMINANCE + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;

        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;

        LED_STATE = "ledState";
        STATUS_LIGHT_STATE = STATUS + "/" + LED_STATE;

    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_COLOR, ColorEvent.class);
        messageTopicMap.put(EVENT_ILLUMINANCE, IlluminanceEvent.class);
        messageTopicMap.put(EVENT_COLOR_REACHED, ColorEvent.class);
        messageTopicMap.put(EVENT_COLOR_TEMPERATURE_REACHED, ColorTemperatureEvent.class);
        messageTopicMap.put(STATUS_COLOR_TEMPERATURE_CALLBACK_PERIOD, ColorTemperatureCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_ILLUMINANCE_CALLBACK_PERIOD, IlluminanceCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_COLOR_THRESHOLD, ColorCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
    }

}
