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
    public final String STATUS_TOPIC_ENABLED;
    public final String INTENT_TOPIC_ENABLED;
       
    public final String MINIMUM_VOLTAGE;
    public final String INTENT_TOPIC_MINIMUM_VOLTAGE;
    public final String STATUS_TOPIC_MINIMUM_VOLTAGE;
    
    public final String UNDERVOLTAGE;
    public final String EVENT_TOPIC_UNDERVOLTAGE;

    public final String VELOCITY;
    public final String INTENT_TOPIC_VELOCITY;
    public final String STATUS_TOPIC_VELOCITY;
    public final String EVENT_TOPIC_VELOCITY;
    
    public final String PERIOD;
    public final String INTENT_TOPIC_VELOCITY_PERIOD;
    public final String STATUS_TOPIC_VELOCITY_PERIOD;
            
    public final String DRIVER_MODE;
    public final String INTENT_TOPIC_DRIVER_MODE;
    public final String STATUS_TOPIC_DRIVER_MODE;

    
    public final String PWM_FREQUENCY;
    public final String INTENT_TOPIC_PWM_FREQUENCY;
    public final String STATUS_TOPIC_PWM_FREQUENCY;
            
    public final String FULL_BRAKE;
    public final String EVENT_TOPIC_FULL_BRAKE;

    public final String ACCELERATION;
    public final String INTENT_TOPIC_ACCELERATION;
    public final String STATUS_TOPIC_ACCELERATION;
    
    public final String EMERGENCY_SHUTDOWN;
    public final String STATUS_TOPIC_EMERGENCY_SHUTDOWN;

    public DCServiceContract(DCDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public DCServiceContract(String id, String device) {
        super(id, device);
        
        ENABLED="enabled";
        STATUS_TOPIC_ENABLED=STATUS_TOPIC+"/"+ENABLED;
        INTENT_TOPIC_ENABLED=INTENT_TOPIC+"/"+ENABLED;
        
        VELOCITY="velocity";
        STATUS_TOPIC_VELOCITY=STATUS_TOPIC+"/"+VELOCITY;
        INTENT_TOPIC_VELOCITY=INTENT_TOPIC+"/"+VELOCITY;
        EVENT_TOPIC_VELOCITY=EVENT_TOPIC+"/"+VELOCITY;
        
        PERIOD="period";
        
        STATUS_TOPIC_VELOCITY_PERIOD=STATUS_TOPIC_VELOCITY+"/"+PERIOD;
        INTENT_TOPIC_VELOCITY_PERIOD=INTENT_TOPIC_VELOCITY+"/"+PERIOD;
        
        FULL_BRAKE = "fullBrake";
        EVENT_TOPIC_FULL_BRAKE = EVENT_TOPIC + "/" + FULL_BRAKE;

        ACCELERATION = "acceleration";
        INTENT_TOPIC_ACCELERATION = INTENT_TOPIC + "/" + ACCELERATION;
        STATUS_TOPIC_ACCELERATION=STATUS_TOPIC+"/"+ACCELERATION;

        MINIMUM_VOLTAGE = "minimumVoltage";
        INTENT_TOPIC_MINIMUM_VOLTAGE = INTENT_TOPIC + "/" + MINIMUM_VOLTAGE;
        STATUS_TOPIC_MINIMUM_VOLTAGE = STATUS_TOPIC + "/" + MINIMUM_VOLTAGE;
        
        UNDERVOLTAGE="undervoltage";
        EVENT_TOPIC_UNDERVOLTAGE=EVENT_TOPIC+"/"+UNDERVOLTAGE;

        DRIVER_MODE = "driverMode";
        INTENT_TOPIC_DRIVER_MODE = INTENT_TOPIC + "/" + DRIVER_MODE;
        STATUS_TOPIC_DRIVER_MODE=STATUS_TOPIC+"/"+DRIVER_MODE;

        EMERGENCY_SHUTDOWN="emergencyShutdown";
        STATUS_TOPIC_EMERGENCY_SHUTDOWN=STATUS_TOPIC+"/"+EMERGENCY_SHUTDOWN;
        
        PWM_FREQUENCY="pwmFrequency";
        STATUS_TOPIC_PWM_FREQUENCY=STATUS_TOPIC+"/"+PWM_FREQUENCY;
        INTENT_TOPIC_PWM_FREQUENCY=INTENT_TOPIC+"/"+PWM_FREQUENCY;
    }
}
