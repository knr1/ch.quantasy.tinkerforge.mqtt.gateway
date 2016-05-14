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
    public final String INTENT_LED_STATE;
    public final String STATUS_LED_STATE;

    public final String SELECTED_LED_STATE;
    public final String INTENT_SELECTED_LED_STATE;

    public final String STATE_CHANGED;
    public final String EVENT_STATE_CHANGED;
    
    public final String MONOFLOP;
    public final String INTENT_MONOFLOP;    
    
    public DualButtonServiceContract(DualButtonDevice device){
                this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }
    public DualButtonServiceContract(String id, String device) {
        super(id, device);
        
        STATE_CHANGED = "stateChanged";
        EVENT_STATE_CHANGED = EVENT + "/" + STATE_CHANGED;

        MONOFLOP = "monoflop";
        INTENT_MONOFLOP = INTENT + "/" + MONOFLOP;

        LED_STATE = "LEDState";
        INTENT_LED_STATE = INTENT + "/" + LED_STATE;
        STATUS_LED_STATE = STATUS + "/" + LED_STATE;

        SELECTED_LED_STATE = "selectedLEDState";
        INTENT_SELECTED_LED_STATE = INTENT + "/" + SELECTED_LED_STATE;

    }
}
