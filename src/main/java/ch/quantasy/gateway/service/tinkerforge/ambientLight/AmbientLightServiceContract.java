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
package ch.quantasy.gateway.service.tinkerforge.ambientLight;

import ch.quantasy.gateway.message.ambientLight.AnalogValueEvent;
import ch.quantasy.gateway.message.ambientLight.IlluminanceEvent;
import ch.quantasy.gateway.message.ambientLight.AmbientLightIntent;
import ch.quantasy.gateway.message.ambientLight.AnalogCallbackPeriodStatus;
import ch.quantasy.gateway.message.ambientLight.AnalogValueThresholdStatus;
import ch.quantasy.gateway.message.ambientLight.DebouncePeriodStatus;
import ch.quantasy.gateway.message.ambientLight.IlluminanceCallbackPeriodStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Intent;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.ambientLight.AmbientLightDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class AmbientLightServiceContract extends DeviceServiceContract {

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

    public final String ILLUMINANCE;
    public final String STATUS_ILLUMINANCE;
    public final String STATUS_ILLUMINANCE_THRESHOLD;
    public final String STATUS_ILLUMINANCE_CALLBACK_PERIOD;
    public final String EVENT_ILLUMINANCE;
    public final String EVENT_ILLUMINANCE_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public AmbientLightServiceContract(AmbientLightDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public AmbientLightServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.AmbientLight.toString());
    }

    public AmbientLightServiceContract(String id, String device) {
        this(id, device, AmbientLightIntent.class);
    }

    public AmbientLightServiceContract(String instance, String baseClass, Class<? extends Intent> intentClass) {
        super(instance, baseClass, intentClass);
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

        ILLUMINANCE = "illuminance";
        STATUS_ILLUMINANCE = STATUS + "/" + ILLUMINANCE;
        STATUS_ILLUMINANCE_THRESHOLD = STATUS_ILLUMINANCE + "/" + THRESHOLD;
        STATUS_ILLUMINANCE_CALLBACK_PERIOD = STATUS_ILLUMINANCE + "/" + CALLBACK_PERIOD;
        EVENT_ILLUMINANCE = EVENT + "/" + ILLUMINANCE;
        EVENT_ILLUMINANCE_REACHED = EVENT_ILLUMINANCE + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;

        addMessageTopic(EVENT_ANALOG_VALUE, AnalogValueEvent.class);
        addMessageTopic(EVENT_ILLUMINANCE, IlluminanceEvent.class);
        addMessageTopic(EVENT_ANALOG_VALUE_REACHED, AnalogValueEvent.class);
        addMessageTopic(EVENT_ILLUMINANCE_REACHED, IlluminanceEvent.class);
        addMessageTopic(STATUS_ANALOG_VALUE_CALLBACK_PERIOD, AnalogCallbackPeriodStatus.class);
        addMessageTopic(STATUS_ILLUMINANCE_CALLBACK_PERIOD, IlluminanceCallbackPeriodStatus.class);
        addMessageTopic(STATUS_ANALOG_VALUE_THRESHOLD, AnalogValueThresholdStatus.class);
        addMessageTopic(STATUS_ILLUMINANCE_THRESHOLD, IlluminanceCallbackPeriodStatus.class);
        addMessageTopic(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
    }

}
