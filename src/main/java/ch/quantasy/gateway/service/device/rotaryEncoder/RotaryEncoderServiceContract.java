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

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDevice;

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
    private final String INTENT_COUNT;
    public final String INTENT_COUNT_THRESHOLD;
    public final String INTENT_COUNT_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    private final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;

     public final String RESET;
     public final String   INTENT_COUNT_RESET;
      public final String  EVENT_COUNT_RESET;

    public RotaryEncoderServiceContract(RotaryEncoderDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public RotaryEncoderServiceContract(String id, String device) {
        super(id, device);

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
        INTENT_COUNT = INTENT + "/" + COUNT;
        INTENT_COUNT_THRESHOLD = INTENT_COUNT + "/" + THRESHOLD;
        INTENT_COUNT_CALLBACK_PERIOD = INTENT_COUNT + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;

        RESET = "reset";
        INTENT_COUNT_RESET = INTENT_COUNT + "/" + RESET;
        EVENT_COUNT_RESET = EVENT_COUNT + "/" + RESET;
    }
}
