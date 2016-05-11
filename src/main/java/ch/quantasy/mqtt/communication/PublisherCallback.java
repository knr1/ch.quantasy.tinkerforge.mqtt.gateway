/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.mqtt.communication;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public interface PublisherCallback{
    public MqttMessage getMessageToPublish(String topic);
}
