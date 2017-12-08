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
package ch.quantasy.gateway.service.device.servo;

import ch.quantasy.gateway.message.servo.UnderVoltageEvent;
import ch.quantasy.gateway.message.servo.VelocityEvent;
import ch.quantasy.gateway.message.servo.PositionEvent;
import ch.quantasy.gateway.message.servo.ServoIntent;
import ch.quantasy.gateway.message.servo.MinimumVoltageStatus;
import ch.quantasy.gateway.message.servo.OutputVoltageStatus;
import ch.quantasy.gateway.message.servo.ServosStatus;
import ch.quantasy.gateway.message.servo.StatusLEDStatus;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.servo.ServoDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class ServoServiceContract extends DeviceServiceContract {

    public final String STATUS_LED;
    public final String STATUS_STATUS_LED;

    public final String MINIMUM_VOLTAGE;
    public final String STATUS_MINIMUM_VOLTAGE;

    public final String UNDERVOLTAGE;
    public final String EVENT_UNDERVOLTAGE;

    public final String EVENT_VELOCITY_REACHED;
    public final String VELOCITY_REACHED;

    public final String DRIVER_MODE;
    public final String STATUS_DRIVER_MODE;

    public final String OUTPUT_VOLTAGE;
    public final String STATUS_OUTPUT_VOLTAGE;

    public final String POSITION_REACHED;
    public final String EVENT_POSITION_REACHED;

    public final String SERVOS;
    public final String STATUS_SERVOS;

    public ServoServiceContract(ServoDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public ServoServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Servo.toString());
    }

    public ServoServiceContract(String id, String device) {
        super(id, device, ServoIntent.class);

        STATUS_LED = "statusLED";
        STATUS_STATUS_LED = STATUS + "/" + STATUS_LED;

        VELOCITY_REACHED = "velocityReached";
        EVENT_VELOCITY_REACHED = EVENT + "/" + VELOCITY_REACHED;

        POSITION_REACHED = "positionReached";
        EVENT_POSITION_REACHED = EVENT + "/" + POSITION_REACHED;

        SERVOS = "servos";
        STATUS_SERVOS = STATUS + "/" + SERVOS;

        MINIMUM_VOLTAGE = "minimumVoltage";
        STATUS_MINIMUM_VOLTAGE = STATUS + "/" + MINIMUM_VOLTAGE;

        UNDERVOLTAGE = "undervoltage";
        EVENT_UNDERVOLTAGE = EVENT + "/" + UNDERVOLTAGE;

        DRIVER_MODE = "driverMode";
        STATUS_DRIVER_MODE = STATUS + "/" + DRIVER_MODE;

        OUTPUT_VOLTAGE = "outputVoltage";
        STATUS_OUTPUT_VOLTAGE = STATUS + "/" + OUTPUT_VOLTAGE;
        addMessageTopic(EVENT_POSITION_REACHED, PositionEvent.class);
        addMessageTopic(EVENT_UNDERVOLTAGE, UnderVoltageEvent.class);
        addMessageTopic(EVENT_VELOCITY_REACHED, VelocityEvent.class);
        addMessageTopic(STATUS_SERVOS, ServosStatus.class);
        addMessageTopic(STATUS_STATUS_LED, StatusLEDStatus.class);
        addMessageTopic(STATUS_MINIMUM_VOLTAGE, MinimumVoltageStatus.class);
        addMessageTopic(STATUS_OUTPUT_VOLTAGE, OutputVoltageStatus.class);
    }

    
}
