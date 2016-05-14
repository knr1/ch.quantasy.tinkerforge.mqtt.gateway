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
package ch.quantasy.gateway.service.device.uvLight;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.uvLight.DeviceUVLightCallbackThreshold;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDevice;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDeviceCallback;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class UVLightService extends AbstractDeviceService<UVLightDevice, UVLightServiceContract> implements UVLightDeviceCallback {

    public UVLightService(UVLightDevice device) throws MqttException {

        super(device, new UVLightServiceContract(device));
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
            if (string.startsWith(getServiceContract().INTENT_UV_LIGHT_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setUVLightCallbackPeriod(period);
            }

            if (string.startsWith(getServiceContract().INTENT_UV_LIGHT_THRESHOLD)) {

                DeviceUVLightCallbackThreshold threshold = getMapper().readValue(payload, DeviceUVLightCallbackThreshold.class);
                getDevice().setUVLightCallbackThreshold(threshold);
            }
           

        } catch (IOException ex) {
            Logger.getLogger(UVLightService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }


    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void uvLightCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_UV_LIGHT_CALLBACK_PERIOD, period);
    }

    @Override
    public void uvLightCallbackThresholdChanged(DeviceUVLightCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_UV_LIGHT_THRESHOLD, threshold);
    }

    @Override
    public void uvLight(long i) {
        addEvent(getServiceContract().EVENT_UV_LIGHT, new UVLightEvent(i));
    }

    @Override
    public void uvLightReached(long i) {
        addEvent(getServiceContract().EVENT_UV_LIGHT_REACHED, new UVLightEvent(i));
    }

    class UVLightEvent {

        protected long timestamp;
        protected long value;

        public UVLightEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public UVLightEvent(long value, long timeStamp) {
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
