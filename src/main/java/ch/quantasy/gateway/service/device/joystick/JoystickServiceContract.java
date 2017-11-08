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
package ch.quantasy.gateway.service.device.joystick;

import ch.quantasy.gateway.message.event.joystick.ButtonEvent;
import ch.quantasy.gateway.message.event.joystick.AnalogValueEvent;
import ch.quantasy.gateway.message.event.joystick.CalibratedEvent;
import ch.quantasy.gateway.message.event.joystick.PositionEvent;
import ch.quantasy.gateway.message.intent.joystick.JoystickIntent;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.joystick.JoystickDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class JoystickServiceContract extends DeviceServiceContract {

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

    public final String Position;
    public final String STATUS_POSITION;
    public final String STATUS_POSITION_THRESHOLD;
    public final String STATUS_POSITION_CALLBACK_PERIOD;
    public final String EVENT_POSITION;
    public final String EVENT_POSITION_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CALIBRATE;
    public final String EVENT_CALIBRATE;

    public final String PRESSED;
    public final String EVENT_PRESSED;

    public final String RELEASED;
    public final String EVENT_RELEASED;

    public JoystickServiceContract(JoystickDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public JoystickServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Joystick.toString());
    }

    public JoystickServiceContract(String id, String device) {
        super(id, device, JoystickIntent.class);

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

        Position = "position";
        STATUS_POSITION = STATUS + "/" + Position;
        STATUS_POSITION_THRESHOLD = STATUS_POSITION + "/" + THRESHOLD;
        STATUS_POSITION_CALLBACK_PERIOD = STATUS_POSITION + "/" + CALLBACK_PERIOD;
        EVENT_POSITION = EVENT + "/" + Position;
        EVENT_POSITION_REACHED = EVENT_POSITION + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;

        CALIBRATE = "calibrate";
        EVENT_CALIBRATE = EVENT + "/" + CALIBRATE;

        PRESSED = "pressed";
        EVENT_PRESSED = EVENT + "/" + PRESSED;

        RELEASED = "released";
        EVENT_RELEASED = EVENT + "/" + RELEASED;
        
          addMessageTopic(EVENT_CALIBRATE, CalibratedEvent.class);
        addMessageTopic(EVENT_PRESSED, ButtonEvent.class);
        addMessageTopic(EVENT_RELEASED, ButtonEvent.class);
addMessageTopic(EVENT_ANALOG_VALUE, AnalogValueEvent.class);
        addMessageTopic(EVENT_POSITION, PositionEvent.class);
        addMessageTopic(EVENT_ANALOG_VALUE_REACHED, AnalogValueEvent.class);
        addMessageTopic(EVENT_POSITION_REACHED, PositionEvent.class);
        

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {

      
        descriptions.put(STATUS_ANALOG_VALUE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_POSITION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_ANALOG_VALUE_THRESHOLD, "option: [x|o|i|<|>]\n minX: [0..4095]\n maxX: [0..4095]\n minY: [0..4095]\n maxY: [0..4095]");
        descriptions.put(STATUS_POSITION_THRESHOLD, "option: [x|o|i|<|>]\n minX: [-100..100]\n maxX: [-100..100]\n minY: [-100..100]\n maxY: [-100..100]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
    }
}
