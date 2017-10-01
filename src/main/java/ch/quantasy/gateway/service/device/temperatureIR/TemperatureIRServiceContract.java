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
package ch.quantasy.gateway.service.device.temperatureIR;

import ch.quantasy.gateway.intent.temperatureIR.TemperatureIRIntent;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class TemperatureIRServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String AMBIENT_TEMPERATURE;
    public final String STATUS_ANALOG_VALUE;
    public final String STATUS_AMBIENT_TEMPERATURE_THRESHOLD;
    public final String STATUS_AMBIENT_TEMPERATURE_CALLBACK_PERIOD;
    public final String EVENT_AMBIENT_TEMPERATURE;
    public final String EVENT_AMBIENT_TEMPERATURE_REACHED;
   
    public final String OBJECT_TEMPERATURE;
    public final String STATUS_OBJECT_TEMPERATURE;
    public final String STATUS_OBJECT_TEMPERATURE_THRESHOLD;
    public final String STATUS_OBJECT_TEMPERATURE_CALLBACK_PERIOD;
    public final String EVENT_OBJECT_TEMPERATURE;
    public final String EVENT_OBJECT_TEMPERATURE_REACHED;
   
    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public TemperatureIRServiceContract(TemperatureIRDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public TemperatureIRServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.TemperatureIR.toString());
    }

    public TemperatureIRServiceContract(String id, String device) {
        super(id, device,TemperatureIRIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        AMBIENT_TEMPERATURE = "ambientTemperature";
        STATUS_ANALOG_VALUE = STATUS + "/" + AMBIENT_TEMPERATURE;
        STATUS_AMBIENT_TEMPERATURE_THRESHOLD = STATUS_ANALOG_VALUE + "/" + THRESHOLD;
        STATUS_AMBIENT_TEMPERATURE_CALLBACK_PERIOD = STATUS_ANALOG_VALUE + "/" + CALLBACK_PERIOD;
        EVENT_AMBIENT_TEMPERATURE = EVENT + "/" + AMBIENT_TEMPERATURE;
        EVENT_AMBIENT_TEMPERATURE_REACHED = EVENT_AMBIENT_TEMPERATURE + "/" + REACHED;
   
        OBJECT_TEMPERATURE = "objectTemperature";
        STATUS_OBJECT_TEMPERATURE = STATUS + "/" + OBJECT_TEMPERATURE;
        STATUS_OBJECT_TEMPERATURE_THRESHOLD = STATUS_OBJECT_TEMPERATURE + "/" + THRESHOLD;
        STATUS_OBJECT_TEMPERATURE_CALLBACK_PERIOD = STATUS_OBJECT_TEMPERATURE + "/" + CALLBACK_PERIOD;
        EVENT_OBJECT_TEMPERATURE = EVENT + "/" + OBJECT_TEMPERATURE;
        EVENT_OBJECT_TEMPERATURE_REACHED = EVENT_OBJECT_TEMPERATURE + "/" + REACHED;
   
        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(EVENT_AMBIENT_TEMPERATURE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-400..1250]");
        descriptions.put(EVENT_OBJECT_TEMPERATURE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-700..3800]");
        descriptions.put(EVENT_AMBIENT_TEMPERATURE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-400..1250]");
        descriptions.put(EVENT_OBJECT_TEMPERATURE_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-700..3800]");
        descriptions.put(STATUS_AMBIENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_OBJECT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_AMBIENT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-400..1250]\n max: [-400..1250]");
        descriptions.put(STATUS_OBJECT_TEMPERATURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [-700..3800]\n max: [-700..3800]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
    }
}
