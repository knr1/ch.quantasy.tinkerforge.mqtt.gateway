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
package ch.quantasy.gateway.service.tinkerforge;

import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.TemperatureIRServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.AmbientTemperatureEvent;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.ObjectTemperatureEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.DeviceAmbientTemperatureCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.DeviceObjectTemperatureCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.AmbientTemperatureCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.AmbientTemperatureCallbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.DebouncePeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.ObjectTemperatureCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.temperatureIR.ObjectTemperatureCallbackThresholdStatus;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDevice;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class TemperatureIRService extends AbstractDeviceService<TemperatureIRDevice, TemperatureIRServiceContract> implements TemperatureIRDeviceCallback {

    public TemperatureIRService(TemperatureIRDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new TemperatureIRServiceContract(device));
    }

    @Override
    public void ambientTemperature(short s) {
        readyToPublishEvent(getContract().EVENT_AMBIENT_TEMPERATURE, new AmbientTemperatureEvent(s));
    }

    @Override
    public void ambientTemperatureReached(short s) {
        readyToPublishEvent(getContract().EVENT_AMBIENT_TEMPERATURE_REACHED, new AmbientTemperatureEvent(s));
    }

    @Override
    public void objectTemperature(short s) {
        readyToPublishEvent(getContract().EVENT_OBJECT_TEMPERATURE, new ObjectTemperatureEvent(s));
    }

    @Override
    public void objectTemperatureReached(short s) {
        readyToPublishEvent(getContract().EVENT_OBJECT_TEMPERATURE_REACHED, new ObjectTemperatureEvent(s));
    }

    @Override
    public void ambientTemperatureCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_AMBIENT_TEMPERATURE_CALLBACK_PERIOD, new AmbientTemperatureCallbackPeriodStatus(period));
    }

    @Override
    public void objectTemperatureCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_OBJECT_TEMPERATURE_CALLBACK_PERIOD, new ObjectTemperatureCallbackPeriodStatus(period));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void ambientTemperatureCallbackThresholdChanged(DeviceAmbientTemperatureCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_AMBIENT_TEMPERATURE_THRESHOLD, new AmbientTemperatureCallbackThresholdStatus(threshold));
    }

    @Override
    public void objectTemperatureCallbackThresholdChanged(DeviceObjectTemperatureCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_OBJECT_TEMPERATURE_THRESHOLD, new ObjectTemperatureCallbackThresholdStatus(threshold));

    }

}
