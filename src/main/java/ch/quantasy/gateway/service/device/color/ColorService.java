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
package ch.quantasy.gateway.service.device.color;

import ch.quantasy.gateway.message.event.color.ColorEvent;
import ch.quantasy.gateway.message.event.color.ColorTemperatureEvent;
import ch.quantasy.gateway.message.event.color.IlluminanceEvent;
import ch.quantasy.gateway.message.intent.color.ColorIntent;
import ch.quantasy.gateway.message.intent.color.DeviceColorCallbackThreshold;
import ch.quantasy.gateway.message.intent.color.DeviceConfiguration;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.color.ColorDevice;
import ch.quantasy.tinkerforge.device.color.ColorDeviceCallback;

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class ColorService extends AbstractDeviceService<ColorDevice, ColorServiceContract> implements ColorDeviceCallback {

    public ColorService(ColorDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new ColorServiceContract(device));
    }

    @Override
    public void color(int i, int i1, int i2, int i3) {
        readyToPublishEvent(getContract().EVENT_COLOR, new ColorEvent(i, i1, i2, i3));
    }

    @Override
    public void colorReached(int i, int i1, int i2, int i3) {
        readyToPublishEvent(getContract().EVENT_COLOR_REACHED, new ColorEvent(i, i1, i2, i3));
    }

    @Override
    public void illuminance(long i) {
        readyToPublishEvent(getContract().EVENT_ILLUMINANCE, new IlluminanceEvent(i));
    }

    @Override
    public void colorTemperature(int i) {
        readyToPublishEvent(getContract().EVENT_COLOR_TEMPERATURE_REACHED, new ColorTemperatureEvent(i));
    }

    @Override
    public void colorTemperatureCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_COLOR_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void configurationChanged(DeviceConfiguration config) {
        publishStatus(getContract().STATUS_CONFIGURATION, config);
    }

    @Override
    public void lightStatusChanged(boolean isLightOn) {
        publishStatus(getContract().STATUS_LIGHT_STATE, isLightOn);
    }

    @Override
    public void colorCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_COLOR_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void illuminanceCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_ILLUMINANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void colorCallbackThresholdChanged(DeviceColorCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_COLOR_THRESHOLD, threshold);
    }

    
}
