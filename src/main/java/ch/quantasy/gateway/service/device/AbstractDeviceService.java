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
package ch.quantasy.gateway.service.device;

import ch.quantasy.gateway.service.AbstractService;
import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 * @param <G>
 * @param <S>
 */
public abstract class AbstractDeviceService<G extends GenericDevice, S extends DeviceServiceContract> extends AbstractService<S> implements DeviceCallback {

    private final G device;

    public AbstractDeviceService(G device, S serviceContract) throws MqttException {
        super(serviceContract, device.getUid());
        this.device = device;
        device.setCallback(this);

    }

    public G getDevice() {
        return device;
    }

}
