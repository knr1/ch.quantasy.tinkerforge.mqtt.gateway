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
package ch.quantasy.gateway.service.device.dualButton;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dualButton.DualButtonDevice;
import ch.quantasy.tinkerforge.device.dualButton.DualButtonDeviceCallback;
import ch.quantasy.tinkerforge.device.dualButton.DeviceLEDState;
import ch.quantasy.tinkerforge.device.dualButton.DeviceSelectedLEDStateParameters;
import ch.quantasy.tinkerforge.device.dualButton.LEDState;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DualButtonService extends AbstractDeviceService<DualButtonDevice, DualButtonServiceContract> implements DualButtonDeviceCallback {

    public DualButtonService(DualButtonDevice device) throws MqttException {
        super(device, new DualButtonServiceContract(device));
        
        addDescription(getServiceContract().INTENT_LED_STATE, "leftLED: [AutoToggleOn|AutoToggleOff|On|Off]\n rightLED: [AutoToggleOn|AutoToggleOff|On|Off] ");
        addDescription(getServiceContract().INTENT_SELECTED_LED_STATE, "led: [AutoToggleOn|AutoToggleOff|On|Off]");
        
        
        addDescription(getServiceContract().EVENT_STATE_CHANGED, "timestamp: [0.." + Long.MAX_VALUE + "]\n led1: [AutoToggleOn|AutoToggleOff|On|Off]\n led2: [AutoToggleOn|AutoToggleOff|On|Off]\n \n led2: [AutoToggleOn|AutoToggleOff|On|Off]\n  switch1: [0|1]\n switch2: [0|1]");
        addDescription(getServiceContract().STATUS_LED_STATE, "led1: [AutoToggleOn|AutoToggleOff|On|Off]\n led2: [AutoToggleOn|AutoToggleOff|On|Off]");
        
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;

        }
        if (string.startsWith(getServiceContract().INTENT_SELECTED_LED_STATE)) {
            DeviceSelectedLEDStateParameters parameters = getMapper().readValue(payload, DeviceSelectedLEDStateParameters.class);
            getDevice().setSelectedLEDState(parameters);
        }
        if (string.startsWith(getServiceContract().INTENT_LED_STATE)) {
            DeviceLEDState parameters = getMapper().readValue(payload, DeviceLEDState.class);
            getDevice().setLEDState(parameters);
        }

    }

    @Override
    public void ledStateChanged(DeviceLEDState state) {
        addStatus(getServiceContract().STATUS_LED_STATE, state);
    }

    @Override
    public void stateChanged(short s, short s1, short s2, short s3) {
        addEvent(getServiceContract().EVENT_STATE_CHANGED, new StateChangedEvent(s, s1, s2, s3));
    }

    public static class StateChangedEvent {

        protected long timestamp;
        protected LEDState led1;
        protected LEDState led2;
        protected short switch1;
        protected short swicht2;

        private StateChangedEvent() {
        }
public StateChangedEvent(short switch1, short switch2, short led1, short led2) {
            this(LEDState.getLEDStateFor(led1), LEDState.getLEDStateFor(led1), switch1, switch2, System.currentTimeMillis());
        }
        public StateChangedEvent(short switch1, short switch2, LEDState led1, LEDState led2) {
            this(led1, led2, switch1, switch2, System.currentTimeMillis());
        }

        public StateChangedEvent(LEDState led1, LEDState led2, short switch1, short switch2, long timeStamp) {
            this.led1 = led1;
            this.led2 = led2;
            this.switch1 = switch1;
            this.swicht2 = switch2;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public LEDState getLed1() {
            return led1;
        }

        public LEDState getLed2() {
            return led2;
        }

        public short getSwitch1() {
            return switch1;
        }

        public short getSwicht2() {
            return swicht2;
        }

    }

}
