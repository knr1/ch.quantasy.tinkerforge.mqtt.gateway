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
package ch.quantasy.gateway.service.tinkerforge.barometer;

import ch.quantasy.gateway.message.barometer.AirPressureEvent;
import ch.quantasy.gateway.message.barometer.AltitudeEvent;
import ch.quantasy.gateway.message.barometer.BarometerIntent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.gateway.message.barometer.DeviceAirPressureCallbackThreshold;
import ch.quantasy.gateway.message.barometer.DeviceAltitudeCallbackThreshold;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import ch.quantasy.tinkerforge.device.barometer.BarometerDeviceCallback;
import ch.quantasy.gateway.message.barometer.DeviceAveraging;
import ch.quantasy.gateway.message.barometer.AirPressureCallbackPeriodStatus;
import ch.quantasy.gateway.message.barometer.AirPressureCallbackThresholdStatus;
import ch.quantasy.gateway.message.barometer.AltitudeCallbackPeriodStatus;
import ch.quantasy.gateway.message.barometer.AltitudeCallbackThresholdStatus;
import ch.quantasy.gateway.message.barometer.AveragingStatus;
import ch.quantasy.gateway.message.barometer.DebouncePeriodStatus;
import ch.quantasy.gateway.message.barometer.ReferenceAirPressureStatus;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class BarometerService extends AbstractDeviceService<BarometerDevice, BarometerServiceContract> implements BarometerDeviceCallback {

    public BarometerService(BarometerDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new BarometerServiceContract(device));

    }

    @Override
    public void airPressure(int i) {

        readyToPublishEvent(getContract().EVENT_AIR_PRESSURE, new AirPressureEvent(i));
    }

    @Override
    public void airPressureReached(int i) {
        readyToPublishEvent(getContract().EVENT_AIR_PRESSURE_REACHED, new AirPressureEvent(i));
    }

    @Override
    public void altitude(int i) {
        readyToPublishEvent(getContract().EVENT_ALTITUDE, new AltitudeEvent(i));
    }

    @Override
    public void altitudeReached(int i) {
        readyToPublishEvent(getContract().EVENT_ALTITUDE_REACHED, new AltitudeEvent(i));
    }

    @Override
    public void airPressureCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_AIR_PRESSURE_CALLBACK_PERIOD, new AirPressureCallbackPeriodStatus(period));
    }

    @Override
    public void altitudeCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_ALTITUDE_CALLBACK_PERIOD, new AltitudeCallbackPeriodStatus(period));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void airPressureCallbackThresholdChanged(DeviceAirPressureCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_AIR_PRESSURE_THRESHOLD, new AirPressureCallbackThresholdStatus(threshold));
    }

    @Override
    public void altitudeCallbackThresholdChanged(DeviceAltitudeCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_ALTITUDE_THRESHOLD, new AltitudeCallbackThresholdStatus(threshold));
    }

    @Override
    public void averagingChanged(DeviceAveraging averaging) {
        readyToPublishStatus(getContract().STATUS_AVERAGING, new AveragingStatus(averaging));
    }

    @Override
    public void referenceAirPressureChanged(Integer referenceAirPressure) {
        readyToPublishStatus(getContract().STATUS_REFERENCE_AIR_PRESSURE, new ReferenceAirPressureStatus(referenceAirPressure));
    }

}