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
package ch.quantasy.gateway.service.device.voltageCurrent;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.voltageCurrent.VoltageCurrentDevice;

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
    public final String INTENT_TARE;

    public final String VOLTAGE;
    public final String STATUS_VOLTAGE;
    public final String STATUS_VOLTAGE_THRESHOLD;
    public final String STATUS_VOLTAGE_CALLBACK_PERIOD;
    public final String EVENT_VOLTAGE;
    public final String EVENT_VOLTAGE_REACHED;
    private final String INTENT_VOLTAGE;
    public final String INTENT_VOLTAGE_THRESHOLD;
    public final String INTENT_VOLTAGE_CALLBACK_PERIOD;
     public final String CURRENT;
    public final String STATUS_CURRENT;
    public final String STATUS_CURRENT_THRESHOLD;
    public final String STATUS_CURRENT_CALLBACK_PERIOD;
    public final String EVENT_CURRENT;
    public final String EVENT_CURRENT_REACHED;
    private final String INTENT_CURRENT;
    public final String INTENT_CURRENT_THRESHOLD;
    public final String INTENT_CURRENT_CALLBACK_PERIOD;
    public final String POWER;
    public final String STATUS_POWER;
    public final String STATUS_POWER_THRESHOLD;
    public final String STATUS_POWER_CALLBACK_PERIOD;
    public final String EVENT_POWER;
    public final String EVENT_POWER_REACHED;
    private final String INTENT_POWER;
    public final String INTENT_POWER_THRESHOLD;
    public final String INTENT_POWER_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    private final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String STATUS_CONFIGURATION;
    public final String INTENT_CONFIGURATION;

    public final String CALIBRATION;
    public final String STATUS_CALIBRATION;
    public final String INTENT_CALIBRATION;

  

    public VoltageCurrentServiceContract(VoltageCurrentDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public VoltageCurrentServiceContract(String id, String device) {
        super(id, device);

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
        INTENT_VOLTAGE = INTENT + "/" + VOLTAGE;
        INTENT_VOLTAGE_THRESHOLD = INTENT_VOLTAGE + "/" + THRESHOLD;
        INTENT_VOLTAGE_CALLBACK_PERIOD = INTENT_VOLTAGE + "/" + CALLBACK_PERIOD;

        CURRENT = "current";
        STATUS_CURRENT = STATUS + "/" + CURRENT;
        STATUS_CURRENT_THRESHOLD = STATUS_CURRENT + "/" + THRESHOLD;
        STATUS_CURRENT_CALLBACK_PERIOD = STATUS_CURRENT + "/" + CALLBACK_PERIOD;
        EVENT_CURRENT = EVENT + "/" + CURRENT;
        EVENT_CURRENT_REACHED = EVENT_CURRENT + "/" + REACHED;
        INTENT_CURRENT = INTENT + "/" + CURRENT;
        INTENT_CURRENT_THRESHOLD = INTENT_CURRENT + "/" + THRESHOLD;
        INTENT_CURRENT_CALLBACK_PERIOD = INTENT_CURRENT + "/" + CALLBACK_PERIOD;
        
        POWER = "power";
        STATUS_POWER = STATUS + "/" + POWER;
        STATUS_POWER_THRESHOLD = STATUS_POWER + "/" + THRESHOLD;
        STATUS_POWER_CALLBACK_PERIOD = STATUS_POWER + "/" + CALLBACK_PERIOD;
        EVENT_POWER = EVENT + "/" + POWER;
        EVENT_POWER_REACHED = EVENT_POWER + "/" + REACHED;
        INTENT_POWER = INTENT + "/" + POWER;
        INTENT_POWER_THRESHOLD = INTENT_POWER + "/" + THRESHOLD;
        INTENT_POWER_CALLBACK_PERIOD = INTENT_POWER + "/" + CALLBACK_PERIOD;

        
        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;
        INTENT_CONFIGURATION = INTENT + "/" + CONFIGURATION;

        TARE = "tare";
        INTENT_TARE = INTENT + "/" + TARE;

        CALIBRATION = "calibration";
        STATUS_CALIBRATION = STATUS + "/" + CALIBRATION;
        INTENT_CALIBRATION = INTENT + "/" + CALIBRATION;

        

    }
}
