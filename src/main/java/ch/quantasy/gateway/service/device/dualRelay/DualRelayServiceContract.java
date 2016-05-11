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
    public final String INTENT_TOPIC_STATE;
    public final String STATUS_TOPIC_STATE;

    public final String SELECTED_STATE;
    public final String INTENT_TOPIC_SELECTED_STATE;

    public final String MONOFLOP_DONE;
    public final String EVENT_TOPIC_MONOFLOP_DONE;

    public final String MONOFLOP;
    public final String INTENT_TOPIC_MONOFLOP;

    public DualRelayServiceContract(DualRelayDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public DualRelayServiceContract(String id, String device) {
        super(id, device);
        MONOFLOP_DONE = "monoflopDone";
        EVENT_TOPIC_MONOFLOP_DONE = EVENT_TOPIC + "/" + MONOFLOP_DONE;

        MONOFLOP = "monoflop";
        INTENT_TOPIC_MONOFLOP = INTENT_TOPIC + "/" + MONOFLOP;

        STATE = "state";
        INTENT_TOPIC_STATE = INTENT_TOPIC + "/" + STATE;
        STATUS_TOPIC_STATE = STATUS_TOPIC + "/" + STATE;

        SELECTED_STATE = "selectedState";
        INTENT_TOPIC_SELECTED_STATE = INTENT_TOPIC + "/" + SELECTED_STATE;

    }
}
