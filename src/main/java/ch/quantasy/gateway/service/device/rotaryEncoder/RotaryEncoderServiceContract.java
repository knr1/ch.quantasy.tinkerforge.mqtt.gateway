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
package ch.quantasy.gateway.service.device.rotaryEncoder;

import ch.quantasy.gateway.intent.rotaryEncoder.RotaryEncoderIntent;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class RotaryEncoderServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String PRESSED;
    public final String EVENT_PRESSED;

    public final String RELEASED;
    public final String EVENT_RELEASED;

    public final String COUNT;
    public final String STATUS_COUNT;
    public final String STATUS_COUNT_THRESHOLD;
    public final String STATUS_COUNT_CALLBACK_PERIOD;
    public final String EVENT_COUNT;
    public final String EVENT_COUNT_REACHED;
   
    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
   public final String STATUS_DEBOUNCE_PERIOD;

    public final String RESET;
    public final String EVENT_COUNT_RESET;

    public RotaryEncoderServiceContract(RotaryEncoderDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public RotaryEncoderServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.RotaryEncoder.toString());
    }

    public RotaryEncoderServiceContract(String id, String device) {
        super(id, device,RotaryEncoderIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";
        PRESSED = "pressed";
        EVENT_PRESSED = EVENT + "/" + PRESSED;
        RELEASED = "released";
        EVENT_RELEASED = EVENT + "/" + RELEASED;

        COUNT = "count";
        STATUS_COUNT = STATUS + "/" + COUNT;
        STATUS_COUNT_THRESHOLD = STATUS_COUNT + "/" + THRESHOLD;
        STATUS_COUNT_CALLBACK_PERIOD = STATUS_COUNT + "/" + CALLBACK_PERIOD;
        EVENT_COUNT = EVENT + "/" + COUNT;
        EVENT_COUNT_REACHED = EVENT_COUNT + "/" + REACHED;
     
        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
      
        RESET = "reset";
        EVENT_COUNT_RESET = EVENT_COUNT + "/" + RESET;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(EVENT_PRESSED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: true");
        descriptions.put(EVENT_RELEASED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: true");
        descriptions.put(EVENT_COUNT_RESET, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [" + Long.MIN_VALUE + "0.." + Long.MAX_VALUE + "]");

        descriptions.put(EVENT_COUNT, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0..9000]");
        descriptions.put(EVENT_COUNT_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [-150..150]");
        descriptions.put(STATUS_COUNT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_COUNT_THRESHOLD, "option: [x|o|i|<|>]\n min: [-150..150]\n max: [-150..150]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
    }
}
