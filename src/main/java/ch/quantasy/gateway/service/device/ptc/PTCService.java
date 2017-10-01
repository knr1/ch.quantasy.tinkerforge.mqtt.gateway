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
package ch.quantasy.gateway.service.device.ptc;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.gateway.intent.ptc.DeviceNoiseReductionFilter;
import ch.quantasy.gateway.intent.ptc.DeviceResistanceCallbackThreshold;
import ch.quantasy.gateway.intent.ptc.DeviceTemperatureCallbackThreshold;
import ch.quantasy.gateway.intent.ptc.PTCIntent;
import ch.quantasy.tinkerforge.device.ptc.PTCDevice;
import ch.quantasy.tinkerforge.device.ptc.PTCDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class PTCService extends AbstractDeviceService<PTCDevice, PTCServiceContract> implements PTCDeviceCallback {

    public PTCService(PTCDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new PTCServiceContract(device));

    }

    

    @Override
    public void debouncePeriodChanged(long period) {
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void temperatureCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void temperatureCallbackThresholdChanged(DeviceTemperatureCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_TEMPERATURE_THRESHOLD, threshold);
    }

    @Override
    public void noiseReductionFilterChanged(DeviceNoiseReductionFilter filter) {
        publishStatus(getContract().STATUS_NOISE_REDUCTION_FILTER, filter);
    }

    @Override
    public void temperature(int i) {
        publishEvent(getContract().EVENT_TEMPERATURE, i);
    }

    @Override
    public void temperatureReached(int i) {
        publishEvent(getContract().EVENT_TEMPERATURE_REACHED, i);
    }

    @Override
    public void resistance(int i) {
        publishEvent(getContract().EVENT_RESISTANCE, i);
    }

    @Override
    public void resistanceReached(int i) {
        publishEvent(getContract().EVENT_RESISTANCE_REACHED, i);
    }

    @Override
    public void resistanceCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_RESISTANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void resistanceCallbackThresholdChanged(DeviceResistanceCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_RESISTANCE_THRESHOLD, threshold);
    }

    @Override
    public void wireModeChanged(short wireMode) {
        publishStatus(getContract().STATUS_WIRE_MODE, wireMode);
    }

}
