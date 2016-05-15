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
package ch.quantasy.gateway.service.device.barometer;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.barometer.DeviceAirPressureCallbackThreshold;
import ch.quantasy.tinkerforge.device.barometer.DeviceAltitudeCallbackThreshold;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import ch.quantasy.tinkerforge.device.barometer.BarometerDeviceCallback;
import ch.quantasy.tinkerforge.device.barometer.DeviceAveraging;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class BarometerService extends AbstractDeviceService<BarometerDevice, BarometerServiceContract> implements BarometerDeviceCallback {

    public BarometerService(BarometerDevice device) throws MqttException {
        super(device, new BarometerServiceContract(device));
        addDescription(getServiceContract().INTENT_AIR_PRESSURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_AIR_PRESSURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [10000..1200000]\n max: [10000..1200000]");
        addDescription(getServiceContract().INTENT_ALTITUDE_THRESHOLD, "option: [x|o|i|<|>]\n min: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n max: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_AVERAGING, "averagingPressure: [0..10]\n averagingTemperature: [0..255]\n movingAveragePressure: [0..25]");
        addDescription(getServiceContract().INTENT_REFERENCE_AIR_PRESSURE, "[" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        addDescription(getServiceContract().EVENT_AIR_PRESSURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [10000..1200000]\n");
        addDescription(getServiceContract().EVENT_ALTITUDE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        addDescription(getServiceContract().EVENT_AIR_PRESSURE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [10000..1200000]\n");
        addDescription(getServiceContract().EVENT_ALTITUDE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
        addDescription(getServiceContract().STATUS_AIR_PRESSURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_AIR_PRESSURE_THRESHOLD, "option: [x|o|i|<|>]\n min: [10000..1200000]\n max: [10000..1200000]");
        addDescription(getServiceContract().STATUS_ALTITUDE_THRESHOLD, "option: [x|o|i|<|>]\n min: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n max: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_AVERAGING, "averagingPressure: [0..10]\n averagingTemperature: [0..255]\n movingAveragePressure: [0..25]");
        addDescription(getServiceContract().STATUS_REFERENCE_AIR_PRESSURE, "[" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

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
            if (string.startsWith(getServiceContract().INTENT_AIR_PRESSURE_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setAirPressureCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_ALTITUDE_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setAltitudeCallbackPeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_AIR_PRESSURE_THRESHOLD)) {

                DeviceAirPressureCallbackThreshold threshold = getMapper().readValue(payload, DeviceAirPressureCallbackThreshold.class);
                getDevice().setAirPressureThreshold(threshold);
            }
            if (string.startsWith(getServiceContract().INTENT_ALTITUDE_THRESHOLD)) {

                DeviceAltitudeCallbackThreshold threshold = getMapper().readValue(payload, DeviceAltitudeCallbackThreshold.class);
                getDevice().setAltitudeCallbackThreshold(threshold);

            }
            if (string.startsWith(getServiceContract().INTENT_AVERAGING)) {

                DeviceAveraging averaging = getMapper().readValue(payload, DeviceAveraging.class);
                getDevice().setAveraging(averaging);

            }
            if (string.startsWith(getServiceContract().INTENT_REFERENCE_AIR_PRESSURE)) {
                Integer reference = getMapper().readValue(payload, Integer.class);
                getDevice().setReferenceAirPressure(reference);

            }

        } catch (IOException ex) {
            Logger.getLogger(BarometerService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void airPressure(int i) {

        addEvent(getServiceContract().EVENT_AIR_PRESSURE, new AirPressureEvent(i));
    }

    @Override
    public void airPressureReached(int i) {
        addEvent(getServiceContract().EVENT_AIR_PRESSURE_REACHED, new AirPressureEvent(i));
    }

    @Override
    public void altitude(int i) {
        addEvent(getServiceContract().EVENT_ALTITUDE, new AltitudeEvent(i));
    }

    @Override
    public void altitudeReached(int i) {
        addEvent(getServiceContract().EVENT_ALTITUDE_REACHED, new AltitudeEvent(i));
    }

    @Override
    public void airPressureCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_AIR_PRESSURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void altitudeCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_ALTITUDE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void airPressureCallbackThresholdChanged(DeviceAirPressureCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_AIR_PRESSURE_THRESHOLD, threshold);
    }

    @Override
    public void altitudeCallbackThresholdChanged(DeviceAltitudeCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_ALTITUDE_THRESHOLD, threshold);
    }

    @Override
    public void averagingChanged(DeviceAveraging averaging) {
        addStatus(getServiceContract().STATUS_AVERAGING, averaging);
    }

    @Override
    public void referenceAirPressureChanged(Integer referenceAirPressure) {
        addStatus(getServiceContract().STATUS_REFERENCE_AIR_PRESSURE, referenceAirPressure);
    }

    class AirPressureEvent {

        protected long timestamp;
        protected int airPressure;

        public AirPressureEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public AirPressureEvent(int value, long timeStamp) {
            this.airPressure = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getAirPressure() {
            return airPressure;
        }

    }

    class AltitudeEvent {

        long timestamp;
        int altitude;

        public AltitudeEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public AltitudeEvent(int value, long timeStamp) {
            this.altitude = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getAltitude() {
            return altitude;
        }

    }

}
