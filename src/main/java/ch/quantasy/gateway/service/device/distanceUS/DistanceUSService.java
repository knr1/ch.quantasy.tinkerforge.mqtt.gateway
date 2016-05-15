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
package ch.quantasy.gateway.service.device.distanceUS;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.distanceUS.DeviceDistanceCallbackThreshold;
import ch.quantasy.tinkerforge.device.distanceUS.DistanceUSDevice;
import ch.quantasy.tinkerforge.device.distanceUS.DistanceUSDeviceCallback;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DistanceUSService extends AbstractDeviceService<DistanceUSDevice, DistanceUSServiceContract> implements DistanceUSDeviceCallback {

    public DistanceUSService(DistanceUSDevice device) throws MqttException {

        super(device, new DistanceUSServiceContract(device));
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getServiceContract().INTENT_MOVING_AVERAGE, "[0..100]");
        
        addDescription(getServiceContract().EVENT_DISTANCE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [[0..4095]\n");
        addDescription(getServiceContract().EVENT_DISTANCE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..4095]\n");
        addDescription(getServiceContract().STATUS_DISTANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DISTANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_MOVING_AVERAGE, "[0..100]");
        
   
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;

        }
        try {
            if (string.startsWith(getServiceContract().INTENT_DEBOUNCE_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setDebouncePeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_DISTANCE_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setDistanceCallbackPeriod(period);
            }

            if (string.startsWith(getServiceContract().INTENT_DISTANCE_THRESHOLD)) {

                DeviceDistanceCallbackThreshold threshold = getMapper().readValue(payload, DeviceDistanceCallbackThreshold.class);
                getDevice().setDistanceCallbackThreshold(threshold);
            }
            
            if (string.startsWith(getServiceContract().INTENT_MOVING_AVERAGE)) {

                Short movingAverage = getMapper().readValue(payload, Short.class);
                getDevice().setMovingAverage(movingAverage);
            }

        } catch (IOException ex) {
            Logger.getLogger(DistanceUSService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }


    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void distanceCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DISTANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void distanceCallbackThresholdChanged(DeviceDistanceCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_DISTANCE_THRESHOLD, threshold);
    }

    @Override
    public void movingAverageChanged(short movingAverage) {
        addStatus(getServiceContract().STATUS_MOVING_AVERAGE, movingAverage);
    }
    
    @Override
    public void distance(int i) {
        addEvent(getServiceContract().EVENT_DISTANCE, new DisntanceEvent(i));  
    }

    @Override
    public void distanceReached(int i) {
        addEvent(getServiceContract().EVENT_DISTANCE_REACHED, new DisntanceEvent(i));
    }

    class DisntanceEvent {

        protected long timestamp;
        protected int value;

        public DisntanceEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public DisntanceEvent(int value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getValue() {
            return value;
        }

    }

}
