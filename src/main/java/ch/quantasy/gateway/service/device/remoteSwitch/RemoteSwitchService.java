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
package ch.quantasy.gateway.service.device.remoteSwitch;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ch.quantasy.tinkerforge.device.remoteSwitch.DimSocketBParameters;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDevice;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDeviceCallback;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketAParameters;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketBParameters;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketCParameters;

/**
 *
 * @author reto
 */
public class RemoteSwitchService extends AbstractDeviceService<RemoteSwitchDevice, RemoteSwitchServiceContract> implements RemoteSwitchDeviceCallback {

    public RemoteSwitchService(RemoteSwitchDevice device) throws MqttException {
        super(device, new RemoteSwitchServiceContract(device));

        addDescription(getServiceContract().INTENT_REPEATS, "[0.." + Short.MAX_VALUE + "]");

        addDescription(getServiceContract().INTENT_SWITCH_SOCKET_A, "houseCode: [0..31]\n receiverCode: [0..31]\n switchingValue: [ON|OFF]");
        addDescription(getServiceContract().INTENT_SWITCH_SOCKET_B, "address: [0..67108863]\n unit: [0..15]\n switchingValue: [ON|OFF]");
        addDescription(getServiceContract().INTENT_SWITCH_SOCKET_C, "systemCode: ['A'..'P']\n deviceCode: [1..16]\n switchingValue: [ON|OFF]");
        addDescription(getServiceContract().INTENT_DIM_SOCKET_B, "address: [0..67108863]\n unit: [0..15]\n dimValue: [0..15]");

        addDescription(getServiceContract().EVENT_SWITCHING_DONE, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_REPEATS, "[0.." + Short.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;

        }
        if (string.startsWith(getServiceContract().INTENT_DIM_SOCKET_B)) {
            DimSocketBParameters parameters = getMapper().readValue(payload, DimSocketBParameters.class);
            getDevice().dimSocketB(parameters);
        }
        if (string.startsWith(getServiceContract().INTENT_SWITCH_SOCKET_A)) {
            SwitchSocketAParameters parameters = getMapper().readValue(payload, SwitchSocketAParameters.class);
            getDevice().switchSocketA(parameters);
        }
        if (string.startsWith(getServiceContract().INTENT_SWITCH_SOCKET_B)) {
            SwitchSocketBParameters parameters = getMapper().readValue(payload, SwitchSocketBParameters.class);
            getDevice().switchSocketB(parameters);
        }
        if (string.startsWith(getServiceContract().INTENT_SWITCH_SOCKET_C)) {

            SwitchSocketCParameters parameters = getMapper().readValue(payload, SwitchSocketCParameters.class);
            getDevice().switchSocketC(parameters);
        }
        if (string.startsWith(getServiceContract().INTENT_REPEATS)) {
            short value = getMapper().readValue(payload, short.class);
            getDevice().setRepeats(value);
        }
    }

    @Override
    public void repeatsChanged(short period) {
        addStatus(getServiceContract().STATUS_REPEATS, period);
    }

    @Override
    public void switchingDone() {
        addEvent(getServiceContract().EVENT_SWITCHING_DONE, System.currentTimeMillis());
    }

}
