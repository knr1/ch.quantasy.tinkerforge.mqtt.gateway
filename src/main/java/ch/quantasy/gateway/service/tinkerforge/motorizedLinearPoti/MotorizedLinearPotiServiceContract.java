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
package ch.quantasy.gateway.service.tinkerforge.motorizedLinearPoti;

import ch.quantasy.gateway.message.motorizedLinearPoti.PositionEvent;
import ch.quantasy.gateway.message.motorizedLinearPoti.MotorizedLinearPotiIntent;
import ch.quantasy.gateway.message.motorizedLinearPoti.MotorPositionStatus;
import ch.quantasy.gateway.message.motorizedLinearPoti.PositionCallbackConfigurationStatus;
import ch.quantasy.gateway.message.motorizedLinearPoti.PositionReachedCallbackConfigurationStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.motorizedLinearPoti.MotorizedLinearPotiDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class MotorizedLinearPotiServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_CONFIG;
    private final String MOTOR;

    public final String POSITION;
    public final String STATUS_MOTOR;
    public final String STATUS_MOTOR_POSITION;
    public final String EVENT_POSITION;
    public final String EVENT_POSITION_REACHED;

    public final String STATUS_POSITION_REACHED_CALLBACK_CONFIGURATION;
    public final String STATUS_POSITION_CALLBACK_CONFIGURATION;

    public MotorizedLinearPotiServiceContract(MotorizedLinearPotiDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public MotorizedLinearPotiServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.MotorizedLinearPoti.toString());
    }

    public MotorizedLinearPotiServiceContract(String id, String device) {
        super(id, device, MotorizedLinearPotiIntent.class);

        PERIOD = "period";
        CALLBACK_CONFIG = "callbackConfiguration";

        REACHED = "reached";
        POSITION = "position";
        MOTOR = "motor";
        STATUS_MOTOR = STATUS + "/" + MOTOR;
        STATUS_MOTOR_POSITION = STATUS_MOTOR + "/" + POSITION;
        EVENT_POSITION = EVENT + "/" + POSITION;
        EVENT_POSITION_REACHED = EVENT_POSITION + "/" + REACHED;
        STATUS_POSITION_REACHED_CALLBACK_CONFIGURATION = STATUS + "/" + POSITION + "/" + REACHED + "/" + CALLBACK_CONFIG;
        STATUS_POSITION_CALLBACK_CONFIGURATION = STATUS + "/" + POSITION + "/" + CALLBACK_CONFIG;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_POSITION, PositionEvent.class);
        messageTopicMap.put(EVENT_POSITION_REACHED, PositionEvent.class);
        messageTopicMap.put(STATUS_MOTOR_POSITION, MotorPositionStatus.class);
        messageTopicMap.put(STATUS_POSITION_REACHED_CALLBACK_CONFIGURATION, PositionReachedCallbackConfigurationStatus.class);
        messageTopicMap.put(STATUS_POSITION_CALLBACK_CONFIGURATION, PositionCallbackConfigurationStatus.class);

    }

}
