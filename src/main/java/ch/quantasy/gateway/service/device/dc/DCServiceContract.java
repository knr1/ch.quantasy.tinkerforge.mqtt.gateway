/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service.device.dc;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.dc.DCDevice;

/**
 *
 * @author reto
 */
public class DCServiceContract extends DeviceServiceContract {

    public final String ENABLED;
    public final String STATUS_ENABLED;
    public final String INTENT_ENABLED;
       
    public final String MINIMUM_VOLTAGE;
    public final String INTENT_MINIMUM_VOLTAGE;
    public final String STATUS_MINIMUM_VOLTAGE;
    
    public final String UNDERVOLTAGE;
    public final String EVENT_UNDERVOLTAGE;

    public final String VELOCITY;
    public final String INTENT_VELOCITY;
    public final String STATUS_VELOCITY;
    public final String EVENT_VELOCITY;
    
    public final String CALLBACK_PERIOD;
    public final String INTENT_VELOCITY_CALLBACK_PERIOD;
    public final String STATUS_VELOCITY_CALLBACK_PERIOD;
            
    public final String DRIVER_MODE;
    public final String INTENT_DRIVER_MODE;
    public final String STATUS_DRIVER_MODE;

    
    public final String PWM_FREQUENCY;
    public final String INTENT_PWM_FREQUENCY;
    public final String STATUS_PWM_FREQUENCY;
            
    public final String FULL_BRAKE;
    public final String EVENT_FULL_BRAKE;

    public final String ACCELERATION;
    public final String INTENT_ACCELERATION;
    public final String STATUS_ACCELERATION;
    
    public final String EMERGENCY_SHUTDOWN;
    public final String EVENT_EMERGENCY_SHUTDOWN;

    public DCServiceContract(DCDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public DCServiceContract(String id, String device) {
        super(id, device);
        
        ENABLED="enabled";
        STATUS_ENABLED=STATUS+"/"+ENABLED;
        INTENT_ENABLED=INTENT+"/"+ENABLED;
        
        VELOCITY="velocity";
        STATUS_VELOCITY=STATUS+"/"+VELOCITY;
        INTENT_VELOCITY=INTENT+"/"+VELOCITY;
        EVENT_VELOCITY=EVENT+"/"+VELOCITY;
        
        CALLBACK_PERIOD="callbackPeriod";
        
        STATUS_VELOCITY_CALLBACK_PERIOD=STATUS_VELOCITY+"/"+CALLBACK_PERIOD;
        INTENT_VELOCITY_CALLBACK_PERIOD=INTENT_VELOCITY+"/"+CALLBACK_PERIOD;
        
        FULL_BRAKE = "fullBrake";
        EVENT_FULL_BRAKE = EVENT + "/" + FULL_BRAKE;

        ACCELERATION = "acceleration";
        INTENT_ACCELERATION = INTENT + "/" + ACCELERATION;
        STATUS_ACCELERATION=STATUS+"/"+ACCELERATION;

        MINIMUM_VOLTAGE = "minimumVoltage";
        INTENT_MINIMUM_VOLTAGE = INTENT + "/" + MINIMUM_VOLTAGE;
        STATUS_MINIMUM_VOLTAGE = STATUS + "/" + MINIMUM_VOLTAGE;
        
        UNDERVOLTAGE="undervoltage";
        EVENT_UNDERVOLTAGE=EVENT+"/"+UNDERVOLTAGE;

        DRIVER_MODE = "driverMode";
        INTENT_DRIVER_MODE = INTENT + "/" + DRIVER_MODE;
        STATUS_DRIVER_MODE=STATUS+"/"+DRIVER_MODE;

        EMERGENCY_SHUTDOWN="emergencyShutdown";
        EVENT_EMERGENCY_SHUTDOWN=STATUS+"/"+EMERGENCY_SHUTDOWN;
        
        PWM_FREQUENCY="pwmFrequency";
        STATUS_PWM_FREQUENCY=STATUS+"/"+PWM_FREQUENCY;
        INTENT_PWM_FREQUENCY=INTENT+"/"+PWM_FREQUENCY;
    }
}
