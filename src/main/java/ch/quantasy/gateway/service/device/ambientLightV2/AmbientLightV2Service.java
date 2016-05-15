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
package ch.quantasy.gateway.service.device.ambientLightV2;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.ambientLightV2.AmbientLightV2Device;
import ch.quantasy.tinkerforge.device.ambientLightV2.AmbientLightV2DeviceCallback;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ch.quantasy.tinkerforge.device.ambientLightV2.DeviceIlluminanceCallbackThreshold;
import ch.quantasy.tinkerforge.device.ambientLightV2.DeviceConfiguration;

/**
 *
 * @author reto
 */
public class AmbientLightV2Service extends AbstractDeviceService<AmbientLightV2Device, AmbientLightV2ServiceContract> implements AmbientLightV2DeviceCallback {

    public AmbientLightV2Service(AmbientLightV2Device device) throws MqttException {

        super(device, new AmbientLightV2ServiceContract(device));
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ILLUMINANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ILLUMINANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..100000]\n max: [0..100000]");
        addDescription(getServiceContract().INTENT_CONFIGURATION, "illuminanceRange:[lx_unlimitted|lx_64000|lx_32000|lx_16000|lx_8000|lx_13000|lx_600]\n integrationTime: [ms_50|ms_100|ms_150|ms_200|ms_250|ms_300|ms_350|ms_400]\n");

        addDescription(getServiceContract().EVENT_IllUMINANCE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..100000]\n");
        addDescription(getServiceContract().EVENT_ILLUMINANCE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..100000]\n");
        addDescription(getServiceContract().STATUS_ILLUMINANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ILLUMINANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..100000]\n max: [0..100000]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_CONFIGURATION, "illuminanceRange:[lx_unlimitted|lx_64000|lx_32000|lx_16000|lx_8000|lx_13000|lx_600]\n integrationTime: [ms_50|ms_100|ms_150|ms_200|ms_250|ms_300|ms_350|ms_400]\n");
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
            if (string.startsWith(getServiceContract().INTENT_ILLUMINANCE_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setIlluminanceCallbackPeriod(period);
            }

            if (string.startsWith(getServiceContract().INTENT_ILLUMINANCE_THRESHOLD)) {

                DeviceIlluminanceCallbackThreshold threshold = getMapper().readValue(payload, DeviceIlluminanceCallbackThreshold.class);
                getDevice().setIlluminanceCallbackThreshold(threshold);
            }

            if (string.startsWith(getServiceContract().INTENT_CONFIGURATION)) {

                DeviceConfiguration configuration = getMapper().readValue(payload, DeviceConfiguration.class);
                getDevice().setConfiguration(configuration);
            }

        } catch (IOException ex) {
            Logger.getLogger(AmbientLightV2Service.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void illuminanceCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_ILLUMINANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void illuminanceCallbackThresholdChanged(DeviceIlluminanceCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_ILLUMINANCE_THRESHOLD, threshold);
    }

    @Override
    public void configurationChanged(DeviceConfiguration configuration) {
        addStatus(getServiceContract().STATUS_CONFIGURATION, configuration);
    }

    @Override
    public void illuminance(long i) {
        addEvent(getServiceContract().EVENT_IllUMINANCE, new IlluminanceEvent(i));
    }

    @Override
    public void illuminanceReached(long i) {
        addEvent(getServiceContract().EVENT_ILLUMINANCE_REACHED, new IlluminanceEvent(i));
    }

    class IlluminanceEvent {

        protected long timestamp;
        protected long value;

        public IlluminanceEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public IlluminanceEvent(long value, long timeStamp) {
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
