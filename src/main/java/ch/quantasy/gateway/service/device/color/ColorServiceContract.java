/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service.device.color;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.color.ColorDevice;

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
    public final String INTENT_COLOR_VALUE;
    public final String INTENT_COLOR_CALLBACK_THRESHOLD;
    public final String INTENT_COLOR_CALLBACK_PERIOD;

    public final String ILLUMINANCE;
    public final String STATUS_ILLUMINANCE;
    public final String STATUS_ILLUMINANCE_CALLBACK_PERIOD;
    public final String EVENT_ILLUMINANCE;
    public final String EVENT_COLOR_TEMPERATURE_REACHED;
    public final String INTENT_ILLUMINANCE;
    public final String INTENT_COLOR_TEMPERATURE_CALLBACK_PERIOD;
    public final String INTENT_IllUMINANCE_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String INTENT_CONFIGURATION;
    public final String STATUS_CONFIGURATION;

    public final String LED_STATE;
    public final String INTENT_LIGHT_STATE;
    public final String STATUS_LIGHT_STATE;

    public ColorServiceContract(ColorDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public ColorServiceContract(String id, String device) {
        super(id, device);


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
        INTENT_COLOR_VALUE = INTENT + "/" + COLOR;
        INTENT_COLOR_CALLBACK_THRESHOLD = INTENT_COLOR_VALUE + "/" + THRESHOLD;
        INTENT_COLOR_CALLBACK_PERIOD = INTENT_COLOR_VALUE + "/" + CALLBACK_PERIOD;

        ILLUMINANCE = "illuminance";
        STATUS_ILLUMINANCE = STATUS + "/" + ILLUMINANCE;
        STATUS_ILLUMINANCE_CALLBACK_PERIOD = STATUS_ILLUMINANCE + "/" + CALLBACK_PERIOD;
        EVENT_ILLUMINANCE = EVENT + "/" + ILLUMINANCE;
        EVENT_COLOR_TEMPERATURE_REACHED = EVENT_ILLUMINANCE + "/" + REACHED;
        INTENT_ILLUMINANCE = INTENT + "/" + ILLUMINANCE;
        INTENT_COLOR_TEMPERATURE_CALLBACK_PERIOD = INTENT_ILLUMINANCE + "/" + THRESHOLD;
        INTENT_IllUMINANCE_CALLBACK_PERIOD = INTENT_ILLUMINANCE + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;
        INTENT_CONFIGURATION = INTENT + "/" + CONFIGURATION;

        LED_STATE = "ledState";
        STATUS_LIGHT_STATE = STATUS + "/" + LED_STATE;
        INTENT_LIGHT_STATE = INTENT + "/" + LED_STATE;
    }
}
