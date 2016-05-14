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
package ch.quantasy.gateway.service.stackManager;

import ch.quantasy.gateway.service.AbstractService;
import ch.quantasy.gateway.tinkerforge.TinkerForgeManager;
import ch.quantasy.gateway.tinkerforge.TinkerforgeFactoryListener;
import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceListener;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackListener;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class ManagerService extends AbstractService<ManagerServiceContract> implements TinkerforgeFactoryListener, TinkerforgeStackListener, TinkerforgeDeviceListener {

    public ManagerService() throws MqttException {
        super(new ManagerServiceContract("Manager"), "TinkerforgeStackManager");
        TinkerForgeManager.getInstance().addListener(this);
        updateStatus();
    }

    
    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;
        }
        try {
            if (string.startsWith(getServiceContract().INTENT_STACK_ADDRESS_ADD)) {

                String payloadString = new String(payload);
                TinkerforgeStackAddress address = getMapper().readValue(payloadString, TinkerforgeStackAddress.class);
                if (TinkerForgeManager.getInstance().containsStack(address)) {
                    return;
                }
                TinkerForgeManager.getInstance().addStack(address);

                System.out.println(">>" + new String(mm.getPayload()));

            }
            if (string.startsWith(getServiceContract().INTENT_STACK_ADDRESS_REMOVE)) {

                String payloadString = new String(payload);
                TinkerforgeStackAddress address = getMapper().readValue(payloadString, TinkerforgeStackAddress.class);
                if (!TinkerForgeManager.getInstance().containsStack(address)) {
                    return;
                }
                TinkerForgeManager.getInstance().removeStack(address);

                System.out.println(">>" + new String(mm.getPayload()));
            }

        } catch (IOException ex) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    public void connected(TinkerforgeStack stack) {
        TinkerforgeStackAddress address = stack.getStackAddress();
        String topic = getServiceContract().STATUS_STACK_ADDRESS + "/" + address.hostName + ":" + address.port;
        addStatus(topic, stack.isConnected());

    }

    @Override
    public void disconnected(TinkerforgeStack stack) {
        TinkerforgeStackAddress address = stack.getStackAddress();
        String topic = getServiceContract().STATUS_STACK_ADDRESS + "/" + address.hostName + ":" + address.port;
        addStatus(topic, stack.isConnected());
    }

    public void updateStatus() {
        Set<TinkerforgeStackAddress> addresses = TinkerForgeManager.getInstance().getStackAddresses();
        for (TinkerforgeStackAddress address : addresses) {
            TinkerforgeStack stack = TinkerForgeManager.getInstance().getStackFactory().getStack(address);
            this.stackAdded(stack);
        }

    }

    @Override
    public void stackAdded(TinkerforgeStack stack) {
        stack.addListener((TinkerforgeStackListener) this);
        stack.addListener((TinkerforgeDeviceListener) this);

        TinkerforgeStackAddress address = stack.getStackAddress();
        addStatus(getServiceContract().STATUS_STACK_ADDRESS + "/" + address.hostName + ":" + address.port, stack.isConnected());
        addEvent(getServiceContract().EVENT_STACK_ADDRESS_ADDED, address);

    }

    @Override
    public void stackRemoved(TinkerforgeStack stack) {

        if (stack == null) {
            return;
        }
        {
            stack.removeListener((TinkerforgeStackListener) this);
            TinkerforgeStackAddress address = stack.getStackAddress();
            String topic = getServiceContract().STATUS_STACK_ADDRESS + "/" + address.hostName + ":" + address.port;
            addStatus(topic, null);
            addEvent(getServiceContract().EVENT_STACK_ADDRESS_REMOVED, address);
        }
        for (TinkerforgeDevice device : stack.getDevices()) {
            device.removeListener(this);
            String topic = getServiceContract().STATUS_DEVICE + "/" + device.getAddress().hostName + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
            addStatus(topic, null);
        }
    }

    private void updateDevice(TinkerforgeDevice device) {
        String topic = getServiceContract().STATUS_DEVICE + "/" + device.getAddress().hostName + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        addStatus(topic, device.isConnected());

    }

    @Override
    public void connected(TinkerforgeDevice device) {
        String topic = getServiceContract().STATUS_DEVICE + "/" + device.getAddress().hostName + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        addStatus(topic, true);
    }

    @Override
    public void reConnected(TinkerforgeDevice device) {
        String topic = getServiceContract().STATUS_DEVICE + "/" + device.getAddress().hostName + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        addStatus(topic, true);
    }

    @Override
    public void disconnected(TinkerforgeDevice device) {
        String topic = getServiceContract().STATUS_DEVICE + "/" + device.getAddress().hostName + "/" + TinkerforgeDeviceClass.getDevice(device.getDevice()) + "/" + device.getUid();
        addStatus(topic, false);
    }
}
