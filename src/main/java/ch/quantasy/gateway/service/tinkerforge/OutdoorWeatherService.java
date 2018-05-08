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

import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectorV2ServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.Indicator;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.IndicatorStatus;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectionCycleEndedEvent;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.MotionDetectionDetected;
import ch.quantasy.gateway.binding.tinkerforge.motionDetectorV2.SensitivityStatus;
import ch.quantasy.gateway.binding.tinkerforge.outdoorWeather.BatteryLowEvent;
import ch.quantasy.gateway.binding.tinkerforge.outdoorWeather.HumidityEvent;
import ch.quantasy.gateway.binding.tinkerforge.outdoorWeather.OutdoorWeatherServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.outdoorWeather.RainEvent;
import ch.quantasy.gateway.binding.tinkerforge.outdoorWeather.TemperatureEvent;
import ch.quantasy.gateway.binding.tinkerforge.outdoorWeather.WindDirectionEvent;
import ch.quantasy.gateway.binding.tinkerforge.outdoorWeather.WindSpeedEvent;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.motionDetectorV2.MotionDetectorV2DeviceCallback;
import ch.quantasy.tinkerforge.device.outdoorWeather.OutdoorWeatherDevice;
import ch.quantasy.tinkerforge.device.outdoorWeather.OutdoorWeatherDeviceCallback;
import java.net.URI;

/**
 *
 * @author reto
 */
public class OutdoorWeatherService extends AbstractDeviceService<OutdoorWeatherDevice, OutdoorWeatherServiceContract> implements OutdoorWeatherDeviceCallback {

    public OutdoorWeatherService(OutdoorWeatherDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new OutdoorWeatherServiceContract(device));
    }

    @Override
    public void sensorData(int identifier, int temperature, int humidity) {
        readyToPublish(getContract().EVENT_TEMPERATURE, new TemperatureEvent(identifier, temperature));
        readyToPublish(getContract().EVENT_HUMIDITY, new HumidityEvent(identifier, humidity));
    }

    @Override
    public void stationData(int identifier, int temperature, int humidity, long windSpeed, long gustSpeed, long rain, int windDirection, boolean batteryLow) {
        readyToPublish(getContract().EVENT_TEMPERATURE, new TemperatureEvent(identifier, temperature));
        readyToPublish(getContract().EVENT_HUMIDITY, new HumidityEvent(identifier, humidity));
        readyToPublish(getContract().EVENT_WIND_SPEED, new WindSpeedEvent(identifier,windSpeed));
        readyToPublish(getContract().EVENT_GUST_SPEED, new WindSpeedEvent(identifier, gustSpeed));
        readyToPublish(getContract().EVENT_RAIN, new RainEvent(identifier, rain));
        readyToPublish(getContract().EVENT_WIND_DIRECTION, new WindDirectionEvent(identifier, windDirection));
        readyToPublish(getContract().EVENT_BATTERY_LOW, new BatteryLowEvent(identifier, batteryLow));
        

    }

}
