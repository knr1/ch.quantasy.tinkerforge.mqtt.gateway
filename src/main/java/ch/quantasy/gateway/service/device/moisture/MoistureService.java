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
package ch.quantasy.gateway.service.device.moisture;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ch.quantasy.tinkerforge.device.moisture.MoistureDevice;
import ch.quantasy.tinkerforge.device.moisture.MoistureDeviceCallback;
import ch.quantasy.tinkerforge.device.moisture.DeviceMoistureCallbackThreshold;
import com.tinkerforge.BrickletMoisture;

/**
 *
 * @author reto
 */
public class MoistureService extends AbstractDeviceService<MoistureDevice, MoistureServiceContract>implements MoistureDeviceCallback {

    

    public MoistureService(MoistureDevice device) throws MqttException {
        super(device,new MoistureServiceContract(device));
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
       byte[] payload = mm.getPayload();
                if (payload == null) {
                    return;

                }
        try {
            if (string.startsWith(getServiceContract().INTENT_TOPIC_DEBOUNCE_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setDebouncePeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_MOISTURE_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setMoistureCallbackPeriod(period);
            }

            if (string.startsWith(getServiceContract().INTENT_TOPIC_MOISTURE_THRESHOLD)) {
                DeviceMoistureCallbackThreshold threshold = getMapper().readValue(payload, DeviceMoistureCallbackThreshold.class );
                getDevice().setMoistureCallbackThreshold(threshold);
            }

        } catch (IOException ex) {
            Logger.getLogger(MoistureService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_TOPIC_DEBOUNCE_PERIOD,period);
    }

    @Override
    public void moistureCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_TOPIC_MOISTURE_CALLBACK_PERIOD,period);
    }

    @Override
    public void moistureCallbackThresholdChanged(DeviceMoistureCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_TOPIC_MOISTURE_THRESHOLD,threshold);
    }

    @Override
    public void movingAverageChanged(short average) {
        addStatus(getServiceContract().STATUS_TOPIC_MOVING_AVERAGE,average);
    }

    @Override
    public void moisture(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_MOISTURE,new MoistureEvent(i));
    }

    @Override
    public void moistureReached(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_MOISTURE_REACHED,new MoistureEvent(i));
    }

    class MoistureEvent {

        protected long timestamp;
        protected int moisture;

        public MoistureEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public MoistureEvent(int value, long timeStamp) {
            this.moisture = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getMoisture() {
            return moisture;
        }

    }

}
