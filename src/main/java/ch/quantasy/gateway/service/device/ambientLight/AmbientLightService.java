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
package ch.quantasy.gateway.service.device.ambientLight;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.ambientLight.AmbientLightDevice;
import ch.quantasy.tinkerforge.device.ambientLight.AmbientLightDeviceCallback;
import ch.quantasy.tinkerforge.device.ambientLight.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.tinkerforge.device.ambientLight.DeviceIlluminanceCallbackThreshold;

import com.tinkerforge.BrickletAmbientLight.AnalogValueCallbackThreshold;
import com.tinkerforge.BrickletAmbientLight.IlluminanceCallbackThreshold;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class AmbientLightService extends AbstractDeviceService<AmbientLightDevice, AmbientLightServiceContract> implements AmbientLightDeviceCallback {

    public AmbientLightService(AmbientLightDevice device) throws MqttException {
        super(device, new AmbientLightServiceContract(device));
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
            if (string.startsWith(getServiceContract().INTENT_TOPIC_ANALOG_VALUE_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setAnalogValueCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_IllUMINANCE_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setIlluminanceCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_ANALOG_VALUE_THRESHOLD)) {
                DeviceAnalogValueCallbackThreshold threshold = getMapper().readValue(payload, DeviceAnalogValueCallbackThreshold.class);
                getDevice().setAnalogValueThreshold(threshold);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_ILLUMINANCE_THRESHOLD)) {
                DeviceIlluminanceCallbackThreshold threshold = getMapper().readValue(payload, DeviceIlluminanceCallbackThreshold.class);
                getDevice().setIlluminanceCallbackThreshold(threshold);

            }

        } catch (IOException ex) {
            Logger.getLogger(AmbientLightService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    @Override
    public void analogValue(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_ANALOG_VALUE, new AnalogValueEvent(i));
    }

    @Override
    public void analogValueReached(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_ANALOG_VALUE_REACHED, new AnalogValueEvent(i));
    }

    @Override
    public void illuminance(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_ILLUMINANCE, new IlluminanceEvent(i));
    }

    @Override
    public void illuminanceReached(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_ILLUMINANCE_REACHED, new IlluminanceEvent(i));
    }

    @Override
    public void analogValueCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_TOPIC_ANALOG_VALUE_CALLBACK_PERIOD, period);
    }

    @Override
    public void illuminanceCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().INTENT_TOPIC_IllUMINANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_TOPIC_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_TOPIC_ANALOG_VALUE_THRESHOLD, threshold);
    }

    @Override
    public void illuminanceCallbackThresholdChanged(DeviceIlluminanceCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_TOPIC_ILLUMINANCE_THRESHOLD, threshold);

    }

    public static class AnalogValueEvent {

        protected long timestamp;
        protected int value;

        public AnalogValueEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public AnalogValueEvent(int value, long timeStamp) {
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

    public static class IlluminanceEvent {

        long timestamp;
        int value;

        public IlluminanceEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public IlluminanceEvent(int value, long timeStamp) {
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
