/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.dualRelay;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDevice;

/**
 *
 * @author reto
 */
public class DualRelayServiceContract extends DeviceServiceContract {

    public final String STATE;
    public final String INTENT_STATE;
    public final String STATUS_STATE;

    public final String SELECTED_STATE;
    public final String INTENT_SELECTED_STATE;

    public final String MONOFLOP_DONE;
    public final String EVENT_MONOFLOP_DONE;

    public final String MONOFLOP;
    public final String INTENT_MONOFLOP;

    public DualRelayServiceContract(DualRelayDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public DualRelayServiceContract(String id, String device) {
        super(id, device);
        MONOFLOP_DONE = "monoflopDone";
        EVENT_MONOFLOP_DONE = EVENT + "/" + MONOFLOP_DONE;

        MONOFLOP = "monoflop";
        INTENT_MONOFLOP = INTENT + "/" + MONOFLOP;

        STATE = "state";
        INTENT_STATE = INTENT + "/" + STATE;
        STATUS_STATE = STATUS + "/" + STATE;

        SELECTED_STATE = "selectedState";
        INTENT_SELECTED_STATE = INTENT + "/" + SELECTED_STATE;

    }
}
