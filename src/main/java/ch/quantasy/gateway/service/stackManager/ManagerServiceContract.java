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

    public final String STATUS_TOPIC_STACK;
    public final String EVENT_TOPIC_STACK;
    public final String INTENT_TOPIC_STACK;

    public final String ADDRESS;
    public final String STATUS_TOPIC_STACK_ADDRESS;

    public final String INTENT_TOPIC_STACK_ADDRESS;
    public final String ADD;
    public final String INTENT_TOPIC_STACK_ADDRESS_ADD;
    public final String REMOVE;
    public final String INTENT_TOPIC_STACK_ADDRESS_REMOVE;

    public final String EVENT_TOPIC_STACK_ADDRESS;
    public final String ADDED;
    public final String EVENT_TOPIC_STACK_ADDRESS_ADDED;
    public final String REMOVED;
    public final String EVENT_TOPIC_STACK_ADDRESS_REMOVED;

    public final String DEVICE;
    public final String STATUS_TOPIC_DEVICE;
    public final String EVENT_TOPIC_DEVICE;
    public final String CONNECTED;

    public String EVENT_TOPIC_ADDRESS_CONNECTED;
    public String DISCONNECTED;

    public String EVENT_TOPIC_ADDRESS_DISCONNECTED;

    public ManagerServiceContract(String base) {
        super(base, null);

        STACK = "stack";

        STATUS_TOPIC_STACK = STATUS_TOPIC + "/" + STACK;
        EVENT_TOPIC_STACK = EVENT_TOPIC + "/" + STACK;
        INTENT_TOPIC_STACK = INTENT_TOPIC + "/" + STACK;

        ADDRESS = "address";
        STATUS_TOPIC_STACK_ADDRESS = STATUS_TOPIC_STACK + "/" + ADDRESS;
        INTENT_TOPIC_STACK_ADDRESS = INTENT_TOPIC_STACK + "/" + ADDRESS;
        ADD = "add";
        INTENT_TOPIC_STACK_ADDRESS_ADD = INTENT_TOPIC_STACK_ADDRESS + "/" + ADD;
        REMOVE = "remove";
        INTENT_TOPIC_STACK_ADDRESS_REMOVE = INTENT_TOPIC_STACK_ADDRESS + "/" + REMOVE;

        EVENT_TOPIC_STACK_ADDRESS = EVENT_TOPIC_STACK + "/" + ADDRESS;
        ADDED = "added";
        EVENT_TOPIC_STACK_ADDRESS_ADDED = EVENT_TOPIC_STACK_ADDRESS + "/" + ADDED;
        REMOVED = "removed";
        EVENT_TOPIC_STACK_ADDRESS_REMOVED = EVENT_TOPIC_STACK_ADDRESS + "/" + REMOVED;

        DEVICE = "device";
        STATUS_TOPIC_DEVICE = STATUS_TOPIC + "/" + DEVICE;
        EVENT_TOPIC_DEVICE = EVENT_TOPIC + "/" + DEVICE;
        CONNECTED = "connected";

        EVENT_TOPIC_ADDRESS_CONNECTED = EVENT_TOPIC_DEVICE + "/" + CONNECTED;
        DISCONNECTED = "disconnected";

        EVENT_TOPIC_ADDRESS_DISCONNECTED = EVENT_TOPIC_DEVICE + "/" + DISCONNECTED;

    }

}
