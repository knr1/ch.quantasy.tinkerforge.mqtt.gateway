/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.mqtt.agents;

import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import ch.quantasy.mqtt.gateway.client.ClientContract;
import ch.quantasy.mqtt.gateway.client.GatewayClient;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class GenericAgent extends GatewayClient<ClientContract> {

    private final ManagerServiceContract managerServiceContract;
    private final Map<TinkerforgeStackAddress, Boolean> stacks;

    public GenericAgent(URI mqttURI, String clientID, ClientContract contract) throws MqttException {
        super(mqttURI, clientID, contract);
        managerServiceContract = new ManagerServiceContract("Manager");
        stacks = new HashMap<>();
    }

    public boolean isStackConnected(TinkerforgeStackAddress address) {
        synchronized (stacks) {
            return stacks.get(address);
        }
    }

    public void connectStacks(TinkerforgeStackAddress... addresses) {
        for (TinkerforgeStackAddress address : addresses) {

            String stackName = address.getHostName() + ":" + address.getPort();
            synchronized (stacks) {
                stacks.put(address, false);
            }
            System.out.println("Subscribing to "+address);
            subscribe(managerServiceContract.STATUS_STACK_ADDRESS + "/" + stackName , (topic, payload) -> {
                System.out.println("Message arrived from: "+topic);
                Boolean isConnected = getMapper().readValue(payload, Boolean.class);
                synchronized (stacks) {
                    stacks.put(address, isConnected);
                    stacks.notifyAll();
                }
            });
                        System.out.println("Connecting: "+stackName);

            publishIntent(managerServiceContract.INTENT_STACK_ADDRESS_ADD, address);
        }
        boolean allConnected = false;
        while (!allConnected) {
            allConnected = true;
            synchronized (stacks) {
                for (boolean connected : stacks.values()) {
                    allConnected &= connected;
                }
            }
            if (allConnected != true) {
                synchronized (stacks) {
                    try {
                        stacks.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GenericAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }
    
   

}
