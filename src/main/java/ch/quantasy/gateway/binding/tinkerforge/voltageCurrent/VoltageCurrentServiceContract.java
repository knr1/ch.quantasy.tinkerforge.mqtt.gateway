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
package ch.quantasy.gateway.binding.tinkerforge.voltageCurrent;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.CurrentEvent;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.PowerEvent;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.VoltageEvent;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.VoltageCurrentIntent;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.CalibrationStatus;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.ConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.CurrentCalbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.CurrentCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.DebouncePeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.PowerCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.PowerCallbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.VoltageCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.voltageCurrent.VoltageCallbackThresholdStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.voltageCurrent.VoltageCurrentDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class VoltageCurrentServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;
    public final String TARE;
    public final String VOLTAGE;
    public final String STATUS_VOLTAGE;
    public final String STATUS_VOLTAGE_THRESHOLD;
    public final String STATUS_VOLTAGE_CALLBACK_PERIOD;
    public final String EVENT_VOLTAGE;
    public final String EVENT_VOLTAGE_REACHED;
    public final String CURRENT;
    public final String STATUS_CURRENT;
    public final String STATUS_CURRENT_THRESHOLD;
    public final String STATUS_CURRENT_CALLBACK_PERIOD;
    public final String EVENT_CURRENT;
    public final String EVENT_CURRENT_REACHED;

    public final String POWER;
    public final String STATUS_POWER;
    public final String STATUS_POWER_THRESHOLD;
    public final String STATUS_POWER_CALLBACK_PERIOD;
    public final String EVENT_POWER;
    public final String EVENT_POWER_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String STATUS_CONFIGURATION;

    public final String CALIBRATION;
    public final String STATUS_CALIBRATION;

    public VoltageCurrentServiceContract(VoltageCurrentDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public VoltageCurrentServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.VoltageCurrent.toString());
    }

    public VoltageCurrentServiceContract(String id, String device) {
        super(id, device, VoltageCurrentIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        VOLTAGE = "voltage";
        STATUS_VOLTAGE = STATUS + "/" + VOLTAGE;
        STATUS_VOLTAGE_THRESHOLD = STATUS_VOLTAGE + "/" + THRESHOLD;
        STATUS_VOLTAGE_CALLBACK_PERIOD = STATUS_VOLTAGE + "/" + CALLBACK_PERIOD;
        EVENT_VOLTAGE = EVENT + "/" + VOLTAGE;
        EVENT_VOLTAGE_REACHED = EVENT_VOLTAGE + "/" + REACHED;

        CURRENT = "current";
        STATUS_CURRENT = STATUS + "/" + CURRENT;
        STATUS_CURRENT_THRESHOLD = STATUS_CURRENT + "/" + THRESHOLD;
        STATUS_CURRENT_CALLBACK_PERIOD = STATUS_CURRENT + "/" + CALLBACK_PERIOD;
        EVENT_CURRENT = EVENT + "/" + CURRENT;
        EVENT_CURRENT_REACHED = EVENT_CURRENT + "/" + REACHED;

        POWER = "power";
        STATUS_POWER = STATUS + "/" + POWER;
        STATUS_POWER_THRESHOLD = STATUS_POWER + "/" + THRESHOLD;
        STATUS_POWER_CALLBACK_PERIOD = STATUS_POWER + "/" + CALLBACK_PERIOD;
        EVENT_POWER = EVENT + "/" + POWER;
        EVENT_POWER_REACHED = EVENT_POWER + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;

        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;

        TARE = "tare";

        CALIBRATION = "calibration";
        STATUS_CALIBRATION = STATUS + "/" + CALIBRATION;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(EVENT_POWER, PowerEvent.class);
        messageTopicMap.put(EVENT_POWER_REACHED, PowerEvent.class);
        messageTopicMap.put(EVENT_VOLTAGE, VoltageEvent.class);
        messageTopicMap.put(EVENT_VOLTAGE_REACHED, VoltageEvent.class);
        messageTopicMap.put(EVENT_CURRENT, CurrentEvent.class);
        messageTopicMap.put(EVENT_CURRENT_REACHED, CurrentEvent.class);
        messageTopicMap.put(STATUS_CALIBRATION, CalibrationStatus.class);
        messageTopicMap.put(STATUS_VOLTAGE_CALLBACK_PERIOD, VoltageCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_VOLTAGE_THRESHOLD, VoltageCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_CURRENT_CALLBACK_PERIOD, CurrentCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_CURRENT_THRESHOLD, CurrentCalbackThresholdStatus.class);
        messageTopicMap.put(STATUS_POWER_CALLBACK_PERIOD, PowerCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_POWER_THRESHOLD, PowerCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
        messageTopicMap.put(STATUS_CONFIGURATION, ConfigurationStatus.class);

    }

}
