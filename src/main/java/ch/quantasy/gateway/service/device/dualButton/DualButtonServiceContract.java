/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.dualButton;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.dualButton.DualButtonDevice;


/**
 *
 * @author reto
 */
public class DualButtonServiceContract extends DeviceServiceContract{

    public final String LED_STATE;
    public final String INTENT_TOPIC_LED_STATE;
    public final String STATUS_TOPIC_LED_STATE;

    public final String SELECTED_LED_STATE;
    public final String INTENT_TOPIC_SELECTED_LED_STATE;

    public final String STATE_CHANGED;
    public final String EVENT_TOPIC_STATE_CHANGED;
    
    public final String MONOFLOP;
    public final String INTENT_TOPIC_MONOFLOP;    
    
    public DualButtonServiceContract(DualButtonDevice device){
                this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }
    public DualButtonServiceContract(String id, String device) {
        super(id, device);
        
        STATE_CHANGED = "stateChanged";
        EVENT_TOPIC_STATE_CHANGED = EVENT_TOPIC + "/" + STATE_CHANGED;

        MONOFLOP = "monoflop";
        INTENT_TOPIC_MONOFLOP = INTENT_TOPIC + "/" + MONOFLOP;

        LED_STATE = "LEDState";
        INTENT_TOPIC_LED_STATE = INTENT_TOPIC + "/" + LED_STATE;
        STATUS_TOPIC_LED_STATE = STATUS_TOPIC + "/" + LED_STATE;

        SELECTED_LED_STATE = "selectedLEDState";
        INTENT_TOPIC_SELECTED_LED_STATE = INTENT_TOPIC + "/" + SELECTED_LED_STATE;

    }
}
