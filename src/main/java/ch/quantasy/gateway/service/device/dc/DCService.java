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
package ch.quantasy.gateway.service.device.dc;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dc.DCDevice;
import ch.quantasy.tinkerforge.device.dc.DCDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DCService extends AbstractDeviceService<DCDevice, DCServiceContract> implements DCDeviceCallback {

    public DCService(DCDevice device) throws MqttException {
        super(device, new DCServiceContract(device));
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;
        }
        if (string.startsWith(getServiceContract().INTENT_ENABLED)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setEnabled(enabled);
        }
        
        if (string.startsWith(getServiceContract().INTENT_ACCELERATION)) {
            Integer acceleration = getMapper().readValue(payload, Integer.class);
            getDevice().setAcceleration(acceleration);
        }
        if (string.startsWith(getServiceContract().INTENT_DRIVER_MODE)) {
            Short driverMode = getMapper().readValue(payload, Short.class);
            getDevice().setDriveMode(driverMode);
        }
        if (string.startsWith(getServiceContract().INTENT_MINIMUM_VOLTAGE)) {
            Integer minimumVoltage = getMapper().readValue(payload, Integer.class);
            getDevice().setMinimumVoltage(minimumVoltage);
        }
        if (string.startsWith(getServiceContract().INTENT_PWM_FREQUENCY)) {
            Integer pwmFrequency = getMapper().readValue(payload, Integer.class);
            getDevice().setPWMFrequency(pwmFrequency);
        }
        if (string.startsWith(getServiceContract().INTENT_VELOCITY)) {
            Short velocity = getMapper().readValue(payload, Short.class);
            getDevice().setVelocity(velocity);
        }
        if (string.startsWith(getServiceContract().INTENT_VELOCITY_PERIOD)) {
            Integer velocityPeriod = getMapper().readValue(payload, Integer.class);
            getDevice().setVelocityPeriod(velocityPeriod);
        }

    }

    @Override
    public void PWMFrequencyChanged(Integer pwmFrequency) {
        addStatus(getServiceContract().STATUS_PWM_FREQUENCY, pwmFrequency);
    }

    @Override
    public void accelerationChanged(Integer acceleration) {
        addStatus(getServiceContract().STATUS_ACCELERATION, acceleration);
    }

    @Override
    public void driveModeChanged(Short driverMode) {
        addStatus(getServiceContract().STATUS_DRIVER_MODE, driverMode);
    }

    @Override
    public void minimumVoltageChanged(Integer minimumVoltage) {
        addStatus(getServiceContract().STATUS_MINIMUM_VOLTAGE, minimumVoltage);

    }

    @Override
    public void velocityPeriodChanged(Integer velocityPeriod) {
        addStatus(getServiceContract().STATUS_VELOCITY_PERIOD, velocityPeriod);
    }

    @Override
    public void velocityChanged(Short velocity) {
        addStatus(getServiceContract().STATUS_VELOCITY, velocity);
    }

    @Override
    public void currentVelocity(short s) {
        addEvent(getServiceContract().EVENT_VELOCITY, new Velocity(s));
    }

    @Override
    public void emergencyShutdown() {
        addEvent(getServiceContract().STATUS_EMERGENCY_SHUTDOWN, System.currentTimeMillis());
    }

    @Override
    public void underVoltage(int i) {
        addEvent(getServiceContract().EVENT_UNDERVOLTAGE, new UnderVoltage(i));
    }

    @Override
    public void velocityReached(short s) {
        addEvent(getServiceContract().EVENT_FULL_BRAKE, new Velocity(s));
    }

    @Override
    public void enabledChanged(Boolean isEnabled) {
        addStatus(getServiceContract().STATUS_ENABLED, isEnabled);
    }

    @Override
    public void fullBrake() {
        addEvent(getServiceContract().EVENT_FULL_BRAKE, System.currentTimeMillis());
    }

    class Velocity {

        protected long timestamp;
        protected short value;

        private Velocity() {
        }

        public Velocity(short value) {
            this(value, System.currentTimeMillis());
        }

        public Velocity(short value, long timeStamp) {
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

    class UnderVoltage {

        protected long timestamp;
        protected int value;

        private UnderVoltage() {
        }

        public UnderVoltage(int value) {
            this(value, System.currentTimeMillis());
        }

        public UnderVoltage(int value, long timeStamp) {
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
