/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway;

import ch.quantasy.gateway.service.stackManager.ManagerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class Tinkerer {
    public static void main(String[] args) throws MqttException, InterruptedException, JsonProcessingException, IOException {
        ManagerService managerService=new ManagerService();
        System.out.println(""+managerService);
        LEDStripDualButtonAgent agent=new LEDStripDualButtonAgent();
        Thread.sleep(1000);
        agent.connect();
        Thread.sleep(1000);

        agent.config();
        Thread.sleep(1000);
        agent.leds(0);
        Thread.sleep(1000);
        agent.leds(1);
        System.in.read();
    }
}
