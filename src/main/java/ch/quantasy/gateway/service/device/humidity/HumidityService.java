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
package ch.quantasy.gateway.service.device.humidity;

import ch.quantasy.gateway.message.event.humidity.AnalogValueEvent;
import ch.quantasy.gateway.message.event.humidity.HumidityEvent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;
import ch.quantasy.gateway.message.intent.humidity.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.gateway.message.intent.humidity.DevicePositionCallbackThreshold;
import ch.quantasy.gateway.message.intent.humidity.HumidityIntent;
import ch.quantasy.gateway.message.status.humidity.AnalogCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.humidity.AnalogValueCallbackThresholdStatus;
import ch.quantasy.gateway.message.status.humidity.DebouncePeriodStatus;
import ch.quantasy.gateway.message.status.humidity.HumidityCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.humidity.HumidityCallbackThresholdStatus;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.humidity.HumidityDeviceCallback;
import com.tinkerforge.BrickletHumidity;
import java.net.URI;

/**
 *
 * @author reto
 */
public class HumidityService extends AbstractDeviceService<HumidityDevice, HumidityServiceContract> implements HumidityDeviceCallback {

    public HumidityService(HumidityDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new HumidityServiceContract(device));

    }

    @Override
    public void analogValue(int i) {
        readyToPublishEvent(getContract().EVENT_ANALOG_VALUE, new AnalogValueEvent(i));
    }

    @Override
    public void analogValueReached(int i) {
        readyToPublishEvent(getContract().EVENT_ANALOG_VALUE_REACHED, new AnalogValueEvent(i));
    }

    @Override
    public void humidity(int i) {
        readyToPublishEvent(getContract().EVENT_HUMIDITY, new HumidityEvent(i));
    }

    @Override
    public void humidityReached(int i) {
        readyToPublishEvent(getContract().EVENT_HUMIDITY_REACHED, new HumidityEvent(i));
    }

    @Override
    public void analogValueCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, new AnalogCallbackPeriodStatus(period));
    }

    @Override
    public void humidityCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_HUMIDITY_CALLBACK_PERIOD, new HumidityCallbackPeriodStatus(period));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_ANALOG_VALUE_THRESHOLD, new AnalogValueCallbackThresholdStatus(threshold));
    }

    @Override
    public void humidityCallbackThresholdChanged(DevicePositionCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_HUMIDITY_THRESHOLD, new HumidityCallbackThresholdStatus(threshold));
    }

}
