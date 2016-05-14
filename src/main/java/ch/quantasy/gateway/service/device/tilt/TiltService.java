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
package ch.quantasy.gateway.service.device.tilt;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.tilt.TiltDevice;
import com.tinkerforge.BrickletTilt;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class TiltService extends AbstractDeviceService<TiltDevice, TiltServiceContract> implements BrickletTilt.TiltStateListener {

    public TiltService(TiltDevice device) throws MqttException {
        super(device, new TiltServiceContract(device));
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        //There are no intents that can be handled
    }

    @Override
    public void tiltState(short s) {
        addEvent(getServiceContract().EVENT_TOPIC_TILT_STATE, new TiltEvent(s));
    }
    
    class TiltEvent {

        long timestamp;
        short value;

        public TiltEvent(short value) {
            this(value, System.currentTimeMillis());
        }

        public TiltEvent(short value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getValue() {
            return value;
        }

    }
}
