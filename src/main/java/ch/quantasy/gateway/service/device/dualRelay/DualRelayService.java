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
package ch.quantasy.gateway.service.device.dualRelay;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDevice;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDeviceCallback;
import ch.quantasy.tinkerforge.device.dualRelay.DeviceMonoflopParameters;
import ch.quantasy.tinkerforge.device.dualRelay.DeviceSelectedState;
import ch.quantasy.tinkerforge.device.dualRelay.DeviceState;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DualRelayService extends AbstractDeviceService<DualRelayDevice, DualRelayServiceContract> implements DualRelayDeviceCallback {

    public DualRelayService(DualRelayDevice device) throws MqttException {
        super(device, new DualRelayServiceContract(device));
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;
        }

        if (string.startsWith(getServiceContract().INTENT_MONOFLOP)) {
            DeviceMonoflopParameters parameters = getMapper().readValue(payload, DeviceMonoflopParameters.class);
            getDevice().setMonoflop(parameters);
        }
        if (string.startsWith(getServiceContract().INTENT_SELECTED_STATE)) {
            DeviceSelectedState parameters = getMapper().readValue(payload, DeviceSelectedState.class);
            getDevice().setSelectedState(parameters);
        }
        if (string.startsWith(getServiceContract().INTENT_STATE)) {   
            DeviceState parameters = getMapper().readValue(payload, DeviceState.class);
            getDevice().setState(parameters);
        }

    }

    @Override
    public void stateChanged(DeviceState state) {
        addStatus(getServiceContract().STATUS_STATE, state);
    }

    @Override
    public void monoflopDone(short relay, boolean state) {
        addEvent(getServiceContract().EVENT_MONOFLOP_DONE,new MonoflopDoneEvent(relay, state));
    }

    class MonoflopDoneEvent {

        protected long timestamp;
        protected short relay;
        protected boolean state;

        private MonoflopDoneEvent() {
        }

        public MonoflopDoneEvent(short relay, boolean state) {
            this(relay, state, System.currentTimeMillis());
        }

        public MonoflopDoneEvent(short relay, boolean state, long timeStamp) {
            this.relay = relay;
            this.state = state;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getRelay() {
            return relay;
        }

        public boolean getState() {
            return state;
        }

    }

}
