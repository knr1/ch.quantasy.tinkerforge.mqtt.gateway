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
package ch.quantasy.gateway.service.device.temperature;

import ch.quantasy.gateway.message.temperature.TemperatureEvent;
import ch.quantasy.gateway.message.temperature.TemperatureIntent;
import ch.quantasy.gateway.message.temperature.DebouncePeriodStatus;
import ch.quantasy.gateway.message.temperature.ModeStatus;
import ch.quantasy.gateway.message.temperature.TemperatureCallbackPeriodStatus;
import ch.quantasy.gateway.message.temperature.TemperatureThresholdStatus;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.temperature.TemperatureDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class TemperatureServiceContract extends DeviceServiceContract {

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

    public final String MODE;
    public final String STATUS_I2CMODE;

    public TemperatureServiceContract(TemperatureDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public TemperatureServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Temperature.toString());
    }

    public TemperatureServiceContract(String id, String device) {
        super(id, device, TemperatureIntent.class);

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

        MODE = "mode";
        STATUS_I2CMODE = STATUS + "/" + MODE;
        addMessageTopic(EVENT_TEMPERATURE, TemperatureEvent.class);
        addMessageTopic(EVENT_TEMPERATURE_REACHED, TemperatureEvent.class);
        addMessageTopic(STATUS_TEMPERATURE_CALLBACK_PERIOD, TemperatureCallbackPeriodStatus.class);
        addMessageTopic(STATUS_TEMPERATURE_THRESHOLD, TemperatureThresholdStatus.class);
        addMessageTopic(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
        addMessageTopic(STATUS_I2CMODE, ModeStatus.class);
    }

   
}
