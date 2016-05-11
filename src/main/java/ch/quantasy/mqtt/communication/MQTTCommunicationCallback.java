/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.mqtt.communication;

import org.eclipse.paho.client.mqttv3.MqttCallback;

/**
 *
 * @author reto
 */
public interface MQTTCommunicationCallback extends MqttCallback,PublisherCallback{
}
