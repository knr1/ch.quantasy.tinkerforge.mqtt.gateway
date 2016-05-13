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
package ch.quantasy.gateway.service.device.temperatureIR;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.temperatureIR.DeviceAmbientTemperatureCallbackThreshold;
import ch.quantasy.tinkerforge.device.temperatureIR.DeviceObjectTemperatureCallbackThreshold;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDevice;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDeviceCallback;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class TemperatureIRService extends AbstractDeviceService<TemperatureIRDevice, TemperatureIRServiceContract> implements TemperatureIRDeviceCallback {

    public TemperatureIRService(TemperatureIRDevice device) throws MqttException {
        super(device, new TemperatureIRServiceContract(device));
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
            if (string.startsWith(getServiceContract().INTENT_TOPIC_AMBIENT_TEMPERATURE_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setAmbientTemperatureCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_OBJECT_TEMPERATURE_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setObjectTemperatureCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_AMBIENT_TEMPERATURE_THRESHOLD)) {
                DeviceAmbientTemperatureCallbackThreshold threshold = getMapper().readValue(payload, DeviceAmbientTemperatureCallbackThreshold.class);
                getDevice().setAmbientTemperatureThreshold(threshold);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_OBJECT_TEMPERATURE_THRESHOLD)) {
                DeviceObjectTemperatureCallbackThreshold threshold = getMapper().readValue(payload, DeviceObjectTemperatureCallbackThreshold.class);
                getDevice().setObjectTemperatureThreshold(threshold);

            }

        } catch (IOException ex) {
            Logger.getLogger(TemperatureIRService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    @Override
    public void ambientTemperature(short s) {
        addEvent(getServiceContract().EVENT_TOPIC_AMBIENT_TEMPERATURE, new AmbientTemperatureEvent(s));
    }

    @Override
    public void ambientTemperatureReached(short s) {
        addEvent(getServiceContract().EVENT_TOPIC_AMBIENT_TEMPERATURE_REACHED, new AmbientTemperatureEvent(s));
    }

    @Override
    public void objectTemperature(short s) {
        addEvent(getServiceContract().EVENT_TOPIC_OBJECT_TEMPERATURE, new ObjectTemperatureEvent(s));
    }

    @Override
    public void objectTemperatureReached(short s) {
        addEvent(getServiceContract().EVENT_TOPIC_OBJECT_TEMPERATURE_REACHED, new ObjectTemperatureEvent(s));
    }
    
    

    @Override
    public void ambientTemperatureCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_TOPIC_AMBIENT_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void objectTemperatureCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().INTENT_TOPIC_OBJECT_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_TOPIC_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void ambientTemperatureCallbackThresholdChanged(DeviceAmbientTemperatureCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_TOPIC_AMBIENT_TEMPERATURE_THRESHOLD, threshold);
    }
    

    @Override
    public void objectTemperatureCallbackThresholdChanged(DeviceObjectTemperatureCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_TOPIC_OBJECT_TEMPERATURE_THRESHOLD, threshold);

    }

    public static class AmbientTemperatureEvent {

        protected long timestamp;
        protected short value;

        public AmbientTemperatureEvent(short value) {
            this(value, System.currentTimeMillis());
        }

        public AmbientTemperatureEvent(short value, long timeStamp) {
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

    public static class ObjectTemperatureEvent {

        long timestamp;
        short illuminance;

        public ObjectTemperatureEvent(short value) {
            this(value, System.currentTimeMillis());
        }

        public ObjectTemperatureEvent(short value, long timeStamp) {
            this.illuminance = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getHumidity() {
            return illuminance;
        }

    }

}
