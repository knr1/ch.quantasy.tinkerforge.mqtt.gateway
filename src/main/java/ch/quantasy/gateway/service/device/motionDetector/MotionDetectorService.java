/*
 * Within this step, a service is using the MQTTCommunication and the 'business-logic'
 * The response of the 'business logic' is promoted to the event topic.
 * The 'business-logic' is invoked via the service and then is self-sustaining.
 * The 'business-logic' sends the result via Callback
 * This way we have a Model-'View'-Presenter (MVP) Where the presenter (the service) is glueing together
 * the Model ('business-logic') and the 'View' (the MQTT-Communication)
 * The service is promoting more status information to the status topic about the underlying 'business-logic'.
 *
 * This time, an agent is communicating with the service and controls it.
 * This way we delve into the Service based Agent oriented programming
 */
package ch.quantasy.gateway.service.device.motionDetector;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.gateway.service.device.humidity.HumidityService;
import ch.quantasy.mqtt.communication.MQTTCommunication;
import ch.quantasy.mqtt.communication.MQTTCommunicationCallback;
import ch.quantasy.mqtt.communication.MQTTParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.net.URI;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDeviceCallback;

/**
 *
 * @author reto
 */
public class MotionDetectorService extends AbstractDeviceService<MotionDetectorDevice, MotionDetectorServiceContract> implements MotionDetectorDeviceCallback {

    public MotionDetectorService(MotionDetectorDevice device) throws MqttException {
        super(device, new MotionDetectorServiceContract(device));
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        //There are no intents that can be handled
    }

    @Override
    public void detectionCycleEnded() {
        addEvent(getServiceContract().EVENT_TOPIC_DETECTION_CYCLE_ENDED, System.currentTimeMillis());

    }

    @Override
    public void motionDetected() {
        addEvent(getServiceContract().EVENT_TOPIC_MOTION_DETECTED, System.currentTimeMillis());
    }
}
