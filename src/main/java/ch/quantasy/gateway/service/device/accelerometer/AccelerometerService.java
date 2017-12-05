/*
 * /*
 *  *   "TiMqWay"
 *  *
 *  *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *  *
 *  *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
 *  *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *    Quellgasse 21, CH-2501 Biel, Switzerland
 *  *
 *  *    Licensed under Dual License consisting of:
 *  *    1. GNU Affero General Public License (AGPL) v3
 *  *    and
 *  *    2. Commercial license
 *  *
 *  *
 *  *    1. This program is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU Affero General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     This program is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU Affero General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU Affero General Public License
 *  *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 *  *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *  *     accordance with the commercial license agreement provided with the
 *  *     Software or, alternatively, in accordance with the terms contained in
 *  *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *  *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *  *
 *  *
 *  *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *  *
 *  *
 */
package ch.quantasy.gateway.service.device.accelerometer;

import ch.quantasy.gateway.message.event.IMU.AccelerationEvent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.accelerometer.AccelerometerDevice;
import ch.quantasy.tinkerforge.device.accelerometer.AccelerometerDeviceCallback;
import ch.quantasy.gateway.message.intent.accelerometer.DeviceAccelerationCallbackThreshold;
import ch.quantasy.gateway.message.intent.accelerometer.DeviceConfiguration;
import ch.quantasy.gateway.message.status.IMU.AccelerationCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.accelerometer.AccelerationThresholdStatus;
import ch.quantasy.gateway.message.status.accelerometer.ConfigurationStatus;
import ch.quantasy.gateway.message.status.accelerometer.DebouncePeriodStatus;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class AccelerometerService extends AbstractDeviceService<AccelerometerDevice, AccelerometerServiceContract> implements AccelerometerDeviceCallback {

    public AccelerometerService(AccelerometerDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new AccelerometerServiceContract(device));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void accelerationCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_ACCELERATION_CALLBACK_PERIOD, new AccelerationCallbackPeriodStatus(period));
    }

    @Override
    public void accelerationCallbackThresholdChanged(DeviceAccelerationCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_ACCELERATION_THRESHOLD, new AccelerationThresholdStatus(threshold));
    }

    @Override
    public void configurationChanged(DeviceConfiguration configuration) {
        publishStatus(getContract().STATUS_CONFIGURATION, new ConfigurationStatus(configuration));
    }

    @Override
    public void acceleration(short x, short y, short z) {
        super.readyToPublishEvent(getContract().EVENT_ACCELERATION, new AccelerationEvent(x, y, z));
    }

    @Override
    public void accelerationReached(short x, short y, short z) {
        super.readyToPublishEvent(getContract().EVENT_ACCELERATION_REACHED, new AccelerationEvent(x, y, z));
    }
}
