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
package ch.quantasy.gateway.service.device.co2;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.co2.CO2Device;
import ch.quantasy.tinkerforge.device.co2.CO2DeviceCallback;
import ch.quantasy.tinkerforge.device.co2.DeviceCO2ConcentrationCallbackThreshold;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class CO2Service extends AbstractDeviceService<CO2Device, CO2ServiceContract> implements CO2DeviceCallback {

    public CO2Service(CO2Device device) throws MqttException {

        super(device, new CO2ServiceContract(device));
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
            if (string.startsWith(getServiceContract().INTENT_CO2_CONCENTRATION_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setCO2ConcentrationCallbackPeriod(period);
            }

            if (string.startsWith(getServiceContract().INTENT_CO2_CONCENTRATION_THRESHOLD)) {

                DeviceCO2ConcentrationCallbackThreshold threshold = getMapper().readValue(payload, DeviceCO2ConcentrationCallbackThreshold.class);
                getDevice().setCO2ConcentrationCallbackThreshold(threshold);
            }

        } catch (IOException ex) {
            Logger.getLogger(CO2Service.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }


    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void co2ConcentrationCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_CO2_CONCENTRATION_CALLBACK_PERIOD, period);
    }

    @Override
    public void co2ConcentrationCallbackThresholdChanged(DeviceCO2ConcentrationCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_CO2_CONCENTRATION_THRESHOLD, threshold);
    }

    @Override
    public void co2Concentration(int i) {
        addEvent(getServiceContract().EVENT_CO2_CONCENTRATION, new CO2ConcentrationEvent(i));
    }

    @Override
    public void co2ConcentrationReached(int i) {
        addEvent(getServiceContract().EVENT_CO2_CONCENTRATION_REACHED, new CO2ConcentrationEvent(i));
    }

    class CO2ConcentrationEvent {

        protected long timestamp;
        protected long value;

        public CO2ConcentrationEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public CO2ConcentrationEvent(long value, long timeStamp) {
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
