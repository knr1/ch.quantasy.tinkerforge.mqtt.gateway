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
package ch.quantasy.gateway.binding.tinkerforge.dc;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.dc.EmergencyShutdownEvent;
import ch.quantasy.gateway.binding.tinkerforge.dc.FullBrakeEvent;
import ch.quantasy.gateway.binding.tinkerforge.dc.UnderVoltageEvent;
import ch.quantasy.gateway.binding.tinkerforge.dc.VelocityEvent;
import ch.quantasy.gateway.binding.tinkerforge.dc.DCIntent;
import ch.quantasy.gateway.binding.tinkerforge.dc.AccelerationStatus;
import ch.quantasy.gateway.binding.tinkerforge.dc.DriveModeStatus;
import ch.quantasy.gateway.binding.tinkerforge.dc.EnableStatus;
import ch.quantasy.gateway.binding.tinkerforge.dc.MinimumVoltageStatus;
import ch.quantasy.gateway.binding.tinkerforge.dc.PwmFrequencyStatus;
import ch.quantasy.gateway.binding.tinkerforge.dc.VelocityStatus;
import ch.quantasy.gateway.binding.tinkerforge.dc.VelocityPeriodStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.dc.DCDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class DCServiceContract extends DeviceServiceContract {

    public final String ENABLED;
    public final String STATUS_ENABLED;

    public final String MINIMUM_VOLTAGE;
    public final String STATUS_MINIMUM_VOLTAGE;

    public final String UNDERVOLTAGE;
    public final String EVENT_UNDERVOLTAGE;

    public final String VELOCITY;
    private final String STATUS_VELOCITY;
    public final String EVENT_VELOCITY;
    public final String EVENT_VELOCITY_REACHED;
    public final String REACHED;

    public final String CALLBACK_PERIOD;
    public final String STATUS_VELOCITY_CALLBACK_PERIOD;

    public final String DRIVER_MODE;
    public final String STATUS_DRIVER_MODE;

    public final String PWM_FREQUENCY;
    public final String STATUS_PWM_FREQUENCY;

    public final String FULL_BRAKE;
    public final String EVENT_FULL_BRAKE;

    public final String ACCELERATION;
    public final String STATUS_ACCELERATION;

    public final String EMERGENCY_SHUTDOWN;
    public final String EVENT_EMERGENCY_SHUTDOWN;
    public final String STATUS_VELOCITY_VELOCITY;
    public final String EVENT_VELOCITY_VELOCITY;

    public DCServiceContract(DCDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public DCServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.DC.toString());
    }

    public DCServiceContract(String id, String device) {
        super(id, device, DCIntent.class);

        ENABLED = "enabled";
        STATUS_ENABLED = STATUS + "/" + ENABLED;

        VELOCITY = "velocity";
        STATUS_VELOCITY = STATUS + "/" + VELOCITY;
        EVENT_VELOCITY = EVENT + "/" + VELOCITY;

        STATUS_VELOCITY_VELOCITY = STATUS_VELOCITY + "/" + VELOCITY;
        EVENT_VELOCITY_VELOCITY = EVENT_VELOCITY + "/" + VELOCITY;
        REACHED = "reached";
        EVENT_VELOCITY_REACHED = EVENT_VELOCITY + "/" + REACHED;

        CALLBACK_PERIOD = "callbackPeriod";
        STATUS_VELOCITY_CALLBACK_PERIOD = STATUS_VELOCITY + "/" + CALLBACK_PERIOD;

        FULL_BRAKE = "fullBrake";
        EVENT_FULL_BRAKE = EVENT + "/" + FULL_BRAKE;

        ACCELERATION = "acceleration";
        STATUS_ACCELERATION = STATUS + "/" + ACCELERATION;

        MINIMUM_VOLTAGE = "minimumVoltage";
        STATUS_MINIMUM_VOLTAGE = STATUS + "/" + MINIMUM_VOLTAGE;

        UNDERVOLTAGE = "undervoltage";
        EVENT_UNDERVOLTAGE = EVENT + "/" + UNDERVOLTAGE;

        DRIVER_MODE = "driverMode";
        STATUS_DRIVER_MODE = STATUS + "/" + DRIVER_MODE;

        EMERGENCY_SHUTDOWN = "emergencyShutdown";
        EVENT_EMERGENCY_SHUTDOWN = EVENT + "/" + EMERGENCY_SHUTDOWN;

        PWM_FREQUENCY = "pwmFrequency";
        STATUS_PWM_FREQUENCY = STATUS + "/" + PWM_FREQUENCY;

    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_FULL_BRAKE, FullBrakeEvent.class);
        messageTopicMap.put(EVENT_UNDERVOLTAGE, UnderVoltageEvent.class);
        messageTopicMap.put(EVENT_VELOCITY, VelocityEvent.class);
        messageTopicMap.put(EVENT_VELOCITY_REACHED, VelocityEvent.class);
        messageTopicMap.put(EVENT_EMERGENCY_SHUTDOWN, EmergencyShutdownEvent.class);
        messageTopicMap.put(STATUS_ACCELERATION, AccelerationStatus.class);
        messageTopicMap.put(STATUS_DRIVER_MODE, DriveModeStatus.class);
        messageTopicMap.put(STATUS_ENABLED, EnableStatus.class);
        messageTopicMap.put(STATUS_MINIMUM_VOLTAGE, MinimumVoltageStatus.class);
        messageTopicMap.put(STATUS_PWM_FREQUENCY, PwmFrequencyStatus.class);
        messageTopicMap.put(STATUS_VELOCITY_VELOCITY, VelocityStatus.class);
        messageTopicMap.put(STATUS_VELOCITY_CALLBACK_PERIOD, VelocityPeriodStatus.class);
    }

}
