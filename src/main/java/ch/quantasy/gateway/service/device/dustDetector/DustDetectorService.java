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
package ch.quantasy.gateway.service.device.dustDetector;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dustDetector.DeviceDustDensityCallbackThreshold;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDevice;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDeviceCallback;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DustDetectorService extends AbstractDeviceService<DustDetectorDevice, DustDetectorServiceContract> implements DustDetectorDeviceCallback {

    public DustDetectorService(DustDetectorDevice device) throws MqttException {

        super(device, new DustDetectorServiceContract(device));
        addDescription(getServiceContract().INTENT_DUST_DENSITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_MOVING_AVERAGE, "[0..100]");

        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DUST_DENSITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..500]\n max: [0..500]");

        addDescription(getServiceContract().EVENT_DUST_DENSITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..500]\n");
        addDescription(getServiceContract().EVENT_DUST_DENSITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..500]\n");
        addDescription(getServiceContract().STATUS_DUST_DENSITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DUST_DENSITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..500]\n max: [0..500]");
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
            if (string.startsWith(getServiceContract().INTENT_MOVING_AVERAGE)) {

                Short average = getMapper().readValue(payload, Short.class);
                getDevice().setMovingAverage(average);
            }
            if (string.startsWith(getServiceContract().INTENT_DUST_DENSITY_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setDustDensityCallbackPeriod(period);
            }

            if (string.startsWith(getServiceContract().INTENT_DUST_DENSITY_THRESHOLD)) {

                DeviceDustDensityCallbackThreshold threshold = getMapper().readValue(payload, DeviceDustDensityCallbackThreshold.class);
                getDevice().setDustDensityCallbackThreshold(threshold);
            }

        } catch (IOException ex) {
            Logger.getLogger(DustDetectorService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void movingAverageChanged(short movingAverage) {
        addStatus(getServiceContract().STATUS_MOVING_AVERAGE, movingAverage);
    }

    @Override
    public void dustDensityCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DUST_DENSITY_CALLBACK_PERIOD, period);
    }

    @Override
    public void dustDensityCallbackThresholdChanged(DeviceDustDensityCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_DUST_DENSITY_THRESHOLD, threshold);
    }

    @Override
    public void dustDensity(int i) {
        addEvent(getServiceContract().EVENT_DUST_DENSITY, new DustDensityEvent(i));
    }

    @Override
    public void dustDensityReached(int i) {
        addEvent(getServiceContract().EVENT_DUST_DENSITY_REACHED, new DustDensityEvent(i));
    }

    class DustDensityEvent {

        protected long timestamp;
        protected long value;

        public DustDensityEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public DustDensityEvent(long value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getValue() {
            return value;
        }

    }

}
