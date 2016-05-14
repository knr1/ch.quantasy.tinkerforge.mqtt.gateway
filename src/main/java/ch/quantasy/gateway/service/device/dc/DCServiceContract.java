/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public final String INTENT_VELOCITY_PERIOD;
    public final String STATUS_VELOCITY_PERIOD;
            
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
    public final String STATUS_EMERGENCY_SHUTDOWN;

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
        
        CALLBACK_PERIOD="callback_period";
        
        STATUS_VELOCITY_PERIOD=STATUS_VELOCITY+"/"+CALLBACK_PERIOD;
        INTENT_VELOCITY_PERIOD=INTENT_VELOCITY+"/"+CALLBACK_PERIOD;
        
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
        STATUS_EMERGENCY_SHUTDOWN=STATUS+"/"+EMERGENCY_SHUTDOWN;
        
        PWM_FREQUENCY="pwmFrequency";
        STATUS_PWM_FREQUENCY=STATUS+"/"+PWM_FREQUENCY;
        INTENT_PWM_FREQUENCY=INTENT+"/"+PWM_FREQUENCY;
    }
}
