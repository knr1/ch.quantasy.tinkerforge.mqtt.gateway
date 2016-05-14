/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.stackManager;

import ch.quantasy.gateway.service.*;

/**
 *
 * @author reto
 */
public class ManagerServiceContract extends ServiceContract {

    public final String STACK;

    public final String STATUS_STACK;
    public final String EVENT_STACK;
    public final String INTENT_STACK;

    public final String ADDRESS;
    public final String STATUS_STACK_ADDRESS;

    public final String INTENT_STACK_ADDRESS;
    public final String ADD;
    public final String INTENT_STACK_ADDRESS_ADD;
    public final String REMOVE;
    public final String INTENT_STACK_ADDRESS_REMOVE;

    public final String EVENT_STACK_ADDRESS;
    public final String ADDED;
    public final String EVENT_STACK_ADDRESS_ADDED;
    public final String REMOVED;
    public final String EVENT_STACK_ADDRESS_REMOVED;

    public final String DEVICE;
    public final String STATUS_DEVICE;
    public final String EVENT_DEVICE;
    public final String CONNECTED;

    public String EVENT_ADDRESS_CONNECTED;
    public String DISCONNECTED;

    public String EVENT_ADDRESS_DISCONNECTED;

    public ManagerServiceContract(String base) {
        super(base, null);

        STACK = "stack";

        STATUS_STACK = STATUS + "/" + STACK;
        EVENT_STACK = EVENT + "/" + STACK;
        INTENT_STACK = INTENT + "/" + STACK;

        ADDRESS = "address";
        STATUS_STACK_ADDRESS = STATUS_STACK + "/" + ADDRESS;
        INTENT_STACK_ADDRESS = INTENT_STACK + "/" + ADDRESS;
        ADD = "add";
        INTENT_STACK_ADDRESS_ADD = INTENT_STACK_ADDRESS + "/" + ADD;
        REMOVE = "remove";
        INTENT_STACK_ADDRESS_REMOVE = INTENT_STACK_ADDRESS + "/" + REMOVE;

        EVENT_STACK_ADDRESS = EVENT_STACK + "/" + ADDRESS;
        ADDED = "added";
        EVENT_STACK_ADDRESS_ADDED = EVENT_STACK_ADDRESS + "/" + ADDED;
        REMOVED = "removed";
        EVENT_STACK_ADDRESS_REMOVED = EVENT_STACK_ADDRESS + "/" + REMOVED;

        DEVICE = "device";
        STATUS_DEVICE = STATUS + "/" + DEVICE;
        EVENT_DEVICE = EVENT + "/" + DEVICE;
        CONNECTED = "connected";

        EVENT_ADDRESS_CONNECTED = EVENT_DEVICE + "/" + CONNECTED;
        DISCONNECTED = "disconnected";

        EVENT_ADDRESS_DISCONNECTED = EVENT_DEVICE + "/" + DISCONNECTED;

    }

}
