/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device.remoteSwitch;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDevice;

/**
 *
 * @author reto
 */
public class RemoteSwitchServiceContract extends DeviceServiceContract {

    public final String SWITCHING_DONE;
    public final String EVENT_SWITCHING_DONE;

    public final String REPEATS;
    public final String INTENT_REPEATS;
    public final String STATUS_REPEATS;

    public final String SWITCH_SOCKET_A;
    public final String INTENT_SWITCH_SOCKET_A;

    public final String SWITCH_SOCKET_B;
    public final String INTENT_SWITCH_SOCKET_B;

    public final String SWITCH_SOCKET_C;
    public final String INTENT_SWITCH_SOCKET_C;

    public final String DIM_SOCKET_B;
    public final String INTENT_DIM_SOCKET_B;

    public RemoteSwitchServiceContract(RemoteSwitchDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public RemoteSwitchServiceContract(String id, String device) {
        super(id, device);

        SWITCHING_DONE = "switchingDone";
        EVENT_SWITCHING_DONE = EVENT + "/" + SWITCHING_DONE;

        REPEATS = "repeats";
        INTENT_REPEATS = INTENT + "/" + REPEATS;
        STATUS_REPEATS = STATUS + "/" + REPEATS;

        SWITCH_SOCKET_A = "switchSocketA";
        INTENT_SWITCH_SOCKET_A = INTENT + "/" + SWITCH_SOCKET_A;
        SWITCH_SOCKET_B = "switchSocketB";
        INTENT_SWITCH_SOCKET_B = INTENT + "/" + SWITCH_SOCKET_B;
        SWITCH_SOCKET_C = "switchSocketC";
        INTENT_SWITCH_SOCKET_C = INTENT + "/" + SWITCH_SOCKET_C;
        DIM_SOCKET_B = "dimSocketB";
        INTENT_DIM_SOCKET_B = INTENT + "/" + DIM_SOCKET_B;

    }
}
