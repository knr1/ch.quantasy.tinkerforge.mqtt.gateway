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
package ch.quantasy.gateway.service.tinkerforge.thermoCouple;

import ch.quantasy.gateway.message.thermoCouple.TemperatureEvent;
import ch.quantasy.gateway.message.thermoCouple.ErrorEvent;
import ch.quantasy.gateway.message.thermoCouple.ThermoCoupleIntent;
import ch.quantasy.gateway.message.thermoCouple.ConfigurationStatus;
import ch.quantasy.gateway.message.thermoCouple.DebouncePeriodStatus;
import ch.quantasy.gateway.message.thermoCouple.TemperatureCallbackPeriodStatus;
import ch.quantasy.gateway.message.thermoCouple.TemperatureCallbackThresholdStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.thermoCouple.ThermoCoupleDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class ThermoCoupleServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String TEMPERATURE;
    public final String STATUS_TEMPERATURE;
    public final String STATUS_TEMPERATURE_THRESHOLD;
    public final String STATUS_TEMPERATURE_CALLBACK_PERIOD;
    public final String EVENT_TEMPERATURE;
    public final String EVENT_TEMPERATURE_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String STATUS_CONFIGURATION;

    public final String ERROR;
    public final String EVENT_ERROR;

    public ThermoCoupleServiceContract(ThermoCoupleDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public ThermoCoupleServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.ThermoCouple.toString());
    }

    public ThermoCoupleServiceContract(String id, String device) {
        super(id, device, ThermoCoupleIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        TEMPERATURE = "temperature";
        STATUS_TEMPERATURE = STATUS + "/" + TEMPERATURE;
        STATUS_TEMPERATURE_THRESHOLD = STATUS_TEMPERATURE + "/" + THRESHOLD;
        STATUS_TEMPERATURE_CALLBACK_PERIOD = STATUS_TEMPERATURE + "/" + CALLBACK_PERIOD;
        EVENT_TEMPERATURE = EVENT + "/" + TEMPERATURE;
        EVENT_TEMPERATURE_REACHED = EVENT_TEMPERATURE + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;

        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;

        ERROR = "error";
        EVENT_ERROR = EVENT + "/" + ERROR;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(EVENT_TEMPERATURE, TemperatureEvent.class);
        messageTopicMap.put(EVENT_TEMPERATURE_REACHED, TemperatureEvent.class);
        messageTopicMap.put(EVENT_ERROR, ErrorEvent.class);
        messageTopicMap.put(STATUS_TEMPERATURE_CALLBACK_PERIOD, TemperatureCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_TEMPERATURE_THRESHOLD, TemperatureCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
        messageTopicMap.put(STATUS_CONFIGURATION, ConfigurationStatus.class);
    }

   
}
