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
package ch.quantasy.gateway.binding.tinkerforge.stepper;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.stepper.DriveModeStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.AllDataPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.DecayStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.EnableStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.MinimumVoltageStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.RectificationStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.StepperIntent;
import ch.quantasy.gateway.binding.tinkerforge.stepper.StepsStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.TargetPositionStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.CurrentPositionStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.MotorCurrentStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.StepModeStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.TimeBaseStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.VelocityStatus;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.stepper.StepperDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class StepperServiceContract extends DeviceServiceContract {

    public final String ENABLED;
    public final String STATUS_ENABLED;

    public final String MINIMUM_VOLTAGE;
    public final String STATUS_MINIMUM_VOLTAGE;

    public final String UNDERVOLTAGE;
    public final String EVENT_UNDERVOLTAGE;
    
    public final String STATUS_ALL_DATA_PERIOD;
    public final String STATUS_TARGET_POSITION;
    public final String STATUS_DRIVE_MODE;
    private final String STEPS;
    public final String STATUS_STEPS;
    public final String STATUS_RECTIFICATION;
    public final String STATUS_CURRENT_POSITION;
    public final String STATUS_DECAY;
    public final String STATUS_MAX_VELOCITY;
    public final String STATUS_MOTOR_CURRENT;
    public final String STATUS_SPEED_RAMP;
    public final String STATUS_STEP_MODE;
    public final String STATUS_TIME_BASE;

    public final String VELOCITY;
    private final String STATUS_VELOCITY;
    public final String EVENT_VELOCITY;
    public final String EVENT_VELOCITY_REACHED;
    public final String REACHED;
    public final String CALLBACK_PERIOD;
    public final String STATUS_VELOCITY_CALLBACK_PERIOD;



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

    public StepperServiceContract(StepperDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public StepperServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.Stepper.toString());
    }

    public StepperServiceContract(String id, String device) {
        super(id, device, StepperIntent.class);

        ENABLED = "enabled";
        STATUS_ENABLED = STATUS + "/" + ENABLED;
        
        STATUS_ALL_DATA_PERIOD=STATUS+"/"+"allDataPeriod";
        STATUS_TARGET_POSITION=STATUS+"/"+"targetPosition";
        STATUS_DRIVE_MODE=STATUS+"/"+"driveMode";
        STEPS="steps";
        STATUS_STEPS=STATUS+"/"+STEPS;
        STATUS_RECTIFICATION=STATUS+"/"+"rectification";
        STATUS_CURRENT_POSITION=STATUS+"/"+"currentPosition";
        STATUS_MOTOR_CURRENT=STATUS+"/"+"motorCurrent";
        VELOCITY = "velocity";
        STATUS_VELOCITY = STATUS + "/" + VELOCITY;
        STATUS_MAX_VELOCITY=STATUS+"/"+VELOCITY+"Max";
        EVENT_VELOCITY = EVENT + "/" + VELOCITY;
        STATUS_DECAY=STATUS+"/"+"decay";
        STATUS_SPEED_RAMP=STATUS+"/"+"speedRamp";
        STATUS_STEP_MODE=STATUS+"/"+"stepMode";
        STATUS_TIME_BASE=STATUS+"/"+"timeBase";
        
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


        EMERGENCY_SHUTDOWN = "emergencyShutdown";
        EVENT_EMERGENCY_SHUTDOWN = EVENT + "/" + EMERGENCY_SHUTDOWN;

        PWM_FREQUENCY = "pwmFrequency";
        STATUS_PWM_FREQUENCY = STATUS + "/" + PWM_FREQUENCY;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(STATUS_STEP_MODE, StepModeStatus.class);
        messageTopicMap.put(STATUS_ALL_DATA_PERIOD, AllDataPeriodStatus.class);
        messageTopicMap.put(STATUS_TARGET_POSITION, TargetPositionStatus.class);
        messageTopicMap.put(STATUS_DRIVE_MODE, DriveModeStatus.class);
        messageTopicMap.put(STATUS_STEPS, StepsStatus.class);
        messageTopicMap.put(STATUS_RECTIFICATION, RectificationStatus.class);
        messageTopicMap.put(STATUS_ENABLED, EnableStatus.class);
        messageTopicMap.put(STATUS_CURRENT_POSITION, CurrentPositionStatus.class);
        messageTopicMap.put(STATUS_DECAY, DecayStatus.class);
        messageTopicMap.put(STATUS_MAX_VELOCITY, VelocityStatus.class);
        messageTopicMap.put(STATUS_MOTOR_CURRENT, MotorCurrentStatus.class);
        messageTopicMap.put(STATUS_TIME_BASE, TimeBaseStatus.class);
        messageTopicMap.put(STATUS_MINIMUM_VOLTAGE, MinimumVoltageStatus.class);
        messageTopicMap.put(STATUS_VELOCITY_VELOCITY, VelocityStatus.class);
    }

   
}
