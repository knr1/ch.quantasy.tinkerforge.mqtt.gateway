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
    public final String INTENT_STATUS_LED;

    public final String MINIMUM_VOLTAGE;
    public final String INTENT_MINIMUM_VOLTAGE;
    public final String STATUS_MINIMUM_VOLTAGE;

    public final String UNDERVOLTAGE;
    public final String EVENT_UNDERVOLTAGE;

    public final String EVENT_VELOCITY_REACHED;
    public final String VELOCITY_REACHED;

    public final String DRIVER_MODE;
    public final String INTENT_DRIVER_MODE;
    public final String STATUS_DRIVER_MODE;

    public final String OUTPUT_VOLTAGE;
    public final String INTENT_OUTPUT_VOLTAGE;
    public final String STATUS_OUTPUT_VOLTAGE;

    public final String POSITION_REACHED;
    public final String EVENT_POSITION_REACHED;

    public final String SERVOS;
    public final String INTENT_SERVOS;
    public final String STATUS_SERVOS;

    public ServoServiceContract(ServoDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public ServoServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Servo.toString());
    }

    public ServoServiceContract(String id, String device) {
        super(id, device);

        STATUS_LED = "statusLED";
        STATUS_STATUS_LED = STATUS + "/" + STATUS_LED;
        INTENT_STATUS_LED = INTENT + "/" + STATUS_LED;

        VELOCITY_REACHED = "velocityReached";
        EVENT_VELOCITY_REACHED = EVENT + "/" + VELOCITY_REACHED;

        POSITION_REACHED = "positionReached";
        EVENT_POSITION_REACHED = EVENT + "/" + POSITION_REACHED;

        SERVOS = "servos";
        INTENT_SERVOS = INTENT + "/" + SERVOS;
        STATUS_SERVOS = STATUS + "/" + SERVOS;

        MINIMUM_VOLTAGE = "minimumVoltage";
        INTENT_MINIMUM_VOLTAGE = INTENT + "/" + MINIMUM_VOLTAGE;
        STATUS_MINIMUM_VOLTAGE = STATUS + "/" + MINIMUM_VOLTAGE;

        UNDERVOLTAGE = "undervoltage";
        EVENT_UNDERVOLTAGE = EVENT + "/" + UNDERVOLTAGE;

        DRIVER_MODE = "driverMode";
        INTENT_DRIVER_MODE = INTENT + "/" + DRIVER_MODE;
        STATUS_DRIVER_MODE = STATUS + "/" + DRIVER_MODE;

        OUTPUT_VOLTAGE = "outputVoltage";
        STATUS_OUTPUT_VOLTAGE = STATUS + "/" + OUTPUT_VOLTAGE;
        INTENT_OUTPUT_VOLTAGE = INTENT + "/" + OUTPUT_VOLTAGE;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_SERVOS, "--- \n  {- \n  id: [0..6]\n  [enabled: [true|false]\n|]  [position: [-32767..32767]\n|]  [acceleration: [0..65536]\n|]  [velocity: [0..65535]\n|]  [degree:\n    min: [-32767..32767]\n    max: [-32767..32767]\n|]  [period: [1..65535]\n|]  [pulseWidth:\n    min: [1..65535]\n    max: [1..65535]|]}_7");
        descriptions.put(INTENT_STATUS_LED, "[true|false]");
        descriptions.put(INTENT_MINIMUM_VOLTAGE, "[5000.." + Integer.MAX_VALUE + "]");
        descriptions.put(INTENT_OUTPUT_VOLTAGE, "[2000..9000]");

        descriptions.put(EVENT_POSITION_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [0..6]\n position: [-32767..32767]");
        descriptions.put(EVENT_UNDERVOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");
        descriptions.put(EVENT_VELOCITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [0..6]\n value: [0.." + Short.MAX_VALUE + "]");

        descriptions.put(STATUS_SERVOS, "--- \n  {- \n  id: [0..6]\n  enabled: [true|false|null]\n  position: [-32767..32767|null]\n  acceleration: [0..65536|null]\n  velocity: [0..65535|null]\n  degree: [[\n    min: [-32767..32767]\n    max: [-32767..32767]\n]|null]\n  period: [1..65535|null]\n  pulseWidth: [[\n    min: [1..65535]\n    max: [1..65535]]|null]}_7");
        descriptions.put(STATUS_STATUS_LED, "[true|false]");
        descriptions.put(STATUS_MINIMUM_VOLTAGE, "[6.." + Integer.MAX_VALUE + "]");
        descriptions.put(STATUS_OUTPUT_VOLTAGE, "[1..20000]");
    }
}
