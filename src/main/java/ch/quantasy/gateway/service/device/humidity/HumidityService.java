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
package ch.quantasy.gateway.service.device.humidity;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;
import ch.quantasy.tinkerforge.device.humidity.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.tinkerforge.device.humidity.DeviceHumidityCallbackThreshold;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ch.quantasy.tinkerforge.device.humidity.HumidityDeviceCallback;

/**
 *
 * @author reto
 */
public class HumidityService extends AbstractDeviceService<HumidityDevice, HumidityServiceContract> implements HumidityDeviceCallback {

    public HumidityService(HumidityDevice device) throws MqttException {
        super(device, new HumidityServiceContract(device));
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
                              Long period = getMapper().readValue(payload, Long.class                );
                getDevice().setAnalogValueCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_HUMIDITY_CALLBACK_PERIOD)) {
              
                Long period = getMapper().readValue(payload, Long.class                );
                getDevice().setHumidityCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_ANALOG_VALUE_THRESHOLD)) {
              
                DeviceAnalogValueCallbackThreshold threshold = getMapper().readValue(payload, DeviceAnalogValueCallbackThreshold.class
                );
                getDevice().setAnalogValueThreshold(threshold);
            }
            if (string.startsWith(getServiceContract().INTENT_TOPIC_HUMIDITY_THRESHOLD)) {
                DeviceHumidityCallbackThreshold threshold = getMapper().readValue(payload, DeviceHumidityCallbackThreshold.class);
                getDevice().setHumidityCallbackThreshold(threshold);
            }

        } catch (IOException ex) {
            Logger.getLogger(HumidityService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        //System.out.println("Delivery is done.");
    }

    @Override
    public void analogValue(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_ANALOG_VALUE,new AnalogValueEvent(i));
    }

    @Override
    public void analogValueReached(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_ANALOG_VALUE_REACHED,new AnalogValueEvent(i));
    }

    @Override
    public void humidity(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_HUMIDITY,new HumidityEvent(i));
    }

    @Override
    public void humidityReached(int i) {
        addEvent(getServiceContract().EVENT_TOPIC_HUMIDITY_REACHED,new HumidityEvent(i));
    }

    @Override
    public void analogValueCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_TOPIC_ANALOG_VALUE_CALLBACK_PERIOD,period);
    }

    @Override
    public void humidityCallbackPeriodChanged(long period) {  
            addStatus(getServiceContract().STATUS_TOPIC_HUMIDITY_CALLBACK_PERIOD,period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_TOPIC_DEBOUNCE_PERIOD,period);
    }

    @Override
    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_TOPIC_ANALOG_VALUE_THRESHOLD,threshold);
    }

    @Override
    public void humidityCallbackThresholdChanged(DeviceHumidityCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_TOPIC_HUMIDITY_THRESHOLD,threshold);
    }

    class AnalogValueEvent {

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

    class HumidityEvent {

        long timestamp;
        int value;

        public HumidityEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public HumidityEvent(int value, long timeStamp) {
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
